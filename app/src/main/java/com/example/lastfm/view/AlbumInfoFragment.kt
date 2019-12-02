package com.example.lastfm.view


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.lastfm.R
import com.example.lastfm.models.albumInfo.AlbumInfo
import com.example.lastfm.viewmodels.AlbumInfoViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_album_info.*
import com.example.lastfm.util.setVisible
import javax.inject.Inject

/**
 * Fragment to display the Album details
 */
class AlbumInfoFragment : Fragment() {

    @Inject
    lateinit var albumInfoViewModel: AlbumInfoViewModel
    private var artist: String? = null
    private var album: String? = null
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var info: AlbumInfo
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artist = it.getString(ARG_ARTIST)
            album = it.getString(ARG_ALBUM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity
        compositeDisposable = CompositeDisposable()
        albumInfoViewModel.albumInfo.observe(this, Observer {
            info = it
            updateDetails(info)
        })

        mainActivity.showProgress()


        /**
         *  make a service call with the provided artist and album details.
         */
        artist?.let { artist->
            album?.let { album->
                compositeDisposable.add(
                    albumInfoViewModel.getAlbumInfo(album, artist)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AlbumInfo>() {
                            override fun onSuccess(t: AlbumInfo) {
                                mainActivity.hideProgress()
                                albumInfoViewModel.albumInfo.value = t
                            }

                            override fun onError(e: Throwable) {
                                mainActivity.hideProgress()
                                e.message?.let {
                                    Log.e(TAG, it)
                                }

                            }
                        })
                )
            }
        }

    }

    /**
     * Update UI
     * showing summary and published date based upon data availability
     */
    private fun updateDetails(info: AlbumInfo) {

        albumName.text = albumInfoViewModel.getAlbumName(info)
        artistName.text = albumInfoViewModel.getArtistName(info)

        albumInfoViewModel.getPublishDate(info)?.let {
            publishDate.apply {
                setVisible(true)
                text = getString(R.string.published_on, it)
            }
        }?: run{
            publishDate.setVisible(false)
        }

        albumInfoViewModel.getSummary(info)?.let {
            summaryLabel.visibility = View.VISIBLE
            summaryDesc.apply {
                visibility = View.VISIBLE
                text = it
            }
        }?: run {
            summaryLabel.setVisible(false)
            summaryDesc.setVisible(false)
        }

        context?.let {
            Glide.with(it)
                .load(info.album.image[IMG_INDEX].text)
                .apply(
                    RequestOptions().override(
                        imageView.drawable.intrinsicWidth,
                        imageView.drawable.intrinsicHeight
                    ).placeholder(R.drawable.ic_launcher_background)
                )
                .into(imageView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {

        private const val TAG = "AlbumInfoFragment"
        private const val ARG_ARTIST = "artist"
        private const val ARG_ALBUM = "album"
        private const val IMG_INDEX = 2

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param artistName Parameter 1.
         * @param albumName Parameter 2.
         * @return A new instance of fragment AlbumInfoFragment.
         */
        @JvmStatic
        fun newInstance(artistName: String, albumName: String) =
            AlbumInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ARTIST, artistName)
                    putString(ARG_ALBUM, albumName)
                }
            }
    }
}

package com.example.lastfm.view


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lastfm.apidataprovider.DataProviderImpl
import com.example.lastfm.models.albumSearchResults.SearchResults
import com.example.lastfm.viewmodels.SearchAlbumViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search_album.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.lastfm.R

/**
 *  Fragment for Searching of Album and showing up the search results
 */
class SearchAlbumFragment : Fragment(), SearchResultsAdapter.ClickListener {

    @Inject
    lateinit var dataProviderImpl: DataProviderImpl
    @Inject
    lateinit var searchAlbumViewModel: SearchAlbumViewModel
    lateinit var searchResultsAdapter: SearchResultsAdapter
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var albumInfoFragment: AlbumInfoFragment

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        setupAdapter()

        searchAlbumViewModel.searchResultsMutableLiveData.observe(this, Observer { searchResults ->

            searchResults?.let {
                searchResultsAdapter.setData(searchAlbumViewModel.getAlbumsData(it))
                searchResultsAdapter.notifyDataSetChanged()
            }

        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                //clear the focus so that keyBoard won't pop-up upon service call results
                searchView.clearFocus()
                query?.let {
                    (requireActivity() as MainActivity).showProgress()
                    compositeDisposable.add(
                        searchAlbumViewModel.getSearchQueryResults(it.trim())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(object : DisposableSingleObserver<SearchResults>() {
                                override fun onSuccess(searchResults: SearchResults) {
                                    (requireActivity() as MainActivity).hideProgress()
                                    searchAlbumViewModel.searchResultsMutableLiveData.value =
                                        searchResults
                                }

                                override fun onError(error: Throwable) {
                                    (requireActivity() as MainActivity).hideProgress()
                                    error.message?.let { it ->
                                        Log.e(TAG, it)
                                        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG)
                                            .show()
                                    }

                                    searchAlbumViewModel.errorResponseLiveData.value = error
                                    searchAlbumViewModel.searchResultsMutableLiveData.value = null
                                }
                            })
                    )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


    private fun setupAdapter() {
        searchResultsAdapter = SearchResultsAdapter(this@SearchAlbumFragment)

        resultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchResultsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onClick(artistName: String, albumName: String) {
        albumInfoFragment = AlbumInfoFragment.newInstance(artistName, albumName)

        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.apply {
            replace(R.id.container, albumInfoFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        const val TAG = "SearchAlbumFragment"
        fun newInstance() = SearchAlbumFragment()
    }
}

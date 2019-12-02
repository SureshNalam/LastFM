package com.example.lastfm.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.lastfm.R
import com.example.lastfm.models.albumSearchResults.Album

/**
 * Adapter to Display Album SearchResults in the RecyclerView
 *  in the form of List
 */

class SearchResultsAdapter(private val clickListenr: ClickListener) :
    RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    private var albumList = listOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_item_view, parent, false)
        return SearchResultsViewHolder(view)
    }

    override fun getItemCount() = albumList.size

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {

        val data = albumList[position]
        holder.bindData(data)
        holder.itemView.setOnClickListener {
            clickListenr.onClick(data.artist, data.name)
        }
    }

    fun setData(albumsList: List<Album>) {
        this.albumList = albumsList
    }


    class SearchResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val albumImage = itemView.findViewById<AppCompatImageView>(R.id.albumImage)
        private val albumName = itemView.findViewById<AppCompatTextView>(R.id.albumName)
        private val albumArtist = itemView.findViewById<AppCompatTextView>(R.id.artistName)

        fun bindData(data: Album) {
            albumName.text = data.name
            albumArtist.text = data.artist


            itemView.context?.let {
                Glide.with(it)
                    .load(data.image[IMG_INDEX].text)
                    .apply(
                        RequestOptions().override(
                            albumImage.drawable.intrinsicWidth,
                            albumImage.drawable.intrinsicHeight
                        ).placeholder(R.mipmap.ic_launcher_round)
                    )
                    .into(albumImage)
            }
        }
    }

    interface ClickListener {
        fun onClick(artistName: String, albumName: String) {
        }
    }

    companion object{
        const val IMG_INDEX = 2
    }

}


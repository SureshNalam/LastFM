package com.example.lastfm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastfm.apidataprovider.DataProviderImpl
import com.example.lastfm.models.albumSearchResults.Album
import com.example.lastfm.models.albumSearchResults.SearchResults
import io.reactivex.Single
import javax.inject.Inject

class SearchAlbumViewModel @Inject constructor(private val dataProviderImpl: DataProviderImpl) :
    ViewModel() {

    val searchResultsMutableLiveData: MutableLiveData<SearchResults> = MutableLiveData()
    val errorResponseLiveData: MutableLiveData<Throwable> = MutableLiveData()

    fun getSearchQueryResults(query: String): Single<SearchResults> {
        return dataProviderImpl.getAlbumSearchResults(query)
    }

    fun getAlbumsData(searchResults: SearchResults): List<Album> {
        return searchResults.results.albummatches.album
    }
}
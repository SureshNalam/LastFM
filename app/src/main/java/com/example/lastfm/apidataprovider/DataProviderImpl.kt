package com.example.lastfm.apidataprovider

import com.example.lastfm.webservice.WebAPI
import com.example.lastfm.models.albumInfo.AlbumInfo
import com.example.lastfm.models.albumSearchResults.SearchResults
import io.reactivex.Single
import javax.inject.Inject

class DataProviderImpl @Inject constructor(private val webAPI: WebAPI) : DataProviderInterface {

    override fun getAlbumSearchResults(query: String): Single<SearchResults> {
        return webAPI.getAlbumSearchResults(
            WebAPI.PARAM_VALUE_ALBUM_SEARCH,
            query,
            WebAPI.PARAM_VALUE_API_KEY,
            WebAPI.PARAM_VALUE_JSON
        )
    }

    override fun getAlbumDetails(artistName: String, albumName: String): Single<AlbumInfo> {
        return webAPI.getAlbumDetails(
            WebAPI.PARAM_VALUE_GET_INFO,
            WebAPI.PARAM_VALUE_API_KEY,
            artistName,
            albumName,
            WebAPI.PARAM_VALUE_JSON
        )
    }
}
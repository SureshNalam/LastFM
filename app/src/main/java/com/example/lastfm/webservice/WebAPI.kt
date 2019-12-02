package com.example.lastfm.webservice

import com.example.lastfm.models.albumInfo.AlbumInfo
import com.example.lastfm.models.albumSearchResults.SearchResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WebAPI {

    @GET(".")
    fun getAlbumSearchResults(
        @Query(PARAM_METHOD) searchMethod: String,
        @Query(PARAM_ALBUM) albumName: String,
        @Query(PARAM_API_KEY) api_key: String,
        @Query(PARAM_FORMAT) format: String
    ): Single<SearchResults>


    @GET(".")
    fun getAlbumDetails(
        @Query(PARAM_METHOD) artistInfo: String,
        @Query(PARAM_API_KEY) api_key: String,
        @Query(PARAM_ARTIST) artistName: String,
        @Query(PARAM_ALBUM) albumName: String,
        @Query(PARAM_FORMAT) format: String
    ): Single<AlbumInfo>


    companion object{
        const val BASE_URL = "http://ws.audioscrobbler.com/2.0/"
        const val PARAM_METHOD = "method"
        const val PARAM_ALBUM = "album"
        const val PARAM_API_KEY = "api_key"
        const val PARAM_ARTIST = "artist"
        const val PARAM_FORMAT = "format"
        const val PARAM_VALUE_ALBUM_SEARCH = "album.search"
        const val PARAM_VALUE_GET_INFO = "album.getinfo"
        const val PARAM_VALUE_API_KEY = ""
        const val PARAM_VALUE_JSON = "json"

    }
}
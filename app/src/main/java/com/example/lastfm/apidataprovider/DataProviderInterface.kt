package com.example.lastfm.apidataprovider

import com.example.lastfm.models.albumInfo.AlbumInfo
import com.example.lastfm.models.albumSearchResults.SearchResults
import io.reactivex.Single

interface DataProviderInterface {
    fun getAlbumSearchResults(query: String): Single<SearchResults>
    fun getAlbumDetails(artistName: String, albumName: String): Single<AlbumInfo>
}
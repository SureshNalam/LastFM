package com.example.lastfm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastfm.apidataprovider.DataProviderImpl
import com.example.lastfm.models.albumInfo.AlbumInfo
import io.reactivex.Single
import javax.inject.Inject

/**
 * ViewModel class for the AlbumInfo
 */
class AlbumInfoViewModel @Inject constructor(private val dataProviderImpl: DataProviderImpl) : ViewModel() {

    var albumInfo =  MutableLiveData<AlbumInfo>()

    fun getAlbumInfo(album: String, artist: String): Single<AlbumInfo> {
        return dataProviderImpl.getAlbumDetails(artist, album)
    }

    fun getAlbumName(info: AlbumInfo) = info.album.name
    fun getArtistName(info: AlbumInfo) = info.album.artist
    fun getPublishDate(info: AlbumInfo):String?{
        return info.album.wiki?.published
    }
    fun getSummary(info: AlbumInfo):String?{
        return info.album.wiki?.summary
    }
}
package com.example.lastfm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lastfm.apidataprovider.DataProviderImpl
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * Factory class for ViewModels
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(dataProviderImpl: DataProviderImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass === SearchAlbumViewModel::class -> modelClass as T
            modelClass === AlbumInfoViewModel::class -> modelClass as T
            else -> throw IllegalArgumentException("unable to find ViewModel class")
        }
    }
}
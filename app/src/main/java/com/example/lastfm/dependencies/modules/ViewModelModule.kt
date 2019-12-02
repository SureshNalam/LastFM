package com.example.lastfm.dependencies.modules

import com.example.lastfm.apidataprovider.DataProviderImpl
import com.example.lastfm.viewmodels.AlbumInfoViewModel
import com.example.lastfm.viewmodels.SearchAlbumViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun getAlbumSearchViewModel(dataProviderImpl: DataProviderImpl): SearchAlbumViewModel {
        return SearchAlbumViewModel(dataProviderImpl)
    }

    @Provides
    fun getAlbumInfoViewModel(dataProviderImpl: DataProviderImpl): AlbumInfoViewModel{
        return AlbumInfoViewModel(dataProviderImpl)
    }
}
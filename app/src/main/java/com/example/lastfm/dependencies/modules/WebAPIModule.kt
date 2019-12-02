package com.example.lastfm.dependencies.modules

import com.example.lastfm.apidataprovider.DataProviderImpl
import com.example.lastfm.webservice.WebAPI
import dagger.Module
import dagger.Provides

@Module
class WebAPIModule {

    @Provides
    fun getDataProvider(webAPI: WebAPI): DataProviderImpl {
        return DataProviderImpl(webAPI)
    }
}
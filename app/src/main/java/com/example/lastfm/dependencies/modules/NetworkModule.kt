package com.example.lastfm.dependencies.modules

import com.example.lastfm.webservice.WebAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun getRetrofitInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(WebAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun getWebServiceAPI(): WebAPI = getRetrofitInstance().create(WebAPI::class.java)
}
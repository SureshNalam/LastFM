package com.example.lastfm.dependencies.component

import com.example.lastfm.view.MainActivity
import com.example.lastfm.view.SearchAlbumFragment
import com.example.lastfm.dependencies.modules.NetworkModule
import com.example.lastfm.dependencies.modules.ViewModelModule
import com.example.lastfm.dependencies.modules.WebAPIModule
import com.example.lastfm.view.AlbumInfoFragment
import dagger.Component

@Component(modules = [NetworkModule::class, WebAPIModule::class , ViewModelModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(searchAlbumFragment: SearchAlbumFragment)
    fun inject(albumInfoFragment: AlbumInfoFragment)

}
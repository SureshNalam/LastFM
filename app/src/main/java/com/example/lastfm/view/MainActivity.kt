package com.example.lastfm.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lastfm.MyApplication
import com.example.lastfm.R
import com.example.lastfm.dependencies.component.AppComponent
import com.example.lastfm.util.ProgressIndicator

/**
 *  Launcher Activity ,
 *  contains fragments to show AlbumSearch Results and Album Information
 */
class MainActivity : AppCompatActivity() {

    lateinit var appComponent: AppComponent
    private lateinit var dialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchAlbumFragment.newInstance())
                .commitNow()
        }

        dialog = ProgressIndicator.progressDialog(this@MainActivity)
    }

    fun showProgress() {
        dialog.show()
    }
    fun hideProgress() {
        dialog.dismiss()
    }

}


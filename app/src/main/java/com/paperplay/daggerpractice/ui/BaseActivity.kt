package com.paperplay.daggerpractice.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.paperplay.daggerpractice.di.SessionManager
import com.paperplay.daggerpractice.ui.auth.AuthActivity
import com.paperplay.daggerpractice.network.state.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Class ini akan digunakan sebagai parent di semua activity agar bisa subscribe SessionManager
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    companion object {
        private const val TAG = "BaseActivity"
    }
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager.observeLiveDataUser().observe(this, Observer {
            when(it.status){
                AuthResource.AuthStatus.LOADING -> {
                }
                AuthResource.AuthStatus.AUTHENTICATED -> {
                }
                AuthResource.AuthStatus.ERROR -> {
                }
                AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                    Log.d(TAG, "Not authenticated")
                    val intent = Intent(this@BaseActivity, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }

}
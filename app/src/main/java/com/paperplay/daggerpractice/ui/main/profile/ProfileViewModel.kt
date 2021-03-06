package com.paperplay.daggerpractice.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.paperplay.daggerpractice.di.SessionManager
import com.paperplay.daggerpractice.data.model.response.User
import com.paperplay.daggerpractice.data.source.remote.state.AuthResource
import javax.inject.Inject

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
class ProfileViewModel @Inject constructor(val sessionManager: SessionManager): ViewModel() {
    fun observeSession(): LiveData<AuthResource<User>>{
        return sessionManager.observeLiveDataUser()
    }
}
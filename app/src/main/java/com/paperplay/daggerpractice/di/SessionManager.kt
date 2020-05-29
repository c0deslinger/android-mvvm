package com.paperplay.daggerpractice.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.paperplay.daggerpractice.data.model.response.User
import com.paperplay.daggerpractice.data.source.remote.state.AuthResource
import com.paperplay.suteraemas.utils.PreferenceUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class ini digunakan untuk manage session disemua activity
 */
@Singleton
class SessionManager @Inject constructor() {

    @Inject
    lateinit var pref : PreferenceUtils

    /**
     * mediator adalah turunan class dari LiveData yg berfungsi untuk mediasi lebih dari 1 LiveData
     */
    private val mediatorLiveDataUser = MediatorLiveData<AuthResource<User>>()

    /**
     * lakukan proses authentikasi dg menampung result ke mediatorLiveDataUser
     */
    fun authenticateWithId(source: LiveData<AuthResource<User>>){
        //set value pertama dg loading dan data user empty
        mediatorLiveDataUser.value = AuthResource.loading(User())

        //set value dari livedata source
        mediatorLiveDataUser.addSource(source, object : Observer<AuthResource<User>> {
            override fun onChanged(user: AuthResource<User>?) {
                mediatorLiveDataUser.value = user
                mediatorLiveDataUser.removeSource(source)
            }
        })
    }

    /**
     * Observasi stream mediatorLiveDataUser
     */
    fun observeLiveDataUser(): LiveData<AuthResource<User>>{
        return mediatorLiveDataUser
    }

    fun logout(){
        mediatorLiveDataUser.value = AuthResource.logout()
        pref.setSharedPreferences(PreferenceUtils.PrefKey.LOGIN_ROLE, "-")
    }
}
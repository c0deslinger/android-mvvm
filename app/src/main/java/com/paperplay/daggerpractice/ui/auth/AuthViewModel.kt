package com.paperplay.daggerpractice.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.paperplay.daggerpractice.di.SessionManager
import com.paperplay.daggerpractice.model.User
import com.paperplay.daggerpractice.network.AuthApi
import com.paperplay.daggerpractice.network.state.AuthResource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * lakukan inject constructor,
 * authApi akan di provide dari AuthModule
 * sessionManager akan di handle dari class SessionManager
 */
class AuthViewModel @Inject constructor(private var authApi: AuthApi,
                                        var sessionManager: SessionManager) : ViewModel() {

    init {
        Log.d("AuthViewModel", "Success Injected")
    }

    fun authenticateWithId(idUser: Int){
        sessionManager.authenticateWithId(callApi(idUser))
    }

    private fun callApi(idUser: Int): LiveData<AuthResource<User>> {
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(idUser)
                //handling agar jika error, tetap return object User
                .onErrorReturn {
                    val errorUser = User()
                    errorUser.id = -1
                    return@onErrorReturn errorUser
                }
                //convert object User ke AuthResource<User>
                .map {
                    if(it.id == -1){
                        return@map AuthResource.error("Error couldn't auth", it)
                    }else{
                        return@map AuthResource.authenticated(it)
                    }
                }
                .subscribeOn(Schedulers.io()))
    }

    /**
     * observer mediator dari session manager ke dalam bentuk LiveData agar bisa diconsume dari activity
     */
    fun observeUser(): LiveData<AuthResource<User>>{
        return sessionManager.observeLiveDataUser()
    }
}
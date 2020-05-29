package com.paperplay.daggerpractice.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.paperplay.daggerpractice.di.SessionManager
import com.paperplay.daggerpractice.data.model.response.User
import com.paperplay.daggerpractice.data.source.remote.AuthApi
import com.paperplay.daggerpractice.data.source.remote.state.AuthResource
import com.paperplay.suteraemas.utils.PreferenceUtils
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * lakukan inject constructor,
 * authApi akan di provide dari AuthModule
 * sessionManager akan di handle dari class SessionManager
 */
class AuthViewModel @Inject constructor(private var authApi: AuthApi,
                                        var sessionManager: SessionManager,
                                        var preferenceUtils: PreferenceUtils) : ViewModel() {
    init {
        Log.d("AuthViewModel", "Success Injected")
    }

    fun isAlreadyLoggedIn(): Boolean{
        val json = preferenceUtils.getSharedPreferences(PreferenceUtils.PrefKey.LOGIN_ROLE)
        if(json.equals("-")){
            return false
        }
        val user = Gson().fromJson(json, User::class.java)
        val liveData = MutableLiveData<AuthResource<User>>()
        liveData.postValue(AuthResource.authenticated(user))
        sessionManager.authenticateWithId(liveData)
        return true
    }

    fun authenticateWithId(idUser: Int){
        sessionManager.authenticateWithId(callApi(idUser))
    }

    private fun callApi(idUser: Int): LiveData<AuthResource<User>> {
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(idUser)
                //handling agar jika error, tetap return object User
                .onErrorReturn {
                    val errorUser =
                        User()
                    errorUser.id = -1
                    return@onErrorReturn errorUser
                }
                //convert object User ke AuthResource<User>
                .map {
                    if(it.id == -1){
                        return@map AuthResource.error("Error couldn't auth", it)
                    }else{
                        val authenticated = AuthResource.authenticated(it)
                        //save to shared preference
                        preferenceUtils.setSharedPreferences(PreferenceUtils.PrefKey.LOGIN_ROLE, Gson().toJson(it))
                        return@map authenticated
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
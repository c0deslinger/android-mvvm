package com.paperplay.daggerpractice.data.source.remote

import com.paperplay.daggerpractice.data.model.response.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Ahmed Yusuf on 09/05/20.
 */
interface AuthApi {

    /**
     * Pakai flowable karena cuma Flowable yg bisa di convert ke LiveData
     */
    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Flowable<User>
}
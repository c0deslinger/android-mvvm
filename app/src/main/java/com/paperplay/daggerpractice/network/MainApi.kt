package com.paperplay.daggerpractice.network

import com.paperplay.daggerpractice.model.Comment
import com.paperplay.daggerpractice.model.Post
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
interface MainApi {
    @GET("posts")
    fun getPost(@Query("userId") id: Int): Observable<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(
        @Path("id") id: Int
    ): Observable<List<Comment>>
}
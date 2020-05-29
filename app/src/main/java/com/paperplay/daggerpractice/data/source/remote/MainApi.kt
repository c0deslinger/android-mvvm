package com.paperplay.daggerpractice.data.source.remote

import com.paperplay.daggerpractice.data.model.response.Comment
import com.paperplay.daggerpractice.data.model.response.PostResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
interface MainApi {
    @GET("posts")
    fun getPost(@Query("userId") id: Int): Observable<List<PostResponse>>

    @GET("posts/{id}/comments")
    fun getComments(
        @Path("id") id: Int
    ): Observable<List<Comment>>
}
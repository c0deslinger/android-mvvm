package com.paperplay.daggerpractice.data.repository

import com.paperplay.daggerpractice.model.Post
import com.paperplay.daggerpractice.network.MainApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


/**
 * Created by Ahmed Yusuf on 12/05/20.
 */
class PostRepository @Inject constructor(private val mainApi: MainApi) {

    fun getPosts(id: Int) : Observable<List<Post>> {
        return mainApi.getPost(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getComments(post: Post): Observable<Post>{
        return mainApi.getComments(post.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                post.comment = it
                return@map post
            }
    }
}
package com.paperplay.daggerpractice.data.source

import android.util.Log
import com.paperplay.daggerpractice.data.mapper.PostMapper
import com.paperplay.daggerpractice.data.model.table.PostsTable
import com.paperplay.daggerpractice.data.source.local.PostDao
import com.paperplay.daggerpractice.data.source.remote.MainApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


/**
 * Created by Ahmed Yusuf on 12/05/20.
 */
class PostRepository @Inject constructor(private val mainApi: MainApi,
                                         private val postMapper: PostMapper,
                                         private val postDao: PostDao) {

    /**
     * Get posts from API
     */
    fun getApiPosts(id: Int): Observable<MutableList<PostsTable>> {
        return mainApi.getPost(id)
            .subscribeOn(Schedulers.io())
            .flatMapIterable { it }
            .map { postMapper.mapToEntity(it) }
            .doOnNext {postDao.insertPost(it)}
            .toList()
            .toObservable()
    }

    /**
     * Get posts from DB
     */
    fun getDbPosts(id: Int): Observable<MutableList<PostsTable>> {
        return postDao.getPost()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                //Print log it.size :)
                Log.d("Total fetch data DB:  ", it.size.toString())
            }
    }

    /**
     * Get comments from API then map into PostsTable
     */
    fun getApiComments(postsTable: PostsTable): Observable<PostsTable>{
        return mainApi.getComments(postsTable.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                postsTable.comments_count = it.size
                return@map postsTable
            }
    }
}
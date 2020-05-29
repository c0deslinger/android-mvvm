package com.paperplay.daggerpractice.ui.main.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paperplay.daggerpractice.data.model.table.PostsTable
import com.paperplay.daggerpractice.data.source.PostRepository
import com.paperplay.daggerpractice.data.source.remote.state.Resource
import com.paperplay.daggerpractice.di.SessionManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
class PostViewModel @Inject constructor(private val repository: PostRepository,
                                        private val sessionManager: SessionManager): ViewModel() {
    companion object {
        private const val TAG = "PostViewModel"
    }

    private val disposable = CompositeDisposable()
    var posts = MutableLiveData<Resource<MutableList<PostsTable>>>()
    var error: MutableLiveData<String> = MutableLiveData()

    /**
     * Observe data Post dan Comment secara bersamaan menggunakan connectable disposable
     */
    fun observePost(): LiveData<Resource<MutableList<PostsTable>>> {
        posts.postValue(Resource.loading(mutableListOf()))

        sessionManager.observeLiveDataUser().value?.data?.id?.let { it ->
            val postObserver: ConnectableObservable<MutableList<PostsTable>> = getPosts(it).replay()
            var postResponse: MutableList<PostsTable> = mutableListOf()
            //fetch data post
            disposable.add(
                postObserver.subscribeWith(object : DisposableObserver<MutableList<PostsTable>>(){
                    override fun onComplete() {}
                    override fun onNext(data: MutableList<PostsTable>) {
                        postResponse = data
                        posts.postValue(Resource.success(postResponse))
                    }
                    override fun onError(e: Throwable) {
                        posts.postValue(Resource.error(e.message!!, mutableListOf()))
                    }
                })
            )
            //fetch data comment
            disposable.add(
                postObserver.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap {
                        //ubah ke bentuk iterable
                        Observable.fromIterable(it)
                    }.flatMap {
                        //map ke post yang sudah diisi comment
                        repository.getApiComments(it)
                    }
                    .subscribeWith(object : DisposableObserver<PostsTable>(){
                        override fun onComplete() {}
                        override fun onNext(t: PostsTable) {
                            for(data in postResponse){
                                if(data.id == t.id){
                                    data.comments_count = t.comments_count
                                    break
                                }
                            }
                            posts.postValue(Resource.success(postResponse))
                        }
                        override fun onError(e: Throwable) {
                            Log.e(TAG, "onError: ", e)
                        }
                    })
            )
            postObserver.connect()
        }
        return posts
    }

    /**
     * Get post dari API dan DB kemudian lakukan concat untuk merging data dari kedua resource
     */
    private fun getPosts(id: Int): Observable<MutableList<PostsTable>> {
        val observableFromApi = repository.getApiPosts(id)
        val observableFromDb = repository.getDbPosts(id)
        return Observable.concatArrayEager(observableFromDb, observableFromApi.materialize()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                // jika terjadi error, lakukan handling di livedata yg berbeda agar tidak mengganggu stream
                it.error?.let {
                    error.postValue("Error: "+it.message)
                }
                // kembalikan item ke stream
                it
            }
            .filter{ !it.isOnError }
//            .dematerialize<MutableList<PostsTable>>()
            .dematerialize { it }
                //lakukan debounce agar tidak terjadi ui flicker
            .debounce(400, TimeUnit.MILLISECONDS))
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
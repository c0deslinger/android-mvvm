package com.paperplay.daggerpractice.ui.main.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paperplay.daggerpractice.data.repository.PostRepository
import com.paperplay.daggerpractice.di.SessionManager
import com.paperplay.daggerpractice.model.Post
import com.paperplay.daggerpractice.network.state.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
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
    var posts = MutableLiveData<Resource<List<Post>>>()

    fun observePost(): LiveData<Resource<List<Post>>> {
        posts.postValue(Resource.loading(listOf()))
        sessionManager.observeLiveDataUser().value?.data?.id?.let { it ->
            val postObserver : ConnectableObservable<List<Post>> = repository.getPosts(it).replay()
            var postResponse: List<Post> = listOf()
            //fetch data post
            disposable.add(
                postObserver.subscribeWith(object : DisposableObserver<List<Post>>(){
                        override fun onComplete() {
                            posts.postValue(Resource.success(postResponse))
                        }

                        override fun onNext(data: List<Post>) {
                            postResponse = data
                        }

                        override fun onError(e: Throwable) {
                            posts.postValue(Resource.error(e.message!!, listOf()))
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
                        repository.getComments(it)
                    }
                    .subscribeWith(object : DisposableObserver<Post>(){
                        override fun onComplete() {
                            Log.d(TAG, "onComplete: "+postResponse)
                            posts.postValue(Resource.success(postResponse))
                        }
                        override fun onNext(t: Post) {
                            for(data in postResponse){
                                if(data.id == t.id){
                                    data.comment = t.comment
                                    break
                                }
                            }
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

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
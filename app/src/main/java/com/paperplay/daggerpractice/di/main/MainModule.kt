package com.paperplay.daggerpractice.di.main

import com.paperplay.daggerpractice.di.MainScope
import com.paperplay.daggerpractice.network.MainApi
import com.paperplay.daggerpractice.ui.main.post.PostRecyclerAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Module ini digunakan untuk provide semua dependency seperti retrofit yg ada di Main
 */
@Module
class MainModule {
    @MainScope
    @Provides
    fun provideMainApi(@Named("base_url") retrofit: Retrofit): MainApi{
        return retrofit.create(MainApi::class.java)
    }

    @MainScope
    @Provides
    fun providePostRecycler(): PostRecyclerAdapter{
        return PostRecyclerAdapter()
    }
}
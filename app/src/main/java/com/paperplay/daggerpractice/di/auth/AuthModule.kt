package com.paperplay.daggerpractice.di.auth

import com.paperplay.daggerpractice.di.AuthScope
import com.paperplay.daggerpractice.network.AuthApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * siapkan instance untuk retrofit call AuthApi
 * instance retrofit sudah disediakan di AppModule
 */
@Module
class AuthModule {
    @AuthScope
    @Provides
    fun provideAuthApi(@Named("base_url") retrofit: Retrofit): AuthApi{
        return retrofit.create(AuthApi::class.java)
    }
}
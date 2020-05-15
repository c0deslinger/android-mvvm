package com.paperplay.daggerpractice.di

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.paperplay.daggerpractice.R
import com.paperplay.daggerpractice.utils.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Siapkan semua kebutuhan level App. misal:
 * Retrofit, Glide
 */
@Module
class AppModule {
    @Module
   companion object{
        @Singleton
        @JvmStatic
        @Provides
        @Named("base_url")
        fun provideBaseUrlWebservice(): Retrofit{
            return createWebService(Constant.BASE_URL)
        }

        @Singleton
        @JvmStatic
        @Provides
        fun provideGlideRequestOptions(): RequestOptions{
            return RequestOptions.placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background)
        }

        @Singleton
        @JvmStatic
        @Provides
        fun provideGlideRequestManager(application: Application, requestOptions: RequestOptions): RequestManager{
            return Glide.with(application).setDefaultRequestOptions(requestOptions)
        }

        /** Get drawable from project resource */
        @Singleton
        @JvmStatic
        @Provides
        fun provideAppDrawable(application: Application): Drawable{
             return ContextCompat.getDrawable(application, R.drawable.logo)!!
        }

        /**
         * Create Retrofit webservice
         */
        private fun createWebService(url: String): Retrofit{
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
   }

}

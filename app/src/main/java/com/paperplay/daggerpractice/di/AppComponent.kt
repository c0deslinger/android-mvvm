package com.paperplay.daggerpractice.di

import android.app.Application
import com.paperplay.daggerpractice.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Ahmed Yusuf on 07/05/20.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
                    AppModule::class,
                    ActivityBuilderModule::class,
                    ViewModelFactoryModule::class])
interface AppComponent: AndroidInjector<BaseApplication> {

    val sessionManager: SessionManager

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun setApplication(application: Application): Builder

        fun build(): AppComponent
    }
}
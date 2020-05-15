package com.paperplay.daggerpractice

import com.paperplay.daggerpractice.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by Ahmed Yusuf on 07/05/20.
 */
class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().setApplication(this).build()
    }
}
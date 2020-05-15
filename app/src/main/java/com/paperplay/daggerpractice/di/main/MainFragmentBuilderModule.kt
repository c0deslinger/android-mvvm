package com.paperplay.daggerpractice.di.main

import com.paperplay.daggerpractice.ui.main.post.PostFragment
import com.paperplay.daggerpractice.ui.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module ini digunakan untuk provide kebutuhan2 semua fragment yg ada di MainActivity,
 * agar bisa extends DaggerFragment
 */
@Module
abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributePostFragment(): PostFragment
}
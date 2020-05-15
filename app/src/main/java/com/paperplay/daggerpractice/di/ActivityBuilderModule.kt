package com.paperplay.daggerpractice.di

import com.paperplay.daggerpractice.di.auth.AuthModule
import com.paperplay.daggerpractice.di.auth.AuthViewModelModule
import com.paperplay.daggerpractice.di.main.MainFragmentBuilderModule
import com.paperplay.daggerpractice.di.main.MainModule
import com.paperplay.daggerpractice.di.main.MainViewModelModule
import com.paperplay.daggerpractice.ui.auth.AuthActivity
import com.paperplay.daggerpractice.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module ini digunakan agar activity bisa extends DaggerAppCompatActivity
 */
@Module
abstract class ActivityBuilderModule {
    /* Siapkan AuthActivity dg semua module (cara kerjanya sama spt subcomponent) */
    @AuthScope
    @ContributesAndroidInjector(modules = [AuthViewModelModule::class, AuthModule::class])
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuilderModule::class, MainViewModelModule::class, MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
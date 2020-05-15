package com.paperplay.daggerpractice.di

import androidx.lifecycle.ViewModelProvider
import com.paperplay.daggerpractice.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * class ini yg bertanggung jawab menyiapkan instance viewModelFactory
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}
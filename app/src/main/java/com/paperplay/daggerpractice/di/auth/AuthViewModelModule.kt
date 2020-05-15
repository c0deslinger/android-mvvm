package com.paperplay.daggerpractice.di.auth

import androidx.lifecycle.ViewModel
import com.paperplay.daggerpractice.di.ViewModelKey
import com.paperplay.daggerpractice.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * digunakan untuk inject viewmodel dg key-value di Auth
 */
@Module
abstract class AuthViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel
}
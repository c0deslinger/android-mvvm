package com.paperplay.daggerpractice.di.main

import androidx.lifecycle.ViewModel
import com.paperplay.daggerpractice.di.ViewModelKey
import com.paperplay.daggerpractice.ui.main.post.PostViewModel
import com.paperplay.daggerpractice.ui.main.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module ini digunakan untuk provide semua viewmodel yg ada di Main
 */
@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(postViewModel: PostViewModel): ViewModel
}
package com.example.notforredditv2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notforredditv2.viewmodel.CommentViewModel
import com.example.notforredditv2.viewmodel.PostViewModel
import com.example.notforredditv2.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(postViewModel: PostViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel::class)
    abstract fun bindCommentViewModel(commentViewModel: CommentViewModel) : ViewModel

    abstract fun bindViewModelFactory(factory : ViewModelFactory) : ViewModelProvider.Factory

}
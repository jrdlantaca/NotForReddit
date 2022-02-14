package com.example.notforredditv2.di

import com.example.notforredditv2.ui.fragment.HomeFragment
import com.example.notforredditv2.ui.fragment.LinkFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment() : HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeLinkFragment() : LinkFragment

}
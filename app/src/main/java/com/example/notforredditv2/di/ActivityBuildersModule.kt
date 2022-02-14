package com.example.notforredditv2.di

import com.example.notforredditv2.ui.AuthActivity
import com.example.notforredditv2.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeAuthActivity() : AuthActivity

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [FragmentBuildersModule::class]
    )
    abstract fun contributeHomeActivity() : HomeActivity

}
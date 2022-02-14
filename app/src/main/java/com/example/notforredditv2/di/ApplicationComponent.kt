package com.example.notforredditv2.di

import android.app.Application
import com.example.notforredditv2.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(modules = [ActivityBuildersModule::class, AndroidInjectionModule::class, RetrofitModule::class])
interface ApplicationComponent : AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}
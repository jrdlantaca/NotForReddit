package com.example.notforredditv2.di

import android.app.Application
import androidx.room.Room
import com.example.notforredditv2.ServiceInterceptor
import com.example.notforredditv2.db.PostDao
import com.example.notforredditv2.db.RedditDatabase
import com.example.notforredditv2.api.OAuthService
import com.example.notforredditv2.db.CommentDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = [ViewModelModule::class])
class RetrofitModule {

    @Provides
    @ApplicationScope
    fun provideApi(retrofit : Retrofit) : OAuthService{
        return retrofit.create(OAuthService::class.java)
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi : Moshi) : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://oauth.reddit.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideMoshi() : Moshi{
        return Moshi.Builder().build()
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, serviceInterceptor : ServiceInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(serviceInterceptor)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @ApplicationScope
    fun provideDb(app : Application) : RedditDatabase{
        return Room
            .databaseBuilder(app, RedditDatabase::class.java, "reddit.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @ApplicationScope
    fun providePostDao(db : RedditDatabase) : PostDao{
        return db.postDao()
    }

    @Provides
    @ApplicationScope
    fun provideCommentDao(db : RedditDatabase) : CommentDao{
        return db.commentDao()
    }

}
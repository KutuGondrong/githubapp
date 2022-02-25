package com.kutugondrong.github.di

import android.content.Context
import com.kutugondrong.data.interceptor.AppTokenInterceptor
import com.kutugondrong.data.remote.service.GithubApi
import com.kutugondrong.github.AppGithubKG
import com.kutugondrong.github.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesApplication(@ApplicationContext context: Context): AppGithubKG {
        return context as AppGithubKG
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

        val appTokenInterceptor = AppTokenInterceptor(BuildConfig.APP_TOKEN)

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(appTokenInterceptor)
            .build()
    }



    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideUserGithubApi(retrofit: Retrofit): GithubApi =
        retrofit.create(GithubApi::class.java)

}
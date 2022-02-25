package com.kutugondrong.github.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.kutugondrong.data.interceptor.AppTokenInterceptor
import com.kutugondrong.data.remote.service.GithubApi
import com.kutugondrong.github.BuildConfig
import com.kutugondrong.github.screen.detail.DetailUserFragment
import com.kutugondrong.github.screen.home.HomeFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@ExperimentalCoroutinesApi
class GithubFragmentFactoryAndroidTest @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            HomeFragment::class.java.name -> HomeFragment()
            DetailUserFragment::class.java.name -> DetailUserFragment()
            else -> super.instantiate(classLoader, className)
        }

    }
}


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
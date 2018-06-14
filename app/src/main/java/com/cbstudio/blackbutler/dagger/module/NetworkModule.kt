package com.cbstudio.blackbutler.dagger.module

import com.cbstudio.blackbutler.dagger.annotation.PerApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
@Module
class NetworkModule {

    @PerApplication
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .create()
    }

    @PerApplication
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }

    @PerApplication
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl("127.0.0.1:5000")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
        return builder.build()
    }

}
package com.cbstudio.blackbutler.module

import android.content.Context
import com.cbstudio.blackbutler.BlackButlerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
@Module
class AppModule {

    @Inject
    lateinit var app: BlackButlerApplication

    @Singleton
    @Provides
    fun provideApplication(): BlackButlerApplication = app


    @Singleton
    @Provides
    fun provideApplicationContext(): Context = app.applicationContext
}
package com.cbstudio.blackbutler

import android.app.Application
import com.cbstudio.blackbutler.dagger.component.AppComponent
import com.cbstudio.blackbutler.dagger.module.AppModule
import com.cbstudio.blackbutler.dagger.component.DaggerAppComponent


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
class BlackButlerApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BlackButlerApplication
    }

}
package com.cbstudio

import android.app.Application
import com.cbstudio.blackbutler.module.AppComponent
import com.cbstudio.blackbutler.module.DaggerAppComponent


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
class BlackButlerApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }
}
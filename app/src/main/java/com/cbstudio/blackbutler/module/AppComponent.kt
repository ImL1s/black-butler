package com.cbstudio.blackbutler.module

import com.cbstudio.blackbutler.BlackButlerApplication
import dagger.BindsInstance
import dagger.Component


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */

@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(app: BlackButlerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: BlackButlerApplication): Builder

        fun build(): AppComponent
    }
}
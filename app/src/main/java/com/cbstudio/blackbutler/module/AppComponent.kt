package com.cbstudio.blackbutler.module

import com.cbstudio.blackbutler.BlackButlerApplication
import com.cbstudio.blackbutler.main.search.activity.SearchActivity
import dagger.Component
import javax.inject.Singleton


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(app: BlackButlerApplication)

//    fun inject(appModule: AppModule)

    fun inject(searchActivity: SearchActivity)

    @Component.Builder
    interface Builder {

//        @BindsInstance
//        fun application(application: BlackButlerApplication): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}
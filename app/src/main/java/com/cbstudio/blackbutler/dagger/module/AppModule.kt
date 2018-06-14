package com.cbstudio.blackbutler.dagger.module

import com.cbstudio.blackbutler.BlackButlerApplication
import com.cbstudio.blackbutler.dagger.annotation.PerApplication
import com.cbstudio.blackbutler.manager.ApplicationsInfoManager
import com.cbstudio.blackbutler.manager.IApplicationsInfoManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by ImL1s on 2018/6/5.
 * Description:
 */
@Module
class AppModule(var app: BlackButlerApplication) {

    @PerApplication
    @Provides
    fun provideApplicationsInfoManager(): IApplicationsInfoManager = ApplicationsInfoManager(context = app)
}
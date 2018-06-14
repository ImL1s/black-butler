package com.cbstudio.blackbutler.dagger.component

import com.cbstudio.blackbutler.dagger.annotation.PerActivity
import com.cbstudio.blackbutler.dagger.module.ActivityCommonModule
import dagger.Component


/**
 * Created by ImL1s on 2018/6/14.
 * Description:
 */
@PerActivity
@Component(modules = [ActivityCommonModule::class])
interface ActivityComponent
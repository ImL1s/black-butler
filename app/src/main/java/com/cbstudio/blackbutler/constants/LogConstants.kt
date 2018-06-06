package com.cbstudio.blackbutler.constants

import com.cbstudio.blackbutler.BlackButlerApplication
import com.cbstudio.blackbutler.utils.ApplicationUtils


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */

val APP_PACKAGE_NAME = ApplicationUtils.getPackageName(BlackButlerApplication.instance)
val LOG_TAG_DEBUG = "$APP_PACKAGE_NAME D"
val LOG_TAG_WARRING = "$APP_PACKAGE_NAME W"
val LOG_TAG_ERROR = "$APP_PACKAGE_NAME E"

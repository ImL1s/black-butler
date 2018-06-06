package com.cbstudio.blackbutler.utils

import android.content.Context


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */
object ApplicationUtils {

    fun getApplicationName(context: Context): String {
        val applicationInfo = context.applicationInfo
        val stringId = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context.getString(stringId)
    }

    fun getPackageName(context: Context): String {
        return context.applicationContext.packageName
    }

}
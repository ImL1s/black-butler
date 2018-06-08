package com.cbstudio.blackbutler.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */
fun Intent.startActivity(context: Context) {
    context.startActivity(this)
}

fun Intent.startActivityForResult(activity: Activity, requestCode: Int) {
    activity.startActivityForResult(this, requestCode)
}

fun Intent.startService(context: Context) {
    context.startService(this)
}

fun Intent.stopService(context: Context) {
    context.stopService(this)
}
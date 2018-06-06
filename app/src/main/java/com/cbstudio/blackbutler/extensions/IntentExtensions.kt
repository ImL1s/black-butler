package com.cbstudio.blackbutler.extensions

import android.app.Activity
import android.content.Intent


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */
fun Intent.startActivity(activity: Activity, requestCode: Int) {
    activity.startActivityForResult(this, requestCode)
}
package com.cbstudio.blackbutler.utils

import android.content.Context


/**
 * Created by ImL1s on 2018/6/8.
 * Description:
 */
object DisplayMetricsUtils {

    fun dpFromPx(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }
}
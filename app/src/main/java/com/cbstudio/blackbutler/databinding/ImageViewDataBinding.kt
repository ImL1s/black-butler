package com.cbstudio.blackbutler.databinding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView


/**
 * Created by ImL1s on 2018/6/13.
 * Description:
 */
object ImageViewDataBinding {

    @JvmStatic
    @BindingAdapter("drawable")
    fun setImageViewDrawable(view: ImageView, drawable: Drawable?) {
        if (drawable == null) return
        view.setImageDrawable(drawable)
    }
}
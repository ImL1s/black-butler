package com.cbstudio.blackbutler.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.WindowManager
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.view.CounterView


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */
class FloatViewService : Service() {

    private lateinit var floatView: CounterView

    private val layoutParams: WindowManager.LayoutParams by lazy {
        WindowManager.LayoutParams()
    }

    override fun onCreate() {
        super.onCreate()
        createView()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun createView() {
        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        layoutParams.format = PixelFormat.RGBA_8888
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        layoutParams.gravity = Gravity.START or Gravity.TOP
        layoutParams.x = 0
        layoutParams.y = 0
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        floatView = CounterView(this)
//        floatView.text = "HELLO WORLD"
//        floatView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        windowManager.addView(floatView, layoutParams)
    }

}
package com.cbstudio.blackbutler.main.main.activity

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.databinding.ActivityMainBinding
import com.cbstudio.blackbutler.main.base.activity.BaseActivity
import com.cbstudio.blackbutler.main.main.vm.MainViewModel
import com.cbstudio.blackbutler.services.FloatViewService

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>
(MainViewModel::class, R.layout.activity_main) {

    private lateinit var floatView: TextView

    private val layoutParams: WindowManager.LayoutParams by lazy {
        WindowManager.LayoutParams()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        viewModel.clickSubject
                .subscribe {
                    //                    createView()
                    startService(Intent(this, FloatViewService::class.java))
                }
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
        floatView = TextView(this)
        floatView.text = "HELLO WORLD"
        floatView.setTextColor(resources.getColor(R.color.colorWhite))
        windowManager.addView(floatView, layoutParams)
    }

}

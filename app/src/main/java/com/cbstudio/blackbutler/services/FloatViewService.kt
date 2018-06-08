package com.cbstudio.blackbutler.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.widget.ImageView
import androidx.core.widget.toast
import com.cbstudio.blackbutler.R
import com.cbstudio.blackbutler.extensions.startActivity
import com.cbstudio.blackbutler.main.search.activity.SearchActivity
import com.cbstudio.blackbutler.utils.DisplayMetricsUtils


/**
 * Created by ImL1s on 2018/6/6.
 * Description:
 */
class FloatViewService : Service() {

    companion object {
        const val MOVE_THRESHOLD = 10
        const val FLOAT_VIEW_WIDTH_DP = 35F
        const val FLOAT_VIEW_HEIGHT_DP = 35F
    }

    private lateinit var floatView: ImageView
    private lateinit var windowManager: WindowManager
    private var displayMetrics = DisplayMetrics()

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

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(floatView)
    }


    private fun createView() {
        windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        if (Build.VERSION.SDK_INT > 25)
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        // format
        layoutParams.format = PixelFormat.RGBA_8888

        // flag
//        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
        layoutParams.flags =
                FLAG_NOT_FOCUSABLE
//                or FLAG_NOT_TOUCHABLE

        layoutParams.gravity = Gravity.START or Gravity.TOP
        layoutParams.x = 0
        layoutParams.y = 0
        layoutParams.width = DisplayMetricsUtils.pxFromDp(this, FLOAT_VIEW_WIDTH_DP).toInt()
        layoutParams.height = DisplayMetricsUtils.pxFromDp(this, FLOAT_VIEW_HEIGHT_DP).toInt()
        floatView = ImageView(this)
//        floatView.setBackgroundColor(0xffff0000.toInt())
        floatView.setImageResource(R.drawable.ic_hat)
        windowManager.addView(floatView, layoutParams)

        setOnTouch(windowManager)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnTouch(windowManager: WindowManager) {
        var lastX = 0
        var lastY = 0
        var paramX = layoutParams.x
        var paramY = layoutParams.y
        var isMoved = false

        floatView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isMoved = false
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                    paramX = layoutParams.x
                    paramY = layoutParams.y
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.rawX - lastX
                    val dy = event.rawY - lastY
                    val isMoveX = Math.abs(dx) > MOVE_THRESHOLD
                    val isMoveY = Math.abs(dy) > MOVE_THRESHOLD

                    if (isMoveX)
                        layoutParams.x = (paramX + dx).toInt()
                    if (isMoveY)
                        layoutParams.y = (paramY + dy).toInt()

                    windowManager.updateViewLayout(floatView, layoutParams)

                    if (isMoveX || isMoveY) {
                        isMoved = true
                    }
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    if (!isMoved) {
                        // onClick
                        toast("on click black butler").show()
                        Intent(this,SearchActivity::class.java)
                                .startActivity(this)

                    } else {
                        if (layoutParams.x < displayMetrics.widthPixels / 2) {
                            layoutParams.x = 0
                        } else {
                            layoutParams.x = displayMetrics.widthPixels
                        }
                        windowManager.updateViewLayout(floatView, layoutParams)
                    }
                    return@setOnTouchListener true
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
        }
    }

}
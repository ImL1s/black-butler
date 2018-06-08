package com.cbstudio.blackbutler.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View


/**
 * Created by ImL1s on 2018/6/7.
 * Description: this class is for test.
 */
@Deprecated("this class is for test")
class CounterView(context: Context) : View(context), View.OnClickListener {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bounds: Rect = Rect()
    private var count: Int = 0
    private var statusBarHeight = Math.ceil((25 * context.resources.displayMetrics.density).toDouble())


    init {
//        setOnClickListener(this)
        setBackgroundColor(0xFFFFFF00.toInt())
    }

    override fun onClick(v: View?) {
        ++count
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        Log.e("onMeasure", "width is at most: ${widthMode == MeasureSpec.AT_MOST}")
        Log.e("onMeasure", "height is at most: ${heightMode == MeasureSpec.AT_MOST}")
        Log.e("onMeasure", "onMeasure: x-$widthMode/$widthSize")
        Log.e("onMeasure", "onMeasure: y-$heightMode/$heightSize")
        Log.e("onMeasure", "x:$x y:$y")
//        x = 300F
//        y = 300F
        x = 0F
//        setMeasuredDimension(widthSize, heightSize)
        setMeasuredDimension(500, 500)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLUE
        canvas.drawRect((width / 2 - 30).toFloat(), (height / 2 - 20).toFloat(), (width / 2 + 20).toFloat(), (height / 2 + 20).toFloat(), paint)
//        canvas.drawRect(0F, 0F, 20F, 20F, paint)
        paint.color = Color.YELLOW
        paint.textSize = 30F
        val text = count.toString()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textWidth = bounds.width()
        val textHeight = bounds.height()
        canvas.drawText(text, (width / 2 - textWidth / 2).toFloat(),
                (height / 2 + textHeight / 2).toFloat(),
                paint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event != null) {
//            when (event.action) {
//                MotionEvent.ACTION_MOVE -> {
//                    x = event.x - width / 2
//                    y = event.y - height / 2
//                    postInvalidate()
//                    Log.e("onMeasure", "x:${event.x} y:${event.y}")
//                    return true
//                }
//            }
//        }
        return super.onTouchEvent(event)
    }
}
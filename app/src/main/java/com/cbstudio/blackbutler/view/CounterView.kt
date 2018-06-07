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
 * Description:
 */
class CounterView(context: Context) : View(context), View.OnClickListener {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bounds: Rect = Rect()
    private var count: Int = 0
    private var statusBarHeight = Math.ceil((25 * context.resources.displayMetrics.density).toDouble())


    init {
        setOnClickListener(this)
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

        Log.e("onMeasure", "onMeasure: x-$widthMode/$widthSize")
        Log.e("onMeasure", "onMeasure: y-$heightMode/$heightSize")

        setMeasuredDimension(20, 20)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLUE
        canvas.drawRect((width / 2 - 30).toFloat(), (height / 2 - 20).toFloat(), (width / 2 + 20).toFloat(), (height / 2 + 20).toFloat(), paint)
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
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    x = event.x - width / 2
                    y = event.y - height / 2
                    postInvalidate()
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }
}
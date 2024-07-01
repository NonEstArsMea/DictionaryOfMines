package raa.example.dictionaryofmines

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.max
import kotlin.math.min


class ZoomImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
    private val gestureDetector = GestureDetector(context, GestureListener())
    private val matrix = Matrix()
    private val bounds = RectF()
    private var scaleFactor = 1.0f

    init {
        imageMatrix = matrix
        scaleType = ScaleType.MATRIX
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.pointerCount == 1) {
            gestureDetector.onTouchEvent(event)
        } else {
            scaleGestureDetector.onTouchEvent(event)
        }

        return true
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        drawable?.let {
            post {
                configureInitialMatrix(it)
            }
        }
    }

    private fun configureInitialMatrix(drawable: Drawable) {
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        val drawableWidth = drawable.intrinsicWidth.toFloat()
        val drawableHeight = drawable.intrinsicHeight.toFloat()

        val scale = min(viewWidth / drawableWidth, viewHeight / drawableHeight)

        val dx = (viewWidth - drawableWidth * scale) / 2
        val dy = (viewHeight - drawableHeight * scale) / 2

        matrix.setScale(scale, scale)
        matrix.postTranslate(dx, dy)
        imageMatrix = matrix

        bounds.set(0f, 0f, drawableWidth, drawableHeight)
        matrix.mapRect(bounds)
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor = 1f
            val scaleFactorDelta = detector.scaleFactor
            scaleFactor *= scaleFactorDelta
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))

            matrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
            imageMatrix = matrix
            return true
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            matrix.postTranslate(-distanceX, -distanceY)
            imageMatrix = matrix
            return true
        }
    }

}
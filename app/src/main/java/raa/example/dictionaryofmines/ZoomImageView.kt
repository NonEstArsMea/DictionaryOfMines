package raa.example.dictionaryofmines

import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.max
import kotlin.math.min


class ZoomImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
    private val gestureDetector = GestureDetector(context, GestureListener())
    private val matrix = Matrix()
    private val bounds = RectF()
    private var scaleFactor = 1.0f
    private var lastFocusX = 0f
    private var lastFocusY = 0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var activePointerId = INVALID_POINTER_ID

    companion object {
        private const val INVALID_POINTER_ID = -1
    }

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

        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val pointerIndex = event.actionIndex
                lastTouchX = event.getX(pointerIndex)
                lastTouchY = event.getY(pointerIndex)
                activePointerId = event.getPointerId(0)
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    val pointerIndex = event.findPointerIndex(activePointerId)
                    val x = event.getX(pointerIndex)
                    val y = event.getY(pointerIndex)

                    val dx = x - lastTouchX
                    val dy = y - lastTouchY

                    matrix.postTranslate(dx, dy)
                    constrainMatrix()
                    imageMatrix = matrix

                    lastTouchX = x
                    lastTouchY = y
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                activePointerId = INVALID_POINTER_ID
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)

                if (pointerId == activePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    lastTouchX = event.getX(newPointerIndex)
                    lastTouchY = event.getY(newPointerIndex)
                    activePointerId = event.getPointerId(newPointerIndex)
                }
            }
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
            lastFocusX = detector.focusX
            lastFocusY = detector.focusY
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactorDelta = detector.scaleFactor
            scaleFactor *= scaleFactorDelta
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))

            matrix.postScale(scaleFactorDelta, scaleFactorDelta, detector.focusX, detector.focusY)
            constrainMatrix()
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
            if (e1?.pointerCount == 1 && e2?.pointerCount == 1) {
                matrix.postTranslate(-distanceX, -distanceY)
                constrainMatrix()
                imageMatrix = matrix
            }
            return true
        }
    }

    private fun constrainMatrix() {
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()

        val drawable = drawable ?: return
        val drawableWidth = drawable.intrinsicWidth.toFloat()
        val drawableHeight = drawable.intrinsicHeight.toFloat()

        bounds.set(0f, 0f, drawableWidth, drawableHeight)
        matrix.mapRect(bounds)

        val width = bounds.width()
        val height = bounds.height()

        val deltaX = when {
            bounds.left > 0 -> -bounds.left
            bounds.right < viewWidth -> viewWidth - bounds.right
            else -> 0f
        }

        val deltaY = when {
            bounds.top > 0 -> -bounds.top
            bounds.bottom < viewHeight -> viewHeight - bounds.bottom
            else -> 0f
        }

        matrix.postTranslate(deltaX, deltaY)

        if (width < viewWidth) {
            matrix.postTranslate((viewWidth - width) / 2 - bounds.left, 0f)
        }
        if (height < viewHeight) {
            matrix.postTranslate(0f, (viewHeight - height) / 2 - bounds.top)
        }
    }
}
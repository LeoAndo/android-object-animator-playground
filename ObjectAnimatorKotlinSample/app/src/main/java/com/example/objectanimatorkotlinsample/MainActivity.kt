package com.example.objectanimatorkotlinsample

import androidx.appcompat.app.AppCompatActivity
import android.animation.TimeInterpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.TextView
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.view.View
import android.view.animation.*
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    private val interpolators = arrayOf<TimeInterpolator?>(
        null,
        AccelerateDecelerateInterpolator(),
        AccelerateInterpolator(),
        AnticipateInterpolator(),
        AnticipateOvershootInterpolator(),
        BounceInterpolator(),
        CycleInterpolator(2.0f),
        DecelerateInterpolator(),
        FastOutLinearInInterpolator(),
        FastOutSlowInInterpolator(),
        LinearInterpolator(),
        LinearOutSlowInInterpolator(),
        OvershootInterpolator()
    )
    private val repeatModes = booleanArrayOf(
        false,
        true
    )
    private var objectAnimator: ObjectAnimator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val targetView = findViewById<TextView>(R.id.targetView)
        val btnEndAnimation = findViewById<Button>(R.id.btnEndAnimation)
        btnEndAnimation.setOnClickListener { v: View? -> endAnimation() }
        val btnRotation = findViewById<Button>(R.id.btnRotation)
        btnRotation.setOnClickListener {
            startAnimation(
                targetView,
                View.ROTATION.name,
                interpolator,
                isRepeatMode,
                0.0f,
                360.0f
            )
        }
        val btnAlpha = findViewById<Button>(R.id.btnAlpha)
        btnAlpha.setOnClickListener {
            startAnimation(
                targetView,
                View.ALPHA.name,
                interpolator,
                isRepeatMode,
                1.0f,
                0.0f,
                1.0f
            )
        }
        val btnTranslate = findViewById<Button>(R.id.btnTranslation)
        btnTranslate.setOnClickListener {
            startAnimation(
                targetView,
                View.TRANSLATION_Y.name,
                interpolator,
                isRepeatMode,
                0.0f,
                300.0f,
                0.0f
            )
        }
        val btnScale = findViewById<Button>(R.id.btnScale)
        btnScale.setOnClickListener {
            startAnimation(
                targetView,
                View.SCALE_X.name,
                interpolator,
                isRepeatMode,
                1.0f,
                2.0f,
                1.0f
            )
        }
        val btnTextColor = findViewById<Button>(R.id.btnTextColor)
        btnTextColor.setOnClickListener {
            startAnimation(
                targetView,
                "textColor",
                interpolator,
                isRepeatMode,
                Color.BLACK,
                Color.YELLOW,
                Color.BLACK
            )
        }
        val btnMultipleProperties = findViewById<Button>(R.id.btnMultipleProperties)
        btnMultipleProperties.setOnClickListener {
            startAnimation(
                targetView, interpolator, isRepeatMode,
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.6f, 3.2f, 1.0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 3.2f, 0.6f, 1.0f),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f, 400.0f, 0f),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, 300.0f, 0f),
                PropertyValuesHolder.ofInt("textColor", Color.BLACK, Color.YELLOW, Color.BLACK)
            )
        }
    }

    private val interpolator: TimeInterpolator?
        get() {
            val spinner = findViewById<Spinner>(R.id.spinnerInterpolator)
            val selectedPosition = spinner.selectedItemPosition
            return interpolators[selectedPosition]
        }
    private val isRepeatMode: Boolean
        get() {
            val spinner = findViewById<Spinner>(R.id.spinnerRepeatMode)
            val selectedPosition = spinner.selectedItemPosition
            return repeatModes[selectedPosition]
        }

    private fun startAnimation(
        targetView: View,
        propertyName: String,
        timeInterpolator: TimeInterpolator?,
        isRepeatMode: Boolean,
        vararg values: Float
    ) {
        objectAnimator = ObjectAnimator.ofFloat(targetView, propertyName, *values)
        startAnimation(timeInterpolator, isRepeatMode)
    }

    private fun startAnimation(
        targetView: View,
        propertyName: String,
        timeInterpolator: TimeInterpolator?,
        isRepeatMode: Boolean,
        vararg values: Int
    ) {
        objectAnimator = ObjectAnimator.ofInt(targetView, propertyName, *values)
        startAnimation(timeInterpolator, isRepeatMode)
    }

    private fun startAnimation(
        targetView: View,
        timeInterpolator: TimeInterpolator?,
        isRepeatMode: Boolean,
        vararg values: PropertyValuesHolder
    ) {
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(targetView, *values)
        startAnimation(timeInterpolator, isRepeatMode)
    }

    private fun startAnimation(timeInterpolator: TimeInterpolator?, isRepeatMode: Boolean) {
        val objectAnimator = objectAnimator ?: return

        objectAnimator.duration = 2000
        if (isRepeatMode) {
            objectAnimator.repeatCount = 1
            objectAnimator.repeatMode = ObjectAnimator.REVERSE
        }
        objectAnimator.interpolator = timeInterpolator
        objectAnimator.start()
    }

    private fun endAnimation() {
        val objectAnimator = objectAnimator ?: return
        objectAnimator.end()
    }
}
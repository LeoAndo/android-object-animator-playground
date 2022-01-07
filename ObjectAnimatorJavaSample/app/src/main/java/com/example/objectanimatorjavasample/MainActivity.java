package com.example.objectanimatorjavasample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final TimeInterpolator[] interpolators = new TimeInterpolator[]{
            null,
            new AccelerateDecelerateInterpolator(),
            new AccelerateInterpolator(),
            new AnticipateInterpolator(),
            new AnticipateOvershootInterpolator(),
            new BounceInterpolator(),
            new CycleInterpolator(2),
            new DecelerateInterpolator(),
            new FastOutLinearInInterpolator(),
            new FastOutSlowInInterpolator(),
            new LinearInterpolator(),
            new LinearOutSlowInInterpolator(),
            new OvershootInterpolator()
    };

    private final boolean[] repeatModes = new boolean[]{
            false,
            true
    };
    private ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView targetView = findViewById(R.id.targetView);

        final Button btnEndAnimation = findViewById(R.id.btnEndAnimation);
        btnEndAnimation.setOnClickListener(v -> {
            endAnimation();
        });

        final Button btnRotation = findViewById(R.id.btnRotation);
        btnRotation.setOnClickListener(v -> {
            startAnimation(targetView, View.ROTATION.getName(), getInterpolator(), isRepeatMode(), 0.0f, 360.0f);
        });

        final Button btnAlpha = findViewById(R.id.btnAlpha);
        btnAlpha.setOnClickListener(v -> {
            startAnimation(targetView, View.ALPHA.getName(), getInterpolator(), isRepeatMode(), 1.0f, 0.0f, 1.0f);
        });

        final Button btnTranslate = findViewById(R.id.btnTranslation);
        btnTranslate.setOnClickListener(v -> {
            startAnimation(targetView, View.TRANSLATION_Y.getName(), getInterpolator(), isRepeatMode(), 0.0f, 300.0f, 0.0f);
        });

        final Button btnScale = findViewById(R.id.btnScale);
        btnScale.setOnClickListener(v -> {
            startAnimation(targetView, View.SCALE_X.getName(), getInterpolator(), isRepeatMode(), 1.0f, 2.0f, 1.0f);
        });

        final Button btnTextColor = findViewById(R.id.btnTextColor);
        btnTextColor.setOnClickListener(v -> {
            startAnimation(targetView, "textColor", getInterpolator(), isRepeatMode(), Color.BLACK, Color.YELLOW, Color.BLACK);
        });

        final Button btnMultipleProperties = findViewById(R.id.btnMultipleProperties);
        btnMultipleProperties.setOnClickListener(v -> {
            startAnimation(targetView, getInterpolator(), isRepeatMode(),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0.6f, 3.2f, 1.0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 3.2f, 0.6f, 1.0f),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f, 400.0f, 0f),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, 300.0f, 0f),
                    PropertyValuesHolder.ofInt("textColor", Color.BLACK, Color.YELLOW, Color.BLACK));
        });
    }

    private TimeInterpolator getInterpolator() {
        final Spinner spinner = findViewById(R.id.spinnerInterpolator);
        final int selectedPosition = spinner.getSelectedItemPosition();
        return interpolators[selectedPosition];
    }

    private boolean isRepeatMode() {
        final Spinner spinner = findViewById(R.id.spinnerRepeatMode);
        final int selectedPosition = spinner.getSelectedItemPosition();
        return repeatModes[selectedPosition];
    }

    private void startAnimation(
            View targetView,
            String propertyName,
            TimeInterpolator timeInterpolator,
            boolean isRepeatMode,
            float... values) {

        objectAnimator = ObjectAnimator.ofFloat(targetView, propertyName, values);
        startAnimation(timeInterpolator, isRepeatMode);
    }

    private void startAnimation(
            View targetView,
            String propertyName,
            TimeInterpolator timeInterpolator,
            boolean isRepeatMode,
            int... values) {

        objectAnimator = ObjectAnimator.ofInt(targetView, propertyName, values);
        startAnimation(timeInterpolator, isRepeatMode);
    }

    private void startAnimation(
            View targetView,
            TimeInterpolator timeInterpolator,
            boolean isRepeatMode,
            PropertyValuesHolder... values
    ) {
        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(targetView, values);
        startAnimation(timeInterpolator, isRepeatMode);
    }

    private void startAnimation(TimeInterpolator timeInterpolator, boolean isRepeatMode) {
        if (objectAnimator == null) return;

        objectAnimator.setDuration(2000);
        if (isRepeatMode) {
            objectAnimator.setRepeatCount(1);
            objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        }
        objectAnimator.setInterpolator(timeInterpolator);
        objectAnimator.start();
    }

    private void endAnimation() {
        if (objectAnimator == null) return;
        objectAnimator.end();
    }
}
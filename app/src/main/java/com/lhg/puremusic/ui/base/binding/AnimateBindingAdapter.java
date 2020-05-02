package com.lhg.puremusic.ui.base.binding;

import android.view.View;
import android.view.animation.Animation;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.BindingAdapter;

/**
 * Create by lhg at 20/4/19
 */
public class AnimateBindingAdapter {

    @BindingAdapter(value = {"setX"}, requireAll = false)
    public static void setX(View view, int setX) {
        view.setX(setX);
    }

    @BindingAdapter(value = {"setY"}, requireAll = false)
    public static void setY(View view, int setY) {
        view.setY(setY);
    }

    @BindingAdapter(value = {"startAnimation"}, requireAll = false)
    public static void startAnimation(View view, Animation animation) {
        if (view != null && animation != null) {
            view.startAnimation(animation);
        }
    }

    @BindingAdapter(value = {"transitionToStart"}, requireAll = false)
    public static void transitionToStart(MotionLayout motionLayout, boolean transitionToStart) {
        motionLayout.transitionToStart();
    }

    @BindingAdapter(value = {"transitionToEnd"}, requireAll = false)
    public static void transitionToEnd(MotionLayout motionLayout, boolean transitionToEnd) {
        motionLayout.transitionToEnd();
    }
}

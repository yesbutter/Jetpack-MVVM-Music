package com.lhg.puremusic.ui.base.binding;

import androidx.databinding.BindingAdapter;

import com.lhg.puremusic.ui.view.PlayPauseView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

/**
 * Create by lhg at 2020/3/13
 */
public class IconBindingAdapter {

    @BindingAdapter(value = {"isPlaying"}, requireAll = false)
    public static void isPlaying(PlayPauseView pauseView, boolean isPlaying) {
        if (isPlaying) {
            pauseView.play();
        } else {
            pauseView.pause();
        }
    }

    @BindingAdapter(value = {"mdIcon"}, requireAll = false)
    public static void setIcon(MaterialIconView view, MaterialDrawableBuilder.IconValue iconValue) {
        view.setIcon(iconValue);
    }

    @BindingAdapter(value = {"circleAlpha"}, requireAll = false)
    public static void circleAlpha(PlayPauseView pauseView, int circleAlpha) {
        pauseView.setCircleAlpah(circleAlpha);
    }

    @BindingAdapter(value = {"drawableColor"}, requireAll = false)
    public static void drawableColor(PlayPauseView pauseView, int drawableColor) {
        pauseView.setDrawableColor(drawableColor);
    }
}

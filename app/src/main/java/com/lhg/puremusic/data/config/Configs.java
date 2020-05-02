package com.lhg.puremusic.data.config;

import android.os.Environment;

import com.lhg.architecture.utils.Utils;

/**
 * Create by lhg at 18/9/28
 */
public class Configs {

    public static final String COVER_PATH = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

    public static final String TOKEN = "token";

}

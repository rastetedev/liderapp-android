package com.hk.kbottomnavigation.zoominimageview.animation;

import android.os.Build;
import android.view.View;

/**
 * A Compat tool for view.postOnAnimation
 */

public class AnimCompat {

    private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

    public static void postOnAnimation(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }




}

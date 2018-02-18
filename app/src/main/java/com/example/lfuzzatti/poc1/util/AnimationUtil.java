package com.example.lfuzzatti.poc1.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationUtil {


    public static void executeAnimation(final Context context,
                                        Animation animation,
                                        Animation.AnimationListener animationListener,
                                        final int animationId,
                                        View viewAnimation) {
        //define animation
        animation = AnimationUtils.loadAnimation(context, animationId);

        // set animation listener
        animation.setAnimationListener(animationListener);

        //anchor animation with element
        viewAnimation.startAnimation(animation);
    }
}

package com.silvandrade.instagramremakejava.common.util;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public final class Colors {

    public static int getColor(Context context, @ColorRes int colorID) {
        return ContextCompat.getColor(context, colorID);
    }
}

package com.prelimtek.android.basecomponents;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.content.res.Resources;
import android.content.res.Resources.Theme;

public class ResourcesUtils {
    @SuppressWarnings("deprecation")
    public static int getColor(View view, int id){
        Theme theme =  view.getContext().getTheme();
        Resources resources = view.getResources();
        int color = -1;

        if (Build.VERSION.SDK_INT < 23) {
            color = resources.getColor(id);
        }else{
            color = resources.getColor(id,theme);
        }

        return color;
    }

    public static Drawable getDrawable(View view, int id){
        Theme theme =  view.getContext().getTheme();
        Resources resources = view.getResources();
        Drawable drawable = null;

        if (Build.VERSION.SDK_INT < 23) {
            drawable = resources.getDrawable(id);
        }else{
            drawable = resources.getDrawable(id,theme);
        }

        return drawable;
    }
}

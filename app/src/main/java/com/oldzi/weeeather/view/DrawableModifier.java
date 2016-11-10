package com.oldzi.weeeather.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Oldzi on 01.09.2016.
 */
public class DrawableModifier {

    @NonNull
    Context context;
    @ColorRes
    private int itemColor;
    private Drawable originalDrawable;
    private Drawable modifiedDrawable;
    private boolean flipHorizontally = false;
    private boolean flipVertically = false;

    public DrawableModifier(@NonNull Context context) {
        this.context = context;
    }

    public static DrawableModifier withContext(@NonNull Context context) {
        return new DrawableModifier(context);
    }

    public DrawableModifier withDrawable(Drawable drawable) {
        originalDrawable = drawable;
        return this;
    }

    public DrawableModifier withItemColor(@ColorRes int color) {
        itemColor = color;
        return this;
    }

    public DrawableModifier flipVertically(boolean flip) {
        flipVertically = flip;
        return this;
    }

    public DrawableModifier flipHorizontally(boolean flip) {
        flipHorizontally = flip;
        return this;
    }

    public DrawableModifier modify() {
        if (originalDrawable == null) {
            throw new NullPointerException("No drawable found");
        }
        if (itemColor == 0) {
            throw new IllegalStateException("Item color not precised");
        }

        modifiedDrawable = originalDrawable.mutate();
        modifiedDrawable = DrawableCompat.wrap(modifiedDrawable);
        DrawableCompat.setTint(modifiedDrawable, ContextCompat.getColor(context, itemColor));
        DrawableCompat.setTintMode(modifiedDrawable, PorterDuff.Mode.SRC_IN);
        return this;
    }

    public void applyTo(@NonNull ImageView imageView) {

        if (modifiedDrawable == null) {
            throw new NullPointerException("Modified drawable is null");
        }
        imageView.setImageDrawable(modifiedDrawable);
    }

    public void applyToBackground(@NonNull View view) {
        if (modifiedDrawable == null) {
            throw new NullPointerException("É preciso chamar o método tint()");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(modifiedDrawable);
        } else {
            view.setBackground(modifiedDrawable);
        }
    }

    public Drawable get() {
        if (modifiedDrawable == null) {
            throw new NullPointerException("No drawable found");
        }
        return modifiedDrawable;
    }
}

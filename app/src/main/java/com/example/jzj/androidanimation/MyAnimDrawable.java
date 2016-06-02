package com.example.jzj.androidanimation;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

/**
 * Created by jzj on 16/6/2.
 */
public class MyAnimDrawable extends Drawable implements Animatable, Drawable.Callback, Runnable {

    private boolean mRunning = false;

    public MyAnimDrawable() {
        super();
        setCallback(this);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawARGB(128, (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        if (visible) {
            if (changed || restart) {
                nextFrame();
            }
        } else {
            unscheduleSelf(this);
        }
        return changed;
    }

    public void invalidateDrawable(Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    @Override
    public void start() {
        if (!mRunning) {
            mRunning = true;
            nextFrame();
        }
    }

    @Override
    public void stop() {
        unscheduleSelf(this);
        mRunning = false;
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void run() {
        invalidateSelf();
        nextFrame();
    }

    private void nextFrame() {
        unscheduleSelf(this);
        scheduleSelf(this, SystemClock.uptimeMillis() + 500);
    }
}
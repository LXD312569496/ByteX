package com.ss.android.ugc.bytex.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 注意：是继承 android.widget.ImageView
 */
public class MonitorImageView extends android.widget.ImageView implements MessageQueue.IdleHandler {

    private static final String TAG = "MonitorImageView";
    private final int MAX_ALARM_IMAGE_SIZE = 1024;

    public MonitorImageView(Context context) {
        super(context);
        Log.d(TAG, "MonitorImageView: ");
    }

    public MonitorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "MonitorImageView: ");
    }

    public MonitorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "MonitorImageView: ");
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        this.monitor();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        monitor();
    }

    private void monitor() {
        Looper.myQueue().removeIdleHandler(this);
        Looper.myQueue().addIdleHandler(this);
    }

    @Override
    public boolean queueIdle() {
        checkDrawable();
        return false;
    }

    private void checkDrawable() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        int imageSize = 0;
        if (drawable instanceof BitmapDrawable) {
            imageSize = ((BitmapDrawable) drawable).getBitmap().getByteCount();
        }
//        if (imageSize > MAX_ALARM_IMAGE_SIZE) {
        Log.d(TAG, "图片大小超标: " + imageSize);
//        }
//        if (drawableWidth > viewWidth || drawableHeight > viewHeight) {
        Log.d(TAG, "图片尺寸超标, drawable: " + drawableWidth + "x" + drawableHeight + ",view: " + viewWidth + "x" + viewHeight);
//        }

        //todo:可以输出View的id，方便定位
    }
}



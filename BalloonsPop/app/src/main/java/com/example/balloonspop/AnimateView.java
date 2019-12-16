/*
 * Written by Khoa L. D. Ho (klh170130)
 * for Assignment 6 for class CS6326 Falll 2019, by Professor J. Cole, at UT Dallas,
 * starting Nov16, 2019, using Android Studio 191 on Windows 8.1
 *
 * Balloons Pop Game Program
 * This Program is a simple balloons pop game
 *
 *
 *
 * This is a custom animated View .
 *
 */


package com.example.balloonspop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Customer animated View
 */
public class AnimateView extends View {
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    int paddingLeft; // = getPaddingLeft();
    int paddingTop; // = getPaddingTop();
    int paddingRight; // = getPaddingRight();
    int paddingBottom; // = getPaddingBottom();

    int contentWidth; // = getWidth() - paddingLeft - paddingRight;
    int contentHeight; // = getHeight() - paddingTop - paddingBottom;

    public int remDurationInMilis;
    public String animText;
    private Runnable rnbTimer;
    Handler drawHandler;// = new Handler();
    int frameRate;// = 10;

    public AnimateView(Context context) {
        super(context);
        init(null, 0);
    }

    public AnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AnimateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.AnimateView, defStyle, 0);
        a.recycle();

        int textSizeSP = 14;
        int textSizePixels = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSizeSP, getResources().getDisplayMetrics());
        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(textSizePixels);
        mTextPaint.setStrokeWidth(2);
        mTextPaint.setStyle(Paint.Style.STROKE);
        invalidateTextPaintAndMeasurements();

        drawHandler = new Handler();
        frameRate = 500;
        remDurationInMilis = 60000;
        animText = String.valueOf(remDurationInMilis/1000) +"s";
        rnbTimer = new Runnable() {
            @Override
            public void run() {
                remDurationInMilis -= frameRate;
                if (remDurationInMilis < 0)
                {
                    remDurationInMilis = 0;
                }
                animText = String.valueOf(remDurationInMilis/1000)+"s";

                invalidate();
            }
        };
    }


    private void invalidateTextPaintAndMeasurements() {
        int textSizeSP = 14;
        int textSizePixels = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSizeSP, getResources().getDisplayMetrics());
        mTextPaint.setTextSize(textSizePixels);
        mTextPaint.setColor(Color.WHITE);
        mTextWidth = mTextPaint.measureText("60s");

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(animText, 0, getHeight()/2 + 10, mTextPaint);


        drawHandler.postDelayed(rnbTimer, frameRate);
    }


    public void setRemDurationInMilis(int remDuration)
    {
        this.remDurationInMilis = remDuration;
        this.animText = String.valueOf(remDurationInMilis/1000) + "s";
    }
} // end class AnimateView

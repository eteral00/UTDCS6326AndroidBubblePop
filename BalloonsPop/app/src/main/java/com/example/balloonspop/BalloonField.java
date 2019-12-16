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
 * This is a custom View to handle animation of the Balloons.
 *
 */


package com.example.balloonspop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is a custom View to handle animation of the Balloons..
 */
public class BalloonField extends View {

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    int paddingLeft; // = getPaddingLeft();
    int paddingTop; // = getPaddingTop();
    int paddingRight; // = getPaddingRight();
    int paddingBottom; // = getPaddingBottom();

    int contentWidth; // = getWidth() - paddingLeft - paddingRight;
    int contentHeight; // = getHeight() - paddingTop - paddingBottom;

    private Runnable rnbTimer;
    Handler drawHandler;// = new Handler();
    int frameRate;// = 10;

    private Random rnd;
    public ArrayList<Balloon> gameBalloons;
    private Paint blPaint;
    private int blColors[];
    public int selectedColor;
    public int selectedShape;
    public int missedPopCount;

    public BalloonField(Context context) {
        super(context);
        init(null, 0);

    }

    public BalloonField(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BalloonField(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BalloonField, defStyle, 0);

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
        blColors = new int[]{Color.RED, 0xFFFFA500, Color.YELLOW, Color.GREEN, Color.BLUE, 0xFF800080, Color.WHITE};

        blPaint = new Paint();
        blPaint.setColor(Color.BLACK);
        blPaint.setStyle(Paint.Style.FILL);

        missedPopCount = 0;
        gameBalloons = new ArrayList<>();
        //gameBalloons.clear();

        rnd = new Random();
        selectedColor = 0;
        selectedShape = 0;
        drawHandler = new Handler();
        frameRate = 10;

        rnbTimer = new Runnable() {
            @Override
            public void run() {



                if ( gameBalloons.size() < 12 )
                {
                    addBalloon(false);
                    addBalloon(true);
                }


                Balloon tBalloon;
                Balloon tOtherBalloon;
                double distance;
                double xDistance;
                double yDistance;
                int stSpeed = 9;
                float diaPixels;
                int diaSizePx;
                float diaPixelsO;
                int diaSizePxO;

                for (int idx = 0; idx < gameBalloons.size(); idx++ ) {
                    tBalloon = gameBalloons.get(idx);
                    tBalloon.riseUp();

                    diaPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tBalloon.diaSizeDP, getResources().getDisplayMetrics());
                    diaSizePx = (int)diaPixels;

                    if( (tBalloon.posY + diaSizePx) <= 0 )
                    {
                        if ( (tBalloon.round == selectedShape) && (tBalloon.color == selectedColor) )
                        {
                            missedPopCount++;
                        }
                        gameBalloons.remove(idx);
                    }


                    // for each other balloon
                    for (int idx1 = 0; idx1 < gameBalloons.size(); idx1++ )
                    {
                        if ( idx != idx1 )
                        {
                            tOtherBalloon = gameBalloons.get(idx1);
                            diaPixelsO = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tOtherBalloon.diaSizeDP, getResources().getDisplayMetrics());

                            xDistance = tBalloon.posX - tOtherBalloon.posX;
                            yDistance = tBalloon.posY - tOtherBalloon.posY;
                            double sqDiaPx = diaPixels * Math.pow(Math.sqrt(2),tBalloon.round);
                            double sqDiaPxO = diaPixelsO * Math.pow(Math.sqrt(2),tOtherBalloon.round);
                            distance = Math.sqrt( Math.pow( xDistance, 2) + Math.pow(yDistance, 2) ) - ((sqDiaPx + sqDiaPxO));
                            if ( distance <= 0 )
                            {
                                tBalloon.fallDown();
                                if (tOtherBalloon.posY < tBalloon.posY)
                                {
                                    tOtherBalloon.posY += distance;
                                    tOtherBalloon.speed++;
                                    if (tBalloon.speed > 2){
                                        tBalloon.speed--;
                                    }

                                }

                            }
                        }
                    }
                }

                invalidate();
            }
        };

    }


    /* method to add balloon
    * flag to add selected color and shape
    */
    public void addBalloon(boolean selected)
    {
        int tShape, tColor, tSize, tSpeed, tPosX, tPosY;
        if (selected)
        {
            tShape = selectedShape;
            tColor = selectedColor;
        }
        else
        {
            tShape = rnd.nextInt(2);
            tColor = rnd.nextInt(7);
        }

        tSize = rnd.nextInt(33)+16;
        tSpeed = rnd.nextInt(9)+1;

        float diaPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tSize, getResources().getDisplayMetrics());
        int tSizePx = (int)diaPixels;
        tPosX = rnd.nextInt( 640 - tSizePx  ) + (tSizePx);
        tPosY = 853 + (tSizePx);
        Balloon nBalloon = new Balloon(tShape, tColor, tSize, tSpeed, tPosX, tPosY);
        gameBalloons.add(nBalloon);
    }

    /* method to handle balloon pop on touch
    *
    */
    public int popBalloon(float posX, float posY, int pShape, int pColor) {

        Balloon tBalloon;
        int diaPixels;
        int goodPop = 0;
        for(int idx=gameBalloons.size()-1; idx >= 0; idx-- )
        {
            tBalloon = gameBalloons.get(idx);

            diaPixels = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tBalloon.diaSizeDP, getResources().getDisplayMetrics());

            if ( (posX >= tBalloon.posX) && (posX <= tBalloon.posX + diaPixels ) && (posY >= tBalloon.posY) && (posY <= tBalloon.posY + diaPixels ) ){
                if ( (tBalloon.round == pShape) && (tBalloon.color == pColor) )
                {
                    goodPop++;
                }
                else
                {
                    goodPop--;
                }
                gameBalloons.remove(idx);
                break; // pop 1 and break

            }

        }
        return goodPop;

    }


    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(12);
        mTextPaint.setColor(1);
        mTextWidth = mTextPaint.measureText("Test");

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Balloon tBalloon;
        float diaPixels;

        for (int idx = 0; idx < gameBalloons.size(); idx++)
        {
            tBalloon = gameBalloons.get(idx);
            diaPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tBalloon.diaSizeDP, getResources().getDisplayMetrics());
            this.blPaint.setColor( blColors[ tBalloon.color ] );


            if( tBalloon.round == 0 )
            {
                canvas.drawCircle(tBalloon.posX,tBalloon.posY, diaPixels, blPaint);
            }
            else
            {
                canvas.drawRect(tBalloon.posX,tBalloon.posY,tBalloon.posX+diaPixels,tBalloon.posY+diaPixels,blPaint);
            }
        }

        drawHandler.postDelayed(rnbTimer, frameRate);
    }


} // end class BalloonField

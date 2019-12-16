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
 * This is Game Activity - the logic controller behind the main game screen.
 *
 */


package com.example.balloonspop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // class data members
    int gameScore;
    int blColors[];
    int selectedColor;
    int selectedShape;
    int goodPopCount;
    int allPopCount;
    int missedPopCount;
    int remTimeInSec;
    int remTimeInMilis;
    Runnable rnbTimer;
    Handler timeHandler;
    int frameRate;
    BalloonField blField;
    AnimateView avRemDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.gameScore = 0;
        this.goodPopCount = 0;
        this.allPopCount = 0;
        this.missedPopCount = 0;
        remTimeInSec = 60;
        remTimeInMilis = 60000;


        Random rnd = new Random();

        blColors = new int[]{Color.RED, 0xFFFFA500, Color.YELLOW, Color.GREEN, Color.BLUE, 0xFF800080, Color.WHITE};
        selectedShape = rnd.nextInt(2);
        selectedColor = rnd.nextInt(blColors.length);
        String sltShape;
        String sltColor;

        if (selectedShape == 0)
        {
            sltShape = "Round";
        }
        else
        {
            sltShape = "Square";
        }
        switch(selectedColor){
            case 0: sltColor = "RED"; break;
            case 1: sltColor = "ORANGE"; break;
            case 2: sltColor = "YELLOW"; break;
            case 3: sltColor = "GREEN"; break;
            case 4: sltColor = "BLUE"; break;
            case 5: sltColor = "PURPLE"; break;
            default: sltColor = "WHITE"; break;

        }

        String strGamePrompt = "Pop the " + sltColor + " " + sltShape + " balloons!";
        TextView txvGamePrompt = (TextView)findViewById(R.id.txvGamePrompt);
        txvGamePrompt.setText(strGamePrompt);
        txvGamePrompt.setTextColor( blColors[ selectedColor ] );

        final TextView txvCurScore = (TextView)findViewById(R.id.txvCurScore);
        final TextView txvGoodPop = (TextView)findViewById(R.id.txvGoodPop);
        final TextView txvAllPop = (TextView)findViewById(R.id.txvAllPop);
        final TextView txvMissedPop = (TextView)findViewById(R.id.txvMissedPop);
        //final TextView txvRemTime = (TextView)findViewById(R.id.txvRemTime);
        txvCurScore.setText(String.valueOf(gameScore));
        txvGoodPop.setText(String.valueOf(goodPopCount));
        txvAllPop.setText(String.valueOf(allPopCount));
        txvMissedPop.setText(String.valueOf(missedPopCount));
        //txvRemTime.setText(String.valueOf(remTimeInSec) + "s");
        avRemDuration = (AnimateView)findViewById(R.id.avRemDuration);
        avRemDuration.setRemDurationInMilis(remTimeInMilis);

        blField = (BalloonField) findViewById(R.id.blfMainField);
        blField.selectedColor = selectedColor;
        blField.selectedShape = selectedShape;
        // get size of device screen
        Display gameScreen = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        gameScreen.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        ViewGroup.LayoutParams fieldLayoutParams = blField.getLayoutParams();

        fieldLayoutParams.height = screenHeight * 2 / 3;
        blField.setLayoutParams(fieldLayoutParams);
        int fieldHeight =  fieldLayoutParams.height;


        blField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View vw, MotionEvent event) {
                int oldGameScore = gameScore;
                float touchX, touchY;
                boolean retVal = false;
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    touchX = event.getX();
                    touchY = event.getY();
                    gameScore += blField.popBalloon(touchX,touchY, selectedShape, selectedColor);
                    retVal = true;
                }

                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    touchX = event.getX();
                    touchY = event.getY();
                    gameScore += blField.popBalloon(touchX,touchY, selectedShape, selectedColor);
                    retVal = true;
                }

                missedPopCount = blField.missedPopCount;
                if (gameScore != oldGameScore )
                {
                    allPopCount++;
                    if (gameScore > oldGameScore)
                    {
                        goodPopCount++;
                        if ( (goodPopCount % 10 ) == 0 ){
                            int remTimeInMilis = avRemDuration.remDurationInMilis;
                            remTimeInMilis += 10000;
                            avRemDuration.setRemDurationInMilis(remTimeInMilis);
                        }
                    }
                    txvCurScore.setText(String.valueOf(gameScore));
                    txvGoodPop.setText(String.valueOf(goodPopCount));
                    txvAllPop.setText(String.valueOf(allPopCount));
                    txvMissedPop.setText(String.valueOf(missedPopCount));

                    //txvRemTime.setText(String.valueOf(remTimeInSec) + "s");
                }

                return retVal;
            }

        } );


        this.frameRate = 50;
        this.timeHandler = new Handler();
        rnbTimer = new Runnable() {
            @Override
            public void run() {
                //remTimeInSec = (remTimeInSec * 100 - frameRate)/100;
                //avRemDuration.setRemDurationInMilis(remTimeInSec*100);
                //txvCurScore.setText(String.valueOf(remTimeInSec));
                checkFinishing();
            }
        };

        checkFinishing();


    } // end onCreate


    /* method to loop with the handler and runnable
    *
    */
    private void checkFinishing()
    {
        remTimeInMilis = avRemDuration.remDurationInMilis;
        missedPopCount = blField.missedPopCount;
        TextView txvMissedPop = (TextView) findViewById(R.id.txvMissedPop);
        txvMissedPop.setText(String.valueOf(missedPopCount));
        if (remTimeInMilis <= 0)
        {
            callEndGame();
        }
        else
        {
            timeHandler.postDelayed(rnbTimer, frameRate);
        }
    }


    /* handler for click event on button End Game
     *
     */
    public void onClickEndGame(View vwButton)
    {
        callEndGame();
    }

    /* method to transfer back to main/start screen
     *
     */
    private void callEndGame()
    {
        this.missedPopCount = this.blField.missedPopCount;
        //Intent iEnd = new Intent(this, MainActivity.class);
        Intent iEnd = new Intent(this, HighscoreActivity.class);
        iEnd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        iEnd.putExtra("gameScore", this.gameScore);
        iEnd.putExtra("missedPopCount", this.missedPopCount);

        startActivity(iEnd);
    }
} // end GameActivity

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
 * This is Main Activity - the logic controller behind the first/start screen.
 *
 */


package com.example.balloonspop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /* handler for click event on button Start
     *
     */
    public void onClickStart(View vwButton)
    {
        startGame();
    }

    /* method to transfer to the GameActivity
     *
     */
    private void startGame()
    {
        Intent iStartGame = new Intent(this, GameActivity.class);
        iStartGame.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // send the lowest score on the high-score list to the game to track
        // int lowestScore = scoreArrayList.get(scoreArrayList.size()-1).getScore();
        //iStartGame.putExtra("lowestScore", lowestScore);

        startActivity(iStartGame);
    }


    /* handler for click event on button Start
     *
     */
    public void onClickHighscores(View vwButton)
    {
        viewHighscores();
    }

    /* method to transfer to the GameActivity
     *
     */
    private void viewHighscores()
    {
        Intent iHighscore = new Intent(this, HighscoreActivity.class);
        iHighscore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        iHighscore.putExtra("gameScore", 0);

        startActivity(iHighscore);
    }


} // end class MainActivity





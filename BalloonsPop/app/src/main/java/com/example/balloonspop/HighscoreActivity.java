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
 * This is Highscore Activity - the logic controller behind the Highscore display screen.
 *
 */


package com.example.balloonspop;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;



public class HighscoreActivity extends AppCompatActivity {

    // Class data members
    private ArrayList<HighScore> scoreArrayList;
    private TechServices myTechServices;
    private String fileName;
    private int lowestScore;
    private int gameScore;
    private int missedPopCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        gameScore = 0;
        missedPopCount = 0;
        Intent iRecIntent = getIntent();
        if (iRecIntent != null);
        {
            gameScore = iRecIntent.getIntExtra("gameScore",0);
            missedPopCount = iRecIntent.getIntExtra("missedPopCount", 0);
            TextView txvGameScore = (TextView) findViewById(R.id.txvGameScore);
            txvGameScore.setText("Your score is: " + String.valueOf(gameScore));
            TextView txvMissedScore = (TextView) findViewById(R.id.txvMissedScore);
            txvMissedScore.setText("Missed Pop Count: " + String.valueOf(missedPopCount));

        }

        myTechServices = new TechServices();
        fileName = "ScoreList.txt";

        Date todayDate = new Date();

        scoreArrayList = new ArrayList<>();
        scoreArrayList.clear();

        myTechServices.readScoresFromFile(scoreArrayList,fileName);

        // sort highest to lowest
        Collections.sort(scoreArrayList, Collections.reverseOrder());

        // trim the list, only take the top 10 highest
        if(scoreArrayList.size() > 10)
        {
            for( int idx = 10; idx < scoreArrayList.size();)
            {
                scoreArrayList.remove(idx);
            }
        }

        // build and set adapter for list view on the layout
        ScoreAdapter sadScores = new ScoreAdapter(this, scoreArrayList);
        ListView lsvScores =(ListView) findViewById(R.id.lsvScores);
        lsvScores.setAdapter(sadScores);

        lowestScore = scoreArrayList.get(scoreArrayList.size()-1).getScore();

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        TextView txvAddPrompt = (TextView) findViewById(R.id.txvAddPrompt);
        if (gameScore >= lowestScore)
        {
            btnAdd.setEnabled(true);
            txvAddPrompt.setText("Congrats! You get a new high score! Do you want to save it?");
        }
        else
        {
            btnAdd.setEnabled(false);
            int dColor = 0x33 << 24 | 0x99 << 16 | 0x00 << 8 | 0xff;
            int dtColor = 0x33 << 24 | 0xaa << 16 | 0xaa << 8 | 0xaa;
            btnAdd.setBackgroundColor(dColor);
            btnAdd.setTextColor(dtColor);
            txvAddPrompt.setText("");
        }

    } // end onCreate


    /* method to handle onClick event of button Add Score
     *
     */
    public void onClickAddScore(View vwButton)
    {
        callAddScore();
    }

    /* method to call and transfer to the AddScoreActivity
     *
     */
    private void callAddScore()
    {
        Intent iAddScore = new Intent(this, AddScoreActivity.class );
        iAddScore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // send the new game score on over to add.
        iAddScore.putExtra("gameScore", this.gameScore);

        startActivityForResult(iAddScore, 1, null);
    }


    /* method to handle results returned from the AddScoreActivity
     *
     */
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent iRetIntent) {
        super.onActivityResult(reqCode, resultCode, iRetIntent);
        if ( reqCode == 1 && resultCode == RESULT_OK && iRetIntent != null )
        {
            Button btnAdd = (Button)findViewById(R.id.btnAdd);
            TextView txvAddPrompt = (TextView) findViewById(R.id.txvAddPrompt);
            btnAdd.setEnabled(false);
            int dColor = 0x33 << 24 | 0x99 << 16 | 0x00 << 8 | 0xff;
            int dtColor = 0x33 << 24 | 0xaa << 16 | 0xaa << 8 | 0xaa;
            btnAdd.setBackgroundColor(dColor);
            btnAdd.setTextColor(dtColor);
            txvAddPrompt.setText("New Highscore saved!");

            btnAdd.setEnabled(false);
            SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String[] newScoreStrs = iRetIntent.getStringArrayExtra("newScoreStrs");

            if (newScoreStrs.length == 3)
            {
                Date scoreDate;
                try {
                    scoreDate = dtFormat.parse(newScoreStrs[2]);
                }
                catch(java.text.ParseException e) {
                    scoreDate = new Date();
                }

                HighScore newScore = new HighScore(newScoreStrs[0], Integer.parseInt(newScoreStrs[1]), scoreDate);
                scoreArrayList.add(newScore);
            }
            else if (newScoreStrs.length > 0)
            {
                Date scoreDate = new Date() ;
                HighScore newScore = new HighScore(newScoreStrs[0], 888, scoreDate);
                scoreArrayList.add(newScore);
            }

            // sort highest to lowest
            Collections.sort(scoreArrayList, Collections.reverseOrder());

            // trim the list, only take the top 10 highest
            if(scoreArrayList.size() > 10)
            {
                for( int idx = 10; idx < scoreArrayList.size();)
                {
                    scoreArrayList.remove(idx);
                }
            }

            // build and set adapter for list view on the layout
            ScoreAdapter sadScores = new ScoreAdapter(this, scoreArrayList);
            ListView lsvScores =(ListView) findViewById(R.id.lsvScores);
            lsvScores.setAdapter(sadScores);

            myTechServices.writeScoresToFile(scoreArrayList, fileName);
            Toast.makeText(this, "New Score Saved!", Toast.LENGTH_LONG).show();

            //callReturn();
        }
    }


    /* handler for click event on button Return
    *
    */
    public void onClickReturn(View vwButton)
    {
        callReturn();
    }

    /* method to transfer back to main/start screen
    *
    */
    private void callReturn()
    {
        Intent iReturn = new Intent(this, MainActivity.class);
        iReturn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(iReturn);
    }


} // end HighscoreActivity

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
 * This is the AddScore Activity, i.e. the logic controller of the AddScore screen.
 *
 */

package com.example.balloonspop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddScoreActivity extends AppCompatActivity {

    private long selectedDateInMilis;
    private int gameScore;

    /* onCreate method for AddScoreActivity
    *
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        Intent iRecIntent = getIntent();
        if (iRecIntent != null)
        {
            gameScore = iRecIntent.getIntExtra("gameScore",0);
            EditText etxtScore = findViewById(R.id.newScore);
            etxtScore.setText(String.valueOf(gameScore));
        }

        final EditText etxtNewDate = findViewById(R.id.newDate);


        Date newDate = new Date();
        SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String dateStr = dtFormat.format(newDate);
        etxtNewDate.setText(dateStr);



    }


    /* method to handle onClick event of Cancel button
     *
     */
    public void onClickCancel(View vwButton) {
        callCancel();
    }

    /* method to "Cancel", i.e. returning to main screen without any change
    *
    */
    private void callCancel()
    {
        Intent iRetItent = new Intent();
        setResult(RESULT_CANCELED);
        finish();
    }


    /* method to handle onClick event of Save button
     *
     */
    public void onClickSave(View vwButton) {
        callSave();
    }

    /* method to "Save", by transfering back to main activity
    *
    */
    private void callSave() {
        EditText etxtNewName = findViewById(R.id.newName);
        EditText etxtNewScore = findViewById(R.id.newScore);
        EditText etxtNewDate = findViewById(R.id.newDate);

        String newName = String.valueOf( etxtNewName.getText()).trim();
        String newScore = String.valueOf( etxtNewScore.getText()).trim();
        String newDate = String.valueOf( etxtNewDate.getText() ).trim();
        boolean isValid = validateInput(newName, newScore, newDate);

        if (isValid)
        {
            String[] newScoreStrs = {newName, newScore, newDate};
            Intent iRetItent = new Intent();
            iRetItent.putExtra("newScoreStrs", newScoreStrs);
            setResult(RESULT_OK, iRetItent);

            finish();
        }


    }

    /* method to validate input
    * taking the 3 (Stringified) text field as Params, in order, Name, Score, Date
    * tests:
    * * all: non-empty
    * * Score: non-negative, higher than lowest score on the list...well, this is a High Score app after all
    * * Date: parsable, correct format, not in the future
    */
    private boolean validateInput(String newName, String newScoreStr, String newDateStr) {
        boolean isValid = false;
        EditText etxtNewName = findViewById(R.id.newName);
        EditText etxtNewScore = findViewById(R.id.newScore);
        EditText etxtNewDate = findViewById(R.id.newDate);

        int newScore;

        if (newName.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_LONG).show();
            etxtNewName.requestFocus();
        }
        else if ( newScoreStr.isEmpty() ) {
            Toast.makeText(this, "Score cannot be empty!", Toast.LENGTH_LONG).show();
            etxtNewScore.requestFocus();
        }
        else if ( ( newScore = Integer.parseInt(newScoreStr) ) < 0 ) {
            Toast.makeText(this, "Score must be non-negative, i.e. >= 0!", Toast.LENGTH_LONG).show();
            etxtNewScore.requestFocus();
        }
        /*
        else if ( newScore < lowestScore ) {
            Toast.makeText(this, "Score must not be lower than current lowest score: " + String.valueOf(lowestScore), Toast.LENGTH_LONG).show();
            etxtNewScore.requestFocus();
        }
        */
        else if ( newDateStr.isEmpty() ) {
            Toast.makeText(this, "Date cannot be empty!", Toast.LENGTH_LONG).show();
            etxtNewDate.requestFocus();
        }
        else {
            SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            try{
                Date newDate = dtFormat.parse(newDateStr);
                Date today = new Date();
                if (newDate.compareTo(today) > 0 ) {
                    Toast.makeText(this, "Date cannot be in the future!", Toast.LENGTH_LONG).show();
                    etxtNewDate.requestFocus();
                }
                else {
                    isValid = true;
                }
            }
            catch (java.text.ParseException e) {
                Toast.makeText(this, "Invalid Date format! Format should be MM/dd/yyyy HH:mm:ss ", Toast.LENGTH_LONG).show();
                //etxtNewDate.setText(dtFormat.format( new Date() ) );
                etxtNewDate.requestFocus();
                isValid = false;
            }
        }
        return isValid;
    }

}

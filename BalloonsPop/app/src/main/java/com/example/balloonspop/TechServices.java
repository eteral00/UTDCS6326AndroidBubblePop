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
 * This is a module to handle technical services such as I/O.
 *
 */


package com.example.balloonspop;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
//import java.sql.Date;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class TechServices {

    // constructor
    public TechServices(){};


    /* Method to write/save score from an array list of highscore objects to file
     * param scoreArrayList: source array list storing scores
     * param fileName: name of file to write,
     * note: only name, not path
     * all input/output files are supposed to be stored in default external storage directory on the device
     */
    public void writeScoresToFile(ArrayList<HighScore> scoreArrayList, String fileName)
    {

        try
        {
            File scoreFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);

            SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            FileOutputStream fosScoresWriter = new FileOutputStream(scoreFile);
            OutputStreamWriter oswScoresWriter = new OutputStreamWriter(fosScoresWriter);

            for ( int idx = 0; idx < scoreArrayList.size(); idx++ )
            {
                oswScoresWriter.write(scoreArrayList.get(idx).getScoreName() );
                oswScoresWriter.write('\t');
                oswScoresWriter.write( String.valueOf(scoreArrayList.get(idx).getScore() ) );
                oswScoresWriter.write('\t');
                oswScoresWriter.write( dtFormat.format(scoreArrayList.get(idx).getScoreDate() ) );
                oswScoresWriter.write('\n');
            }

            fosScoresWriter.flush();
            oswScoresWriter.flush();
            fosScoresWriter.close();
            oswScoresWriter.close();
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        }
        catch(IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());

        }
    }


    /* Method to load/read score from file to an array list of highscore objects
    * param scoreArrayList: target array list to store scores
    * param fileName: name of file to load,
    * note: only name, not path
    * all input/output files are supposed to be stored in default external storage directory on the device
    */
    public void readScoresFromFile(ArrayList<HighScore> scoreArrayList, String fileName)
    {
        try
        {
            File scoreFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
            Scanner scoreScanner = new Scanner(scoreFile);

            SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String textLine;
            while( scoreScanner.hasNextLine() )
            {
                textLine = scoreScanner.nextLine();
                String[] tokens = textLine.split("\t",3);
                Date scoreDate;
                if (tokens.length == 3)
                {
                    try
                    {
                        scoreDate = dtFormat.parse(tokens[2]);
                    }
                    catch (java.text.ParseException e)
                    {
                        scoreDate = new Date() ;
                    }
                    HighScore aHighScore = new HighScore(tokens[0],Integer.parseInt(tokens[1]), scoreDate );
                    scoreArrayList.add(aHighScore);
                }
                else if (tokens.length > 0)
                {
                    scoreDate = new Date() ;
                    HighScore aHighScore = new HighScore(tokens[0], 888, scoreDate);
                    scoreArrayList.add(aHighScore);
                }

            }
            scoreScanner.close();
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        }
        catch(IOException e)
        {
            Log.e("Exception", "File read failed: " + e.toString());

        }
    }

}

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
 * This is the HighScore class, which define a HighScore item, i.e. a group of 3 variables - Name, Score, Date - packed into 1 object.
 *
 */

package com.example.balloonspop;

import java.io.Serializable;
import java.util.Date;

public class HighScore implements Comparable<HighScore> {
    String scoreName;
    int score;
    Date scoreDate;

    public HighScore(String name, int highScore, Date date)
    {
        this.scoreName = name;
        this.score = highScore;
        this.scoreDate = date;
    }

    public String getScoreName()
    {
        return scoreName;
    }
    public void setScoreName(String name)
    {
        scoreName = name;
    }

    public int getScore()
    {
        return score;
    }
    public void setScore(int highScore)
    {
        score = highScore;
    }

    public Date getScoreDate()
    {
        return scoreDate;
    }
    public void setScoreDate(Date date)
    {
        scoreDate = date;
    }

    public int compareTo( HighScore otherHighscore)
    {
        int result;
        if (this.score > otherHighscore.getScore() )
        {
            return result = 1;
        }
        else if (this.score < otherHighscore.getScore() )
        {
            return result = -1;
        }
        else
            // ==
        {
            result = this.scoreDate.compareTo(otherHighscore.getScoreDate() );
            if (result == 0)
            {
                return result = -(this.scoreName.compareToIgnoreCase(otherHighscore.getScoreName() ) );
            }
            else
            {
                return result;
            }
        }
    }

}

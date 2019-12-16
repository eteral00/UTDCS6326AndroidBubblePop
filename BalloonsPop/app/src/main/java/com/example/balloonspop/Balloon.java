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
 * This is the Balloon class, which define a Balloon object.
 *
 */




package com.example.balloonspop;



public class Balloon {

    // class data members
    public int round; // 0 = circle, any other = square
    public int color;
    public int diaSizeDP;
    public int speed;
    public int posX;
    public int posY;


    /* constructor
    *
    */
    public Balloon ( int newShape, int newColor, int newSize, int newSpeed, int newX, int newY)
    {
        this.round = newShape;
        this.color = newColor;
        this.diaSizeDP = newSize;
        this.speed = newSpeed;
        this.posX = newX;
        this.posY = newY;

    }

    /* method to adjust posY by speed, i.e. balloon rising up
    *
    */
    public void riseUp()
    {
        this.posY -= this.speed;
    }

    /* method to adjust posY when collide, i.e. balloon falling down
    *
    */
    public void fallDown()
    {
        this.posY += this.speed;
    }
} // end of class Balloon

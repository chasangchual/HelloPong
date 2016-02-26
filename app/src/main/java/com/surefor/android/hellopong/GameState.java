package com.surefor.android.hellopong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Ethan on 2016-02-23.
 */
public class GameState {
    public static final int H_SPACE = 130 ;
    public static final int V_SPACE = 40 ;

    int width ;
    int height ;

    int boardTop = H_SPACE ;
    int boardLeft = V_SPACE ;
    int boardBottom ;
    int boardRight ;

    final int ballSize = 10 ;

    int ballX = 100 ;
    int ballY = 100 ;
    int ballVelocityX = 7 ;
    int ballVelocityY = 7 ;

    final int batLength = 75 ;
    final int batHeight = 10 ;

    int topBatX = (width / 2 ) - (batLength / 2) ;
    int topBatY = boardTop + batHeight  ;

    int bottomBatX = (width / 2 ) - (batLength / 2) ;
    int bottomBatY = 400 ;

    public GameState() {

    }

    public void update() {
        ballX += ballVelocityX ;
        ballY += ballVelocityY ;

        // collisions with the slides
        if(ballY > boardBottom || ballY < boardTop) {
            ballX = width / 2 ;
            ballY = height / 2 ;
        }

        // collisions with the bats
        if(ballX > boardRight || ballX < boardLeft) {
            ballVelocityX *= -1 ;
        }

        // collisions with the bats
        if(ballX > topBatX && ballX < topBatX + batLength && ballY < topBatY) {
            ballVelocityY *= -1 ;
        }

        if(ballX > bottomBatX && ballX < bottomBatX + batLength && ballY > bottomBatY) {
            ballVelocityY *= -1 ;
        }
    }

    public boolean moveTopBarLeft(int distance) {
        topBatX -= distance ;
        if(topBatX < boardLeft) {
            topBatX += distance ;
        }
        return false ;
    }

    public boolean moveTopBarRight(int distance) {
        topBatX += distance ;
        if((topBatX + batLength) > boardRight) {
            topBatX -= distance ;
        }
        return false ;
    }

    public boolean moveBottomBarLeft(int distance) {
        bottomBatX -= distance ;
        if(bottomBatX < boardLeft) {
            bottomBatX += distance ;
        }
        return false ;
    }

    public boolean moveBottomBarRight(int distance) {
        bottomBatX += distance ;
        if((bottomBatX + batLength) > boardRight) {
            bottomBatX -= distance ;
        }
        return false ;
    }

    //the draw method
    public void draw(Canvas canvas, Paint paint) {

        //Clear the screen
        canvas.drawRGB(20, 20, 20);

        //set the colour
        paint.setARGB(200, 0, 200, 0);

        //draw the ball
        canvas.drawRect(new Rect(ballX, ballY, ballX + ballSize, ballY + ballSize), paint);

        //draw the bats
        canvas.drawRect(new Rect( topBatX, topBatY, topBatX + batLength, topBatY + batHeight), paint); //top bat
        canvas.drawRect(new Rect( bottomBatX, bottomBatY, bottomBatX + batLength, bottomBatY + batHeight), paint); //bottom bat

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        boardRight = width - V_SPACE ;
        ballX = width / 2 ;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        boardBottom = height - H_SPACE ;
        bottomBatY = boardBottom - batHeight ;
        ballY = height / 2 ;
    }
}

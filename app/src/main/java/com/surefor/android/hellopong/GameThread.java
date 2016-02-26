package com.surefor.android.hellopong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

/**
 * Created by Ethan on 2016-02-23.
 */
public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder ;
    private Paint paint ;
    private GameState gameState;

    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
        this.surfaceHolder = surfaceHolder ;
        this.paint = new Paint() ;
        this.gameState = new GameState() ;
    }

    @Override
    public void run() {
        while(!this.isInterrupted()) {
            Canvas canvas = surfaceHolder.lockCanvas() ;
            gameState.update();
            gameState.draw(canvas, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public GameState getGameState() {
        return gameState ;
    }
}

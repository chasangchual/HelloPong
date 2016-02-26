package com.surefor.android.hellopong;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ethan on 2016-02-23.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread ;
    private int topx1, topx2;
    private int bottomx1, bottomx2;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder surfaceHolder = getHolder() ;
        surfaceHolder.addCallback(this);
        setFocusable(true);

        thread = new GameThread(surfaceHolder, context, new Handler()) ;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.getGameState().setWidth(this.getWidth());
        thread.getGameState().setHeight(this.getHeight());
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.getGameState().setWidth(width);
        thread.getGameState().setHeight(height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(!thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    public void start() {
        thread.start();
    }

    public void interrupt() {
        thread.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x, y ;
        int count = 0 ;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                count = event.getPointerCount() ;

                for(int i = 0 ; i < count; i++) {
                    x = (int) event.getX(i) ;
                    y = (int) event.getY(i) ;

                    if(y > (this.getHeight() / 2)) {
                        bottomx1 = x ;
                    } else {
                        topx1 = x ;
                    }
                }
                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                break;

            // Player has swiped the screen
            case MotionEvent.ACTION_MOVE:
                count = event.getPointerCount() ;

                for(int i = 0 ; i < count; i++) {
                    x = (int) event.getX(i) ;
                    y = (int) event.getY(i) ;

                    if(y > (this.getHeight() / 2)) {
                        bottomx2 = x ;
                        if(bottomx2 > bottomx1) {
                            thread.getGameState().moveBottomBarRight(bottomx2 - bottomx1) ;
                        } else {
                            thread.getGameState().moveBottomBarLeft(bottomx1 - bottomx2) ;
                        }
                        bottomx1 = bottomx2 ;
                    } else {
                        topx2 = x ;
                        if(topx2 > topx1) {
                            thread.getGameState().moveTopBarRight(topx2 - topx1) ;
                        } else {
                            thread.getGameState().moveTopBarLeft(topx1 - topx2) ;
                        }
                        topx1 = topx2 ;
                    }
                }
        }
        return true ;
    }
}

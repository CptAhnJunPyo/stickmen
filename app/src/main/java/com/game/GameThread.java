package com.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import com.game.GameView;
public class GameThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean running;
    private static final long FRAME_TIME = 1000 / 60;  // 60 FPS

    public GameThread(SurfaceHolder holder, GameView view) {
        surfaceHolder = holder;
        gameView = view;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();

        while (running) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    gameView.update1();
                    gameView.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            // Control frame rate
            long currentTime = System.nanoTime();
            long deltaTime = (currentTime - lastTime) / 1000000; // Convert to milliseconds
            if (deltaTime < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - deltaTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lastTime = System.nanoTime();
        }
    }
}

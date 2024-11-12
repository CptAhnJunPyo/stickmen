package com.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.managers.GameManager;
import com.models.Ability;
import com.models.Character;
import com.models.Player;
import com.models.PlayerStateType;
import com.utils.Constants;
import com.models.Vector2D;
import com.models.PlayerState;
import com.ui.Button;
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Player player1;
    private Player player2;
    private Paint paint;
    private double screenWidth;
    private double screenHeight;
    private Button attackButton;
    private Button jumpButton;
    private double lastTouchX;
    private long lastAttackTime;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        paint = new Paint();
        setupGame();
    }

    private void setupGame() {
        // Create characters
        Character warrior = new Character.Builder(1, "Warrior", 20.0, 100.0, 120.0, 1.5, 15.0).build();
        Character ninja = new Character.Builder(2, "Ninja", 15.0, 80.0, 150.0, 1.0, 20.0).build();

        // Create Ability
        Ability ability = new Ability(1, 1.5, 5.0);
        // Create players
        player1 = new Player(1, warrior, 200, 500);
        player2 = new Player(2, ninja, 800, 500);

        // Initialize touch controls
        attackButton = new Button(100, 900, 200, 1000, "Attack");
        jumpButton = new Button(300, 900, 400, 1000, "Jump");
        lastAttackTime = SystemClock.uptimeMillis();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenWidth = getWidth();
        screenHeight = getHeight();
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = x;
                if (attackButton.contains(x, y)) {
                    player1.attack(player2);
                } else if (jumpButton.contains(x, y)) {
                    player1.jump();
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                double dx = x - lastTouchX;
                if (Math.abs(dx) > 5) {  // Small threshold to prevent tiny movements
                    player1.move(dx > 0 ? 1 : -1, 0);
                }
                lastTouchX = x;
                return true;
        }
        return super.onTouchEvent(event);
    }
    private boolean canAttack() {
        long currentTime = SystemClock.uptimeMillis();
        return currentTime - lastAttackTime >= (1000 / player1.getCharacter().getAttackCooldown());
    }
    public void update() {
        // Update player positions and states
        player1.update(0.016f);  // ~60 FPS
        player2.update(0.016f);

        // Simple AI for player2
        if (player2.canAttack(player1)) {
            player2.attack(player1);
        } else {
            // Move towards player1
            double dx = player1.getPosition().x - player2.getPosition().x;
            player2.move(dx > 0 ? 1 : -1, 0);
        }

        // Keep players within screen bounds
        player1.keepInBounds(0, screenWidth);
        player2.keepInBounds(0, screenWidth);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // Draw background
            canvas.drawColor(Color.WHITE);

            // Draw ground
            paint.setColor(Color.GRAY);
            canvas.drawRect((float) 0, (float) (screenHeight - 100), (float) screenWidth, (float) screenHeight, paint);

            // Draw players
            drawPlayer(canvas, player1, Color.BLUE);
            drawPlayer(canvas, player2, Color.RED);

            // Draw UI
            drawUI(canvas);

            // Draw health bars
            drawHealthBar(canvas, player1, 50, 50);
            drawHealthBar(canvas, player2, screenWidth - 250, 50);
        }
    }
    public void update1() {
        double deltaTime = 1.0f / Constants.FPS;
        GameManager.getInstance().update(deltaTime);
    }
    private void drawPlayer(Canvas canvas, Player player, int color) {
        paint.setColor(color);
        Vector2D pos = player.getPosition();

        // Draw body
        canvas.drawRect((float) (pos.x - 25), (float) pos.y - 50, (float) pos.x + 25, (float) pos.y, paint);

        // Draw head
        canvas.drawCircle((float) pos.x, (float) (pos.y - 65), 15, paint);

        // Draw attack animation if attacking
        if (player.getState().getType() == PlayerStateType.ATTACKING) {
            paint.setColor(Color.YELLOW);
            double attackX = player.getFacingRight() ? pos.x + 50 : pos.x - 50;
            canvas.drawCircle((float) attackX, (float) (pos.y - 25), 20, paint);
        }
    }

    private void drawUI(Canvas canvas) {
        paint.setColor(Color.LTGRAY);
        attackButton.draw(canvas, null);
        jumpButton.draw(canvas, null);
    }

    private void drawHealthBar(Canvas canvas, Player player, double x, double y) {
        double width = 200;
        double height = 20;
        double healthPercentage = (player.getCharacter().getHp() / 100f);

        // Draw background
        paint.setColor(Color.GRAY);
        canvas.drawRect((float) x, (float) y, (float) (x + width), (float) (y + height), paint);

        // Draw health
        paint.setColor(Color.GREEN);
        canvas.drawRect((float) x, (float) y, (float) (x + (width * healthPercentage)), (float) (y + height), paint);
    }
    public void pause() {
        gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void onAttackButtonClicked() {
        player1.attack(player2);
    }

    public void onJumpButtonClicked() {
        player1.jump();
    }

    public void resume() {
        gameThread.setRunning(true);
        gameThread.start();
    }

    public Player getPlayer(Player player) {
        return player;
    }
}

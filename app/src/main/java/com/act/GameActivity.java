package com.act;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.game.GameView;
import com.map.tuankietvuong.stickmanfighting.R;
import com.ui.Button;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    private ProgressBar player1HealthBar, player2HealthBar;
    private Button attackButton, jumpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get the GameView from the layout
        gameView = findViewById(R.id.game_view);

        // Get the UI elements


    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume(); // Start the game loop
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause(); // Pause the game loop
    }

    public void updatePlayerHealthBars() {
        // Update the progress of the health bars based on the player's health values
        //player1HealthBar.setProgress((int) (gameView.getPlayer().getCharacter().getHp()));
        //player2HealthBar.setProgress((int) (gameView.getPlayer().getCharacter().getHp()));
    }
}
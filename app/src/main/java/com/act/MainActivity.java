package com.act;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.game.GameView;
import com.map.tuankietvuong.stickmanfighting.R;
import com.models.Player;
public class MainActivity extends AppCompatActivity {
    public Button btnStart, btnCharacter, btnCharacterList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.start_btn);
    }
}
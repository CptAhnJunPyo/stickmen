package com.act;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.map.tuankietvuong.stickmanfighting.R;
import com.models.Character;

public class CharacterAdditionActivity extends AppCompatActivity {
    public EditText characterName;
    public EditText attackPowerEditText;
    public EditText healthEditText;
    public EditText speedEditText;
    public TextView tvName, tvAttack, tvHealth, tvSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_addition);
        tvName = findViewById(R.id.character_label);
        tvAttack = findViewById(R.id.attack_tv);
        tvHealth = findViewById(R.id.health_tv);
        tvSpeed = findViewById(R.id.speed_tv);
        characterName = findViewById(R.id.character_name);
        attackPowerEditText = findViewById(R.id.attack_power_edit_text);
        healthEditText = findViewById(R.id.health_edit_text);
        speedEditText = findViewById(R.id.speed_edit_text);


        // Set up save button click listener
        findViewById(R.id.save_button).setOnClickListener(v -> saveCharacterAddition());
    }


    private void saveCharacterAddition() {
        String additionCharacterName = characterName.getText().toString();
        double attackPower = Double.parseDouble(attackPowerEditText.getText().toString());
        double health = Double.parseDouble(healthEditText.getText().toString());
        double speed = Double.parseDouble(speedEditText.getText().toString());

        // Create a new Character object with the selected attributes
        Character selectedCharacter = new Character.Builder(additionCharacterName, attackPower, health, speed).build();

        // Save the selected character in a shared preference or local variable

        // to be used when setting up the game
    }
}

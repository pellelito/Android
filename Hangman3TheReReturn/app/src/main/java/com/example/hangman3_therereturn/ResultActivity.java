package com.example.hangman3_therereturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView name = findViewById(R.id.nameTextView);
        TextView time = findViewById(R.id.timeTextView);
        TextView attemts = findViewById(R.id.AttemtsTextView);
        TextView guessed = findViewById(R.id.guessedTextView);
        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        time.setText(intent.getStringExtra("time") + " seconds left");
        attemts.setText(intent.getStringExtra("attemtsLeft") +" attemts left");
        guessed.setText("You have guessed: " + intent.getStringExtra("guessed"));

    }
}

package com.example.hangman3_therereturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPref.init(getApplicationContext());

        Button startBtn = (Button)findViewById(R.id.startBtn);
        Boolean gameOn = SharedPref.read(SharedPref.IS_SELECT, false);
        if(gameOn){
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(intent);
        }
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set default name
                String name = "Looser";
                EditText nameEditText = (EditText)findViewById(R.id.nameEditText);

                if(nameEditText.getText() != null){
                    name = nameEditText.getText().toString();
                    Log.d("myTag",name);
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }else{
                Log.d("myTag",name);
                }

            }
        });
    }
}

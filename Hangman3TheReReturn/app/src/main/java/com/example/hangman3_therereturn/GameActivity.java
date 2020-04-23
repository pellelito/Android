package com.example.hangman3_therereturn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity {

    String savedName = SharedPref.read(SharedPref.NAME, null); //read string in shared preference.
    int savedAttempts = SharedPref.read(SharedPref.ATTEMTS, 0); //read int in shared preference.
    boolean isSelect = SharedPref.read(SharedPref.IS_SELECT, false); //read boolean in shared preference.
    String savedWord = SharedPref.read(SharedPref.WORD,"correctWord");
    String hidden = SharedPref.read(SharedPref.HIDDEN, "hidden");
    String guessed = SharedPref.read(SharedPref.GUESSED, "guessed");
    int time = SharedPref.read(SharedPref.TIME, 100);

    String name;

    CountDownTimer cTimer = null;

   @Override
   protected void onPause() {
       super.onPause();
        cancelTimer();

            SharedPref.write(SharedPref.NAME, name);//save string in shared preference.
            SharedPref.write(SharedPref.ATTEMTS, savedAttempts);//save int in shared preference.
            SharedPref.write(SharedPref.IS_SELECT, isSelect);//save boolean in shared preference.
            SharedPref.write(SharedPref.WORD, savedWord);
            SharedPref.write(SharedPref.GUESSED, guessed);
            SharedPref.write(SharedPref.HIDDEN, hidden);
            SharedPref.write(SharedPref.TIME, time);
            Log.d("myTag","Game On saved");
       
       Log.d("myTag","Pause");
   }
    @Override
    protected  void onResume(){
       super.onResume();

         startTimer(time * 1000);
       Log.d("myTag","Resume");
    }
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final PlayGame game = new PlayGame();
        TextView answer = findViewById(R.id.wordToFindTextView);
        TextView countDown = findViewById(R.id.CountDownTextView);
        final Button guessBtn = findViewById(R.id.guessBtn);
        final ImageView img = findViewById(R.id.imageView);
        final EditText charToGuess = findViewById(R.id.charToGuess);


        Intent intent = getIntent();
        if (isSelect){
            name = savedName;
            game.attemts = savedAttempts;
            game.correctAnswer = savedWord;
            game.hiddenAnswer.append(hidden);
            game.guesses.append(guessed);
            answer.setText(game.hiddenAnswer);
            //game.gameOn = isSelect;
            img.setImageResource(getImg(game.attemts));
            Toast.makeText(getApplicationContext(),"Welcome back " + name,Toast.LENGTH_SHORT).show();

            Log.d("myTag",game.correctAnswer);
            Log.d("myTag",game.guesses.toString());
            Log.d("myTag","File read OK");

        } else{
            name = intent.getStringExtra("name");
            Toast.makeText(getApplicationContext(),"Welcome " + name,Toast.LENGTH_SHORT).show();
            game.loadGame();
            isSelect = true;
            answer.setText(game.hiddenAnswer);
            time = 100;
            countDown.setText(String.valueOf(time));
            // startTimer(time * 1000);
        }

        guessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answer = findViewById(R.id.wordToFindTextView);
                Log.d("myTag","Check1");
                // Kraschar här på runda två
                if(!isSelect){
                    Log.d("myTag","Check2");

                    game.loadGame();
                     isSelect = true; 
                    time = 100;
                    startTimer(time * 1000);
                    guessBtn.setText("Guess");

                }

                if(game.guesses.toString().contains(charToGuess.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Already guessed this sucker",Toast.LENGTH_SHORT).show();

                }else if(charToGuess.getText().toString().length()==1){
                         game.checkAnswer(charToGuess.getText().toString());
                }

                if(game.attemts == 8){
                     Toast.makeText(getApplicationContext(),"Game over Bitch!",Toast.LENGTH_LONG).show();
                     gameOverBitch();

                }
                
                if(game.correctAnswer.equals(game.hiddenAnswer.toString())||(game.correctAnswer.equals(charToGuess.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"You're a Winner!",Toast.LENGTH_LONG).show();
                    isSelect = false;
                    guessBtn.setText("Let me win again");
                    answer.setText(game.correctAnswer);
                    cancelTimer();
                }
                if(charToGuess.getText().toString().length()>1){
                   game.attemts++;
                }
                answer.setText(game.hiddenAnswer);         
                charToGuess.setText("");
                img.setImageResource(getImg(game.attemts));
                     savedAttempts = game.attemts;
                     // isSelect = game.gameOn;
                     savedWord = game.correctAnswer;
                     hidden = game.hiddenAnswer.toString();
                     guessed = game.guesses.toString();


            }

            public void gameOverBitch() {
                   isSelect = false;
                   guessBtn.setText("Try Again");
                   cancelTimer();
            }
        });


    }

    private int  getImg(int index){
        switch (index){
            case 0: return R.drawable.a;
            case 1: return R.drawable.b;
            case 2: return R.drawable.c;
            case 3: return R.drawable.d;
            case 4: return R.drawable.e;
            case 5: return R.drawable.f;
            case 6: return R.drawable.g;
            case 7: return R.drawable.h;
            case 8: return R.drawable.i;
            default: return -1;
        }
    }
    public void startTimer(int timer) {
       Log.d("myTag","Timer start");
       final TextView countDown = findViewById(R.id.CountDownTextView);
       final Button guessBtn = findViewById(R.id.guessBtn);


        cTimer = new CountDownTimer(timer, 1000) {
            public void onTick(long millisUntilFinished) {
                time--;
                countDown.setText(String.valueOf(time));
            }
            public void onFinish() {
                Log.d("myTag","Timer ended");
                Toast.makeText(getApplicationContext(),"Game over Bitch!",Toast.LENGTH_LONG).show();
                isSelect = false;
                guessBtn.setText("Try Again");
                                                                       
            }
        };
        cTimer.start();
    }


    //cancel timer
    public void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
    public void seeUser(View v){
       PlayGame g = new PlayGame();
       Intent seeUser = new Intent(getApplicationContext(),ResultActivity.class);
       onPause();
       seeUser.putExtra("name", name);
       seeUser.putExtra("time", Integer.toString(time));
       seeUser.putExtra("attemtsLeft", String.valueOf(8-savedAttempts) );
       seeUser.putExtra("guessed", guessed);

       startActivity(seeUser);


    }
}

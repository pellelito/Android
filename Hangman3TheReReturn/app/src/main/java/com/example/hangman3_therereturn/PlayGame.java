package com.example.hangman3_therereturn;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayGame extends AppCompatActivity {
    //Boolean gameOn;
    int attemts;
    String correctAnswer="";
    StringBuilder guesses = new StringBuilder();
    StringBuilder hiddenAnswer = new StringBuilder();

    public void loadGame(){
        //sets up new game, clears answers and sets a new correct password
        Random random = new Random();
        String[] words = {"java","boolean","integer","array","string","length","void"};
                //this.getResources().getStringArray(R.array.words);
        //gameOn = true;
        correctAnswer = words[random.nextInt(words.length)];
        hiddenAnswer.setLength(0);
        for(int i =0; i < correctAnswer.length();i++ ){
            hiddenAnswer.append("*");
        }
        Log.d("myTag",correctAnswer);
        attemts = 0;
        guesses.setLength(0);

    }


    public void checkAnswer(String s){
        // change answer to lowercase
        s = s.toLowerCase().replace(" ", "");
            //check if letter is in correctWord
            if(correctAnswer.toString().contains(s)){
                //shows where correct letter is i word
                for(int i=0;i<correctAnswer.length();i++) {
                    if(correctAnswer.charAt(i) == s.charAt(0)) {
                        char a = s.charAt(0);
                        hiddenAnswer.setCharAt(i, s.charAt(0));
                    }
                }
        }else{
                // increase faild attemt
                attemts++;
            }
        // adds to sting of guesses
        guesses.append(s);
        Log.d("myTag", String.valueOf(guesses));

    }



}

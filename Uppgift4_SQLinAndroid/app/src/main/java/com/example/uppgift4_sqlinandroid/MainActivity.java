package com.example.uppgift4_sqlinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button addBtn;
    Button viewBtn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editDBText);
        addBtn = (Button)findViewById(R.id.addBtn);
        viewBtn = (Button)findViewById(R.id.viewBtn);
        db = new DatabaseHelper(this);

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewListContents.class);
                startActivity(intent);
            }
        });

        // calls a function to add a new entry
         addBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name = editText.getText().toString();
                 if(editText.length() != 0){
                     AddData(name);
                     editText.setText("");
                 }else{
                    // shold be a toast in prod
                     Log.d("myapp", "You must enter something in th textfield");
                     Toast. makeText(getApplicationContext(),"You must enter something in th textfield",Toast. LENGTH_SHORT).show();
                 }
             }
         });
    }

   public void AddData(String name){
        Contact contact = new Contact(-1,name);
        boolean insertData = db.addData(contact);

        if(insertData){
            // shold be a toast in prod
            Log.d("myapp", "Entry successfully updated");

        }else{
            // shold be a toast in prod
            Log.d("myapp", "Something went wrong :-(");
            Toast. makeText(getApplicationContext(),"Something went wrong :-(",Toast. LENGTH_SHORT).show();

        }
    }
}

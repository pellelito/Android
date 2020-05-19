package com.example.uppgift4_sqlinandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateDB extends AppCompatActivity {
    DatabaseHelper db;
    Button deleteBtn;
    Button updateBtn;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_d_b);

        editText = (EditText)findViewById(R.id.editDBText);
        textView = (TextView)findViewById(R.id.textView);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        db = new DatabaseHelper(this);

        //gets the tru ID from caller activity
        Integer id = Integer.parseInt(getIntent().getStringExtra("ID"));

        //gets the contact we want to edit or delete
        final Contact contact = db.getContact(id) ;

        // sets name
        editText.setText(contact.getName());

        //shows ID (not editable)
        textView.setText("ID nr: " + contact.getID());

        //deletes the entry from database
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               db.deleteContact(contact);
                Intent intent = new Intent(getApplicationContext(), ViewListContents.class);
                startActivity(intent);
            }
        });
        // uppdates the current entry in database
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              contact.setName(editText.getText().toString());
              db.updateContact(contact);
                Intent intent = new Intent(getApplicationContext(), ViewListContents.class);
                startActivity(intent);
            }
        });
    }
}

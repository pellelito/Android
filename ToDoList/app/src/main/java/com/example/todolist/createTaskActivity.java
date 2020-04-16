package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class createTaskActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        context = this;
        Button submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(getApplicationContext(), MainActivity.class);
                EditText itemEditText = (EditText) findViewById(R.id.ItemEditText);
                EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

                //Recover TaskList from previous activity
                TaskList taskList = TaskList.fromString(getIntent().getStringExtra("com.example.todolist.taskList"));
                if (taskList == null) { //if not found make a new one
                    taskList = new TaskList();
                }
                taskList.addTask(new Task(itemEditText.getText().toString(), descriptionEditText.getText().toString()));

                //Send taskList as String
                addItemIntent.putExtra("com.example.todolist.taskList", taskList.toString());

                startActivity(addItemIntent);
            }
        });
    }
}

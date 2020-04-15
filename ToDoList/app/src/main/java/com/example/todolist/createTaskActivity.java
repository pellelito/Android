package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class createTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        final Context context = this;
        Button submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(getApplicationContext(), MainActivity.class);
                EditText itemEditText = (EditText) findViewById(R.id.ItemEditText);
                EditText descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);

                Toast.makeText(context, getIntent().getStringExtra("com.example.todolist.taskList"), Toast.LENGTH_LONG).show();
                TaskList taskList = TaskList.fromString(getIntent().getStringExtra("com.example.todolist.taskList"));
                if (taskList == null) {
                    taskList = new TaskList();
                }
                taskList.addTask(new Task(itemEditText.getText().toString(), descriptionEditText.getText().toString()));

                addItemIntent.putExtra("com.example.todolist.taskList", taskList.toString());

                startActivity(addItemIntent);
            }
        });
    }
}

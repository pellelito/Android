package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TaskList taskList = new TaskList();
    ItemAdaptor itemAdaptor;
    ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign listeners
        Button addBtn = (Button)findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submitTask = new Intent(getApplicationContext(), createTaskActivity.class);
                submitTask.putExtra("com.example.todolist.taskList", taskList.toString());
                startActivity(submitTask);
            }
        });

        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.deleteChecked();
                taskListView.setAdapter(itemAdaptor);
            }
        });

        taskListView  = (ListView) findViewById(R.id.taskListView);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                taskList.toggleChecked(position);
                taskListView.setAdapter(itemAdaptor);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();

        if (getIntent().hasExtra("com.example.todolist.taskList")){
            taskList = TaskList.fromString(getIntent().getStringExtra("com.example.todolist.taskList"));
        } else {
            try {
                FileInputStream fIn = openFileInput("taskList");

                int c;
                StringBuilder data = new StringBuilder();
                while((c = fIn.read()) != -1){
                    data.append(Character.toString((char)c));
                }

                taskList = TaskList.fromString(data.toString());

                fIn.close();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        itemAdaptor = new ItemAdaptor(this, taskList);
        taskListView.setAdapter(itemAdaptor);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        try {
            FileOutputStream fOut = openFileOutput("taskList", Context.MODE_PRIVATE);
            Toast.makeText(this, taskList.toString(), Toast.LENGTH_SHORT);
            fOut.write(taskList.toString().getBytes());
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.uppgift4_sqlinandroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contacts_layout);

        ListView listView = (ListView)findViewById(R.id.listView);
        db = new DatabaseHelper(this);

        ArrayList<String> myList = new ArrayList<>();
        final ArrayList<Integer> idList = new ArrayList<>();

        //gets all element i database into data
        final Cursor data = db.getListContents();

        if(data.getCount() == 0){

            Toast. makeText(getApplicationContext(),"The database is empty",Toast. LENGTH_SHORT).show();

        }else{
            while(data.moveToNext()){
                 // sets data into two arrays
                 myList.add(data.getString(1));
                 idList.add(data.getInt(0));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, myList);
                listView.setAdapter(listAdapter);
            }                                                               
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // gets the index of selected item and passes this forward
                int trueID = idList.get(position);
                Log.d("myapp","ID: " + trueID);
                Intent intent = new Intent(getApplicationContext(), UpdateDB.class);
                intent.putExtra("ID", String.valueOf(trueID));
                startActivity(intent);
            }
        });
    }
}

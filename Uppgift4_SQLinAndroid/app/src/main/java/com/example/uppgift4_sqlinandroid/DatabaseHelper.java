package com.example.uppgift4_sqlinandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contacts.db";
    public static final String TABLE_NAME = "contacts_data";
    public static final String COL0 = "ID";
    public static final String COL1 = "NAME";

    public DatabaseHelper(Context context){super(context, DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);

    }
        // adds new entry to database
    public boolean addData(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, contact.getName());

        long result = db.insert(TABLE_NAME, null, values);

        // for errorhandling
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {

        //gets all elements

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public void deleteContact(Contact contact){

        // deletes an entry in database
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COL0 + " = ?",new String[]{String.valueOf(contact.getID())});
        db.close();
    }

    public int updateContact(Contact contact){

        // updates an entry in the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, contact.getName());
        return db.update(TABLE_NAME,values,COL0 + " = ?",new String[]{String.valueOf(contact.getID())});

    }
    Contact getContact(int id){
        // gets a spec entry from database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT *  FROM " + TABLE_NAME + " WHERE ID = " + id, null);

        if(data !=null){
            data.moveToFirst();
        }
        Contact contact = new Contact(Integer.parseInt(data.getString(0)),data.getString(1));
        return contact;
    }

}




package com.example.uppgift4_sqlinandroid;

public class Contact {
    private int _id;
    private String _name;

    public Contact(int id, String name){
         this._id=id;
         this._name=name;
    }
    public int getID(){
        return this._id;
    }

    public String getName() { return this._name; }

    public void setID(int id) {
        this._id = id;
    }

    public void setName(String name) {
        this._name = name;
    }
}

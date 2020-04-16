package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdaptor extends BaseAdapter {

    private LayoutInflater inflator;
    private TaskList taskList;

    public ItemAdaptor(Context context, TaskList taskList){
        this.taskList = taskList;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.getTask(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // filling up the list

        View v = inflator.inflate(R.layout.my_listview_layout, null);

        TextView itemTextView = (TextView) v.findViewById(R.id.itemTextView);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);

        itemTextView.setText(taskList.getItemName(position));
        descriptionTextView.setText(taskList.getItemDescription(position));

        if (taskList.isChecked(position)) { //highlight when presseed
            v.setBackgroundColor(parent.getResources().getColor(R.color.highlight));
        } else {
            v.setBackgroundColor(parent.getResources().getColor(R.color.not_highlight));
        }

        return v;
    }
}

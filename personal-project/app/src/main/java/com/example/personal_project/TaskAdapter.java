package com.example.personal_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final ArrayList<StudyTask> tasks;

    public TaskAdapter(Context context, ArrayList<StudyTask> tasks) {
        this.tasks = tasks;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.task_list_item, parent, false);
        TextView titleTextView = (TextView) row.findViewById(R.id.taskTitleTextView);
        TextView categoryTextView = (TextView) row.findViewById(R.id.categoryTextView);
        TextView statusTextView = (TextView) row.findViewById(R.id.statusTextView);

        StudyTask task = tasks.get(position);
        titleTextView.setText(task.getTitle());
        categoryTextView.setText(task.getCategory());
        statusTextView.setText(task.isImportant() ? "Important" : "Normal");

        return row;
    }
}

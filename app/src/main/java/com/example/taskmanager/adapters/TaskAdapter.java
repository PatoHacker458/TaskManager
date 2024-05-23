package com.example.taskmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.taskmanager.R;
import com.example.taskmanager.models.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TaskAdapter extends BaseAdapter {
    Context context;
    List<Task> taskList;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    public TaskAdapter(Context context, List<Task> tasks){
        this.context = context;
        this.taskList = tasks;
    };
    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.task_item,null);
        }
        Task task = (Task) getItem(position);
        TextView txtTaskName = convertView.findViewById(R.id.txtTaskName);
        TextView txtTaskCompleted = convertView.findViewById(R.id.txtTaskCompleted);

        txtTaskName.setText(task.getName());
        txtTaskCompleted.setText((task.getCompleted())?"Completed":"Pending");

        return convertView;
    }
}

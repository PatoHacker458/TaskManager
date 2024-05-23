package com.example.taskmanager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmanager.adapters.TaskAdapter;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {
    ListView lvTaskView;
    private FloatingActionButton fab;
    private User user;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvTaskView = findViewById(R.id.lvTaskView);

        Bundle bundle = getIntent().getExtras();
        user =(User) bundle.getSerializable("user");
        String full_name = user.getFirst_name() + user.getLast_name();

        initFab();
        readFirebaseTask(user.getUid());
    }

    private void showAddTaskForm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.task_add_form, null);
        builder.setView(view);

        EditText txtTaskName = view.findViewById(R.id.txtTaskName);
        EditText txtDescription = view.findViewById(R.id.txtTaskDescription);
        EditText txtDeadline = view.findViewById(R.id.txtTaskDeadline);
        Button btnAddTask = view.findViewById(R.id.btnAddTask);
        Button btnDismissTask = view.findViewById(R.id.btnDismissTask);

        Dialog dialog = builder.create();
        btnDismissTask.setOnClickListener((v)->{
            btnDismissTask.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            dialog.dismiss();
        });
        btnAddTask.setOnClickListener((v)->{
            btnAddTask.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            String taskId = UUID.randomUUID().toString();
            Task newTask = new Task(taskId,
                    txtTaskName.getText().toString(),
                    txtDescription.getText().toString(),
                    txtDeadline.getText().toString(),
                    false,user.getUid());
            Toast.makeText(this, "Task Created", Toast.LENGTH_SHORT).show();
            createFirbaseTask(newTask);
            dialog.dismiss();
                });
        dialog.show();
    }
    private void createFirbaseTask(Task task){
        DatabaseReference ref = db.getReference("Tasks/"+task.getId());
        ref.setValue(task);
    }
    private void initFab(){
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            fab.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            showAddTaskForm();
        });
    }
    List<Task> taskList = new ArrayList<>();
    private void readFirebaseTask(String userId){
        DatabaseReference ref = db.getReference("Tasks/");
        Query query = ref.orderByChild("uid").equalTo(userId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskList.clear();
                for(DataSnapshot item: snapshot.getChildren()){
                    Task task = item.getValue(Task.class);
                    taskList.add(task);
                }
                TaskAdapter adapter = new TaskAdapter(HomeActivity.this, taskList);
                lvTaskView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }
}
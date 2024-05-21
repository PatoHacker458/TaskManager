package com.example.taskmanager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        String [] arrTasks = {"Task 1", "Task 2", "Task 3", "Task 4", "Task 5", "Task 6", "Task 7", "Task 8", "Task 9", "Task 10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.select_dialog_multichoice,
                arrTasks);
        lvTaskView = findViewById(R.id.lvTaskView);
        lvTaskView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvTaskView.setAdapter(adapter);


        Bundle bundle = getIntent().getExtras();
        user =(User) bundle.getSerializable("user");
        String full_name = user.getFirst_name() + user.getLast_name();

        initFab();
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
            createFirbaseTask(newTask);
            dialog.dismiss();
                });
        dialog.show();
    }
    private void createFirbaseTask(Task task){
        DatabaseReference ref = db.getReference("Tasks/ "+task.getId());
        System.out.println(task.getName());
        ref.setValue(task);
    }
    private void initFab(){
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            fab.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            showAddTaskForm();
        });
    }
}
package com.example.taskmanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmanager.models.User;

public class HomeActivity extends AppCompatActivity {
    //TextView welcome;
    ListView lvTaskView;
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

        String [] arrTasks = {"Task 1", "Task 2", "Task 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.select_dialog_multichoice,
                arrTasks);
        lvTaskView = findViewById(R.id.lvTaskView);
        lvTaskView.setAdapter(adapter);

        /*welcome = findViewById(R.id.welcome);
        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable("user");
        String full_name = user.getFirst_name() + " " + user.getLast_name();
        welcome.setText("Welcome " + full_name);

         */
    }
}
package com.example.taskmanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmanager.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtUsername, txtPassword, txtReenterPassword, txtFirstName, txtLastName, txtEmail, txtPhone;
    private Spinner spinnerGender;
    private Button btnRegister;
    //private TextView ;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reg), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtReenterPassword = findViewById(R.id.txtReenterPassword);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtNumber);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> registerUser());
        initSpinnerGender();

    }
    private void registerUser(){
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String first_name = txtFirstName.getText().toString();
        String last_name = txtLastName.getText().toString();
        String phone = txtPhone.getText().toString();
        String gender = spinnerGender.getSelectedItem().toString();

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirst_name(first_name);
        newUser.setLast_name(last_name);
        newUser.setPhone(phone);
        newUser.setGender(gender);

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SUCCES", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                assert user != null;
                                newUser.setUid(user.getUid());
                                createUserData(newUser);
                                Toast.makeText(getApplicationContext(), "Authentication succesful.",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Please provide valid info",Toast.LENGTH_SHORT).show();
        }
    }
    private void initSpinnerGender(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.arrayGenders,
                android.R.layout.simple_spinner_item);
        spinnerGender.setAdapter(adapter);
    }

    private void createUserData(User newUser){
        DatabaseReference refUser = db.getReference("Users/" + newUser.getUid());
        refUser.setValue(newUser);
    }
}
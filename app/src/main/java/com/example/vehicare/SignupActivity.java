package com.example.vehicare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    ImageButton ImageButton;
    EditText editText_signupPhoneEmail, editText_signupPassword;
    Button button_signupComplete;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DriverInfo driverInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageButton = findViewById(R.id.img_back_Signup);
        editText_signupPhoneEmail = findViewById(R.id.editText_signupPhoneEmail);
        editText_signupPassword = findViewById(R.id.editText_signupPassword);
        button_signupComplete = findViewById(R.id.button_signupComplete);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("DriverInfo");

        driverInfo = new DriverInfo();

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, StartupActivity.class);
                startActivity(intent);
            }
        });

        button_signupComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneEmail = editText_signupPhoneEmail.getText().toString().trim();
                String password = editText_signupPassword.getText().toString().trim();

                if (TextUtils.isEmpty(phoneEmail) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(SignupActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(phoneEmail, password);
                    addDataToFirebase(phoneEmail, password);
                }
            }
        });
    }

    private void registerUser(String phoneEmail, String password) {
        auth.createUserWithEmailAndPassword(phoneEmail, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SignupActivity.this, VehicleActivity.class));
                    Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addDataToFirebase(String phoneEmail, String password) {
        driverInfo.setDriverUsername(phoneEmail);
        driverInfo.setDriverPassword(password);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(driverInfo);

                Toast.makeText(SignupActivity.this, "Data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignupActivity.this, "Failed to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
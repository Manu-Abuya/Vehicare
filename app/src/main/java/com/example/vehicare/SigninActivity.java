package com.example.vehicare;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {

    ImageButton ImageButton;
    TextView textView_pasForgot, textView_QForgotPas, textView_SignUp;
    Button button_SignIn;
    EditText editText_phone_emailAddress, editText_signin_Password;
    FirebaseAuth auth;
    public ProgressDialog loginprogress;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ImageButton = findViewById(R.id.img_back_Signin);
        textView_pasForgot = findViewById(R.id.textView_pasForgot);
        textView_QForgotPas = findViewById(R.id.textView_QForgotPas);
        textView_SignUp = findViewById(R.id.textView_SignUp);
        editText_phone_emailAddress = findViewById(R.id.editText_phone_emailAddress);
        editText_signin_Password = findViewById(R.id.editText_signin_Password);
        button_SignIn = findViewById(R.id.button_SignIn);
        loginprogress=new ProgressDialog(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, StartupActivity.class);
                startActivity(intent);
            }
        });

        textView_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


        button_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneEmail = editText_phone_emailAddress.getText().toString().trim();
                String password = editText_signin_Password.getText().toString().trim();
                loginUser(phoneEmail, password);
            }
        });

        textView_pasForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailet= new EditText(this);

        // type the email you used to register
        emailet.setText("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        // Click on Recover and an email will be sent to your registered email id
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailet.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail
        // open your email and write the new password and then you can login
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful())
                {
                    // if isSuccessful, message will be shown and you can change the password
                    Toast.makeText(SigninActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SigninActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(SigninActivity.this, "Error Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String phoneEmail, String password) {
        auth.signInWithEmailAndPassword(phoneEmail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SigninActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SigninActivity.this, VehicleRegActivity.class));
                finish();
            }
        });
    }
}
package com.example.vehicare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    ImageButton ImageButton;
    private EditText editText_signupName;
    private EditText editText_signupPhoneEmail;
    private EditText editTextDOB;
    private EditText editText_signupPassword;
    private EditText editText_confPassword;
    private Button button_signupComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageButton = (ImageButton) findViewById(R.id.img_back_Signup);
        editText_signupName = findViewById(R.id.editText_signupName);
        editText_signupPhoneEmail = findViewById(R.id.editText_signupPhoneEmail);
        editTextDOB = findViewById(R.id.editTextDOB);
        editText_signupPassword = findViewById(R.id.editText_signupPassword);
        editText_confPassword = findViewById(R.id.editText_confPassword);
        button_signupComplete = findViewById(R.id.button_signupComplete);

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
                String txt_Name = editText_signupName.getText().toString();
                String txt_PhoneEmail = editText_signupPhoneEmail.getText().toString();
                String txt_DOB = editTextDOB.getText().toString();
                String txt_Password = editText_signupPassword.getText().toString();
                String txt_confPassword = editText_confPassword.getText().toString();

                if (TextUtils.isEmpty(txt_Name || TextUtils.isEmpty(txt_PhoneEmail || TextUtils.isEmpty(txt_DOB || TextUtils.isEmpty(txt_Password || TextUtils.isEmpty(txt_Password || TextUtils.isEmpty(txt_confPassword))))))){
                    Toast.makeText(SignupActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                } else if (txt_Password.length() < 6){
                    Toast.makeText(SignupActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else if (txt_Password != txt_confPassword){
                    Toast.makeText(SignupActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_Name, txt_PhoneEmail, txt_DOB, txt_Password, txt_confPassword);
                }
            }
        });
    }

    private void registerUser(String txt_Name, String txt_PhoneEmail, String txt_DOB, String txt_Password, String txt_confPassword){

    }
}
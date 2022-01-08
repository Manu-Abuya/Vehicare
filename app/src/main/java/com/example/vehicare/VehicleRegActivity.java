package com.example.vehicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class VehicleRegActivity extends AppCompatActivity {

    ImageButton ImageButton;
    GoogleSignInClient mGoogleSignInClient;
    EditText editText_vehLicenseNo, editText_vehInsuNo, editText_vehIDNo;
    Button button_vehRegComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_reg);

        ImageButton = (android.widget.ImageButton) findViewById(R.id.img_back_vehReg);
        editText_vehLicenseNo = findViewById(R.id.editText_vehLicenseNo);
        editText_vehInsuNo = findViewById(R.id.editText_vehInsuNo);
        editText_vehIDNo = findViewById(R.id.editText_vehIDNo);
        button_vehRegComplete = findViewById(R.id.button_vehRegComplete);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.img_back_vehReg:
                        signOut();
                        break;
                    // ...
                }
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(VehicleRegActivity.this, "User Signed Out Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), StartupActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
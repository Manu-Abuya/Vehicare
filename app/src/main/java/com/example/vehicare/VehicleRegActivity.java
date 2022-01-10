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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class VehicleRegActivity extends AppCompatActivity {

    ImageButton ImageButton;
    GoogleSignInClient mGoogleSignInClient;
    EditText editText_your_car, editText_vehLicenseNo, editText_vehInsuNo, editText_vehIDNo;
    Button button_vehRegComplete;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_reg);

        db = FirebaseFirestore.getInstance();

        ImageButton = (android.widget.ImageButton) findViewById(R.id.img_back_vehReg);
        editText_your_car = findViewById(R.id.editText_your_car);
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

        button_vehRegComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String car = editText_your_car.getText().toString().trim();
                String licence = editText_vehLicenseNo.getText().toString().trim();
                String insurance = editText_vehInsuNo.getText().toString().trim();
                String identification = editText_vehIDNo.getText().toString().trim();

                if (!validateInputs(car, licence, insurance, identification)) {
                    CollectionReference dbVehicleDetails = db.collection("VehicleDetails");

                    VehicleDetails vehicleDetails = new VehicleDetails(
                            car,
                            licence,
                            insurance,
                            identification
                    );

                    dbVehicleDetails.add(vehicleDetails)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(VehicleRegActivity.this, "Vehicle Details added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VehicleRegActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    private boolean validateInputs(String car, String license, String insurance, String identification){
        if (car.isEmpty()){
            editText_your_car.setError("Field is required");
            editText_your_car.requestFocus();
        }

        if (license.isEmpty()){
            editText_vehLicenseNo.setError("Field is required");
            editText_vehLicenseNo.requestFocus();
        }

        if (insurance.isEmpty()){
            editText_vehInsuNo.setError("Field is required");
            editText_vehInsuNo.requestFocus();
        }

        if (identification.isEmpty()){
            editText_vehIDNo.setError("Field is required");
            editText_vehIDNo.requestFocus();
        }
        return false;
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
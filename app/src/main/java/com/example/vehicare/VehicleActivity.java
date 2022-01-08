package com.example.vehicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class VehicleActivity extends AppCompatActivity {

    TextView textView_model, textView_detail1, textView_detail2;
    EditText editText_your_car;
    Button vehicleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        textView_model = findViewById(R.id.textView_model);
        textView_detail1 = findViewById(R.id.textView_detail1);
        textView_detail2 = findViewById(R.id.textView_detail2);
        editText_your_car = findViewById(R.id.editText_your_car);
        vehicleButton = findViewById(R.id.vehicleButton);

        
        vehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleActivity.this, VehicleRegActivity.class);
                startActivity(intent);
            }
        });

        editText_your_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(VehicleActivity.this);
                String url = "https://api.auto-data.net/media/schema.json";

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(VehicleActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VehicleActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
            }
        });
    }
}
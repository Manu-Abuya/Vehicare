package com.example.vehicare;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class view_database_layout extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";

    //add Firebase Database
    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;
    String userID;

    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_database_layout);

        

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            FuelInformation fInfo = new FuelInformation();
            fInfo.setID1(ds.child(userID).getValue(FuelInformation.class).getID1);
            fInfo.setID2(ds.child(userID).getValue(FuelInformation.class).getID2);
            fInfo.setID3(ds.child(userID).getValue(FuelInformation.class).getID3);

            //display data
            Log.d(TAG, "showData: ID1: " + fInfo.getID1());
            Log.d(TAG, "showData: ID2: " + fInfo.getID2());
            Log.d(TAG, "showData: ID3: " + fInfo.getID3());

            ArrayList<String> array  = new ArrayList<>();
            array.add(fInfo.getID1());
            array.add(fInfo.getID2());
            array.add(fInfo.getID3());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            mListView.setAdapter(adapter);

        }
    }
}
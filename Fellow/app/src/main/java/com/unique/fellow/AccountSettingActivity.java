package com.unique.fellow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSettingActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView profileURL;
    EditText nameEditText;
    EditText emailEditText;
    EditText dobEditText;
    RadioGroup accountTypeRadioGroup;
    RadioButton accountTypeRadioButton;
    Button doneAccountSettingButton;
    Button developerRadioButton;
    Button advisorRadioButton;
    Button investorRadioButton;
    private String userType;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        profileURL = findViewById(R.id.profileURL);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        dobEditText = findViewById(R.id.dobEditText);

        //fetch database value into account setting page
        mFirebaseDatabaseReference.child("User Collection").orderByChild("userID").equalTo(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        nameEditText.setText(user.child("name").getValue().toString());
                        emailEditText.setText(user.child("email").getValue().toString());
                        dobEditText.setText(user.child("dob").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        //begin change or edit value in account setting page
        accountTypeRadioGroup = findViewById(R.id.accountTypeRadioGroup);
        doneAccountSettingButton = findViewById(R.id.doneAccountSettingButton);

        nameEditText.setText(mFirebaseUser.getDisplayName());
        emailEditText.setText(mFirebaseUser.getEmail());

        Glide.with(this).load(mFirebaseUser.getPhotoUrl().toString()).into(profileURL);


        //if this user already choose user type so disable radio button
        accountTypeRadioButton = findViewById(accountTypeRadioGroup.getCheckedRadioButtonId());
        developerRadioButton = findViewById(R.id.developerRadioButton);
        advisorRadioButton = findViewById(R.id.advisorRadioButton);
        investorRadioButton = findViewById(R.id.investorRadioButton);
        mFirebaseDatabaseReference.child("User Collection").orderByChild("userID").equalTo(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        userType = user.child("userType").getValue().toString();
                    }
                    if (userType.equals("Developer")) {
                        advisorRadioButton.setEnabled(false);
                        investorRadioButton.setEnabled(false);
                        Log.d("Test", "Developer");
                    } else if (userType.equals("Advisor")) {
                        developerRadioButton.setEnabled(false);
                        investorRadioButton.setEnabled(false);
                        Log.d("Test", "Advisor");
                    } else if (userType.equals("Investor")) {
                        developerRadioButton.setEnabled(false);
                        advisorRadioButton.setEnabled(false);
                        Log.d("Test", "Investor");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        doneAccountSettingButton.setOnClickListener(this);
        Log.d("Test", "On create");

        //startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public void onClick(View view) {

        accountTypeRadioButton = findViewById(accountTypeRadioGroup.getCheckedRadioButtonId());

        final UserCollections userCollections = new
                UserCollections(dobEditText.getText().toString(), emailEditText.getText().toString(), nameEditText.getText().toString(), profileURL.toString(), accountTypeRadioButton.getText().toString());
        userCollections.setUserID(mFirebaseUser.getUid());
        userCollections.setProfileURL(mFirebaseUser.getPhotoUrl().toString());
        mFirebaseDatabaseReference.child("User Collection").orderByChild("userID").equalTo(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        key = childSnapshot.getKey();
                    }
                    Log.d("test", key);
                    mFirebaseDatabaseReference.child("User Collection/" + key).setValue(userCollections);
                } else {
                    mFirebaseDatabaseReference.child("User Collection").push().setValue(userCollections);
                }
                Log.d("Test", "No data on click");
                Toast.makeText(AccountSettingActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AccountSettingActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                Log.d("test", "After Intent");
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

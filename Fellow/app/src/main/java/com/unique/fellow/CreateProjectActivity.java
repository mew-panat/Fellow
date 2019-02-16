package com.unique.fellow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateProjectActivity extends AppCompatActivity {

    ProjectCollections projectCollections;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    UserCollections userList;

    ArrayList<String> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        getSupportFragmentManager().beginTransaction().add(R.id.createProjectFragment, ProjectInfoFragment.newInstance(), "projectInfo").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("projectName") != null) {
            // Initialize Firebase Auth
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

            memberList = getIntent().getStringArrayListExtra("members");

            for (int i = 0; i < memberList.size(); i++) {

                final int finalI = i;
                mFirebaseDatabaseReference.child("User Collection").orderByChild("email").equalTo(memberList.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                userList = user.getValue(UserCollections.class);
                            }
                            memberList.set(finalI, userList.getUserID());
                            Log.d("email",String.valueOf(finalI));
                            projectCollections = new ProjectCollections(getIntent().getStringExtra("projectName"), getIntent().getStringExtra("description"), getIntent().getStringExtra("category"), getIntent().getStringExtra("projectTeamName"),memberList);
                            projectCollections.setProjectID("projectID");
                            mFirebaseDatabaseReference.child("Project Collection").setValue(projectCollections);
                            mFirebaseDatabaseReference.child("Project Collection").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        ProjectCollections project = postSnapshot.getValue(ProjectCollections.class);
                                        for (int i = 0; i < project.getMembers().size(); i++) {
                                            Log.d("test", project.getMembers().get(i));
                                            if (project.getMembers().get(i).equals(mFirebaseUser.getUid())) {
                                                String key = postSnapshot.getKey();
                                                Log.d("key", key);
                                                mFirebaseDatabaseReference.child("Project Collection/" + key + "/projectID").setValue(key);
                                            }
                                        }
                                    }
                                    Toast.makeText(CreateProjectActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(CreateProjectActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("The read failed: " + databaseError.getCode());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            Log.d("email","OnCreate");

            projectCollections = new ProjectCollections(getIntent().getStringExtra("projectName"), getIntent().getStringExtra("description"), getIntent().getStringExtra("category"), getIntent().getStringExtra("projectTeamName"),memberList);
            projectCollections.setProjectID("projectID");
            mFirebaseDatabaseReference.child("Project Collection").push().setValue(projectCollections);
        }
    }
}

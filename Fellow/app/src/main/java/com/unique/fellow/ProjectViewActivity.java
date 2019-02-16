package com.unique.fellow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProjectViewActivity extends AppCompatActivity {

    private RecyclerView developerRecyclerView;
    private RecyclerView.Adapter memberAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Button chatButton;

    TextView projectName;
    TextView projectCategory;
    TextView projectDescription;
    TextView projectTeamname;

    ArrayList<String> members;
    ProjectCollections projectList;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        final String projectID = getIntent().getStringExtra("projectID");

        projectName = findViewById(R.id.projectNameView);
        projectCategory = findViewById(R.id.projectCategoryView);
        projectDescription = findViewById(R.id.projectDescriptionView);
        projectTeamname = findViewById(R.id.projectTeamNameView);
        developerRecyclerView = findViewById(R.id.developerRecyclerView);

        chatButton = findViewById(R.id.chatButton);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabaseReference.child("Project Collection").orderByChild("projectID").equalTo(projectID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot project : dataSnapshot.getChildren()) {
                        projectList = project.getValue(ProjectCollections.class);
                    }
                    projectName.setText(projectList.getProjectName());
                    projectCategory.setText(projectList.getCategory());
                    projectDescription.setText(projectList.getProjectDescription());
                    projectTeamname.setText(projectList.getTeamName());
                    members = projectList.getMembers();

                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(ProjectViewActivity.this);
                    developerRecyclerView.setLayoutManager(mLayoutManager);

                    memberAdapter = new MemberRecyclerViewAdapter(members);
                    developerRecyclerView.setAdapter(memberAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProjectViewActivity.this, ChatActivity.class);
                i.putExtra("projectID", projectID);
                startActivity(i);
            }
        });
    }
}

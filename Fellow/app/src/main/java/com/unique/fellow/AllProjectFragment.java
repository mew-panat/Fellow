package com.unique.fellow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AllProjectFragment extends Fragment {

    private ListView listView;
    private ProjAdapter pAdapter;
    ArrayList<ProjectCollections> projectList = new ArrayList<>();

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    // TODO: Rename and change types and number of parameters
    public static AllProjectFragment newInstance() {
        AllProjectFragment fragment = new AllProjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_allproject, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        listView = rootView.findViewById(R.id.listviewAllProject);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        readData(new MyProjectFragment.MyCallBack() {
            @Override
            public void onCallBack(ArrayList<ProjectCollections> proj) {
                System.out.println("helloworld");
                pAdapter = new ProjAdapter(getActivity(), proj);
                listView.setAdapter(pAdapter);
            }
        });
    }

    public void readData(final MyProjectFragment.MyCallBack myCallBack) {
        mFirebaseDatabaseReference.child("Project Collection").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projectList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProjectCollections project = postSnapshot.getValue(ProjectCollections.class);
                    projectList.add(project);
                }
                myCallBack.onCallBack(projectList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String projectID = projectList.get(position).getProjectID();
                Log.d("test", projectID);
                Intent i = new Intent(getActivity(),ProjectViewActivity.class);
                i.putExtra("projectID",projectID);
                startActivity(i);
            }
        });
    }

    public interface MyCallBack {
        void onCallBack(ArrayList<ProjectCollections> proj);
    }
}

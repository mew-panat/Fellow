package com.unique.fellow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class MyProjectFragment extends Fragment {

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    private ListView listView;
    private ProjAdapter pAdapter;
    ArrayList<ProjectCollections> projectList = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static MyProjectFragment newInstance() {
        MyProjectFragment fragment = new MyProjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_myproject, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        listView = rootView.findViewById(R.id.listview);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        readData(new MyCallBack() {
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
                    for (int i = 0; i < project.getMembers().size(); i++) {
                        Log.d("test",project.getMembers().get(i));
                        if (project.getMembers().get(i).equals(mFirebaseUser.getUid())) {
                            projectList.add(project);
                        }
                    }
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
                Intent i = new Intent(getActivity(), ProjectViewActivity.class);
                i.putExtra("projectID", projectID);
                startActivity(i);
            }
        });
    }

    public interface MyCallBack {
        void onCallBack(ArrayList<ProjectCollections> proj);
    }
}

package com.unique.fellow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddMemberFragment extends Fragment implements View.OnClickListener {

    EditText inviteEmailProject;
    Button addEmailProjectButton;
    Button addMemberNextButton;

    private ListView memberEmailListView;
    private AddMemberAdapter addMemberAdapter;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    private String userID;
    private ArrayList<String> members = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static AddMemberFragment newInstance() {
        AddMemberFragment fragment = new AddMemberFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_add_member, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        addMemberNextButton = rootView.findViewById(R.id.addMemberNextButton);
        inviteEmailProject = rootView.findViewById(R.id.inviteEmailProject);
        addEmailProjectButton = rootView.findViewById(R.id.addEmailProject);
        memberEmailListView = rootView.findViewById(R.id.addMemberListView);
        addMemberNextButton.setOnClickListener(this);
        addEmailProjectButton.setOnClickListener(this);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        addMemberAdapter = new AddMemberAdapter(getActivity(), members);
        memberEmailListView.setAdapter(addMemberAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addMemberNextButton:
                mFirebaseDatabaseReference.child("User Collection").orderByChild("email").equalTo(inviteEmailProject.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (members.isEmpty()) {
                            Toast.makeText(getActivity(),"Please add user",Toast.LENGTH_SHORT).show();
                            inviteEmailProject.setText("");
                        }
                        else {
                            members.add(mFirebaseUser.getEmail());
                            ScheduleFragment scheduleFragment = new ScheduleFragment();
                            Bundle value = new Bundle();
                            value.putString("projectName",getArguments().getString("projectName"));
                            value.putString("projectTeamName",getArguments().getString("projectTeamName"));
                            value.putString("category",getArguments().getString("category"));
                            value.putString("description",getArguments().getString("description"));
                            value.putStringArrayList("members",members);
                            scheduleFragment.setArguments(value);
                            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fui_slide_in_right,R.anim.fui_slide_out_left).replace(R.id.createProjectFragment,scheduleFragment).commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
                break;
            case R.id.addEmailProject:
                mFirebaseDatabaseReference.child("User Collection").orderByChild("email").equalTo(inviteEmailProject.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(getActivity(),"User not found",Toast.LENGTH_SHORT).show();
                            inviteEmailProject.setText("");
                        }
                        else if(inviteEmailProject.getText().toString().equals(mFirebaseUser.getEmail())){
                            Toast.makeText(getActivity(),"Cannot add yourself",Toast.LENGTH_SHORT).show();
                            inviteEmailProject.setText("");
                        }
                        else {
                            members.add(inviteEmailProject.getText().toString());
                            Toast.makeText(getActivity(),"User added",Toast.LENGTH_SHORT).show();
                            inviteEmailProject.setText("");
                            addMemberAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
                break;
        }
    }
}

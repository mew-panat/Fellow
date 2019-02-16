package com.unique.fellow;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ProjectInfoFragment extends Fragment implements View.OnClickListener {

    Button projectInfoNextButton;
    EditText projectNameEditText;
    EditText projectTeamNameEditText;
    EditText categoryEditText;
    EditText descriptionEditText;

    // TODO: Rename and change types and number of parameters
    public static ProjectInfoFragment newInstance() {
        ProjectInfoFragment fragment = new ProjectInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_project_info, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        projectNameEditText = rootView.findViewById(R.id.projectNameEditText);
        projectTeamNameEditText = rootView.findViewById(R.id.projectTeamNameEditText);
        categoryEditText = rootView.findViewById(R.id.categoryEditText);
        descriptionEditText = rootView.findViewById(R.id.descriptionEditText);

        projectInfoNextButton = rootView.findViewById(R.id.projectInfoNextButton);
        projectInfoNextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.projectInfoNextButton:
                AddMemberFragment addMemberFragment = new AddMemberFragment();
                Bundle value = new Bundle();
                value.putString("projectName",projectNameEditText.getText().toString());
                value.putString("projectTeamName",projectTeamNameEditText.getText().toString());
                value.putString("category",categoryEditText.getText().toString());
                value.putString("description",descriptionEditText.getText().toString());
                addMemberFragment.setArguments(value);
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fui_slide_in_right,R.anim.fui_slide_out_left).replace(R.id.createProjectFragment,addMemberFragment).commit();
        }
    }
}

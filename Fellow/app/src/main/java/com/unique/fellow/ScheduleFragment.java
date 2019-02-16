package com.unique.fellow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ScheduleFragment extends Fragment implements View.OnClickListener {

    EditText projectTask;
    EditText projectTaskStart;
    EditText projectTaskEnd;
    EditText projectSubTask;
    EditText projectSubTaskContributor;
    EditText projectSubTaskStart;
    EditText projectSubTaskEnd;
    Button scheduleConfirmButton;

    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        scheduleConfirmButton = rootView.findViewById(R.id.scheduleConfirmButton);
        projectTask = rootView.findViewById(R.id.projectTask);
        projectTaskStart = rootView.findViewById(R.id.projectTaskStart);
        projectTaskEnd = rootView.findViewById(R.id.projectTaskEnd);
        projectSubTask = rootView.findViewById(R.id.projectSubtask);
        projectSubTaskStart = rootView.findViewById(R.id.projectSubtaskStart);
        projectSubTaskEnd = rootView.findViewById(R.id.projectSubtaskEnd);
        projectSubTaskContributor = rootView.findViewById(R.id.projectSubtaskContributor);

        scheduleConfirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scheduleConfirmButton:
                Intent intent = new Intent(getActivity(),CreateProjectActivity.class);
                intent.putExtra("projectName",getArguments().getString("projectName"));
                intent.putExtra("projectTeamName",getArguments().getString("projectTeamName"));
                intent.putExtra("category",getArguments().getString("category"));
                intent.putExtra("description",getArguments().getString("description"));
                intent.putExtra("members",getArguments().getStringArrayList("members"));
                intent.putExtra("projectTask",projectTask.getText().toString());
                intent.putExtra("projectTaskStart",projectTaskStart.getText().toString());
                intent.putExtra("projectTaskEnd",projectSubTaskEnd.getText().toString());
                intent.putExtra("projectSubtask",projectSubTask.getText().toString());
                intent.putExtra("projectSubtaskStart",projectSubTaskStart.getText().toString());
                intent.putExtra("projectSubtaskEnd",projectSubTaskEnd.getText().toString());
                intent.putExtra("projectSubtaskContributor",projectSubTaskContributor.getText().toString());
                getActivity().startActivity(intent);
        }
    }
}

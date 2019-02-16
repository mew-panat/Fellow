package com.unique.fellow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ProjAdapter extends ArrayAdapter<ProjectCollections> {

    TextView name;
    TextView description;
    ImageView photo;

    private Context mContext;
    private List<ProjectCollections> projectsList;

    public ProjAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ProjectCollections> list){
        super(context,0,list);
        mContext = context;
        projectsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_dashboard,parent,false);

        //findViewById
        name = listItem.findViewById(R.id.projectName);
        description = listItem.findViewById(R.id.projectDescription);
        photo = listItem.findViewById(R.id.projectPhoto);

        ProjectCollections currentproj = projectsList.get(position);

        //Set View
        name.setText(currentproj.getProjectName());
        description.setText(currentproj.getProjectDescription());
        photo.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_account_circle_black_36dp));

        return listItem;
    }
}

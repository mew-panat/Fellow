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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddMemberAdapter extends ArrayAdapter<String> {

    TextView email;

    Button deleteEmailButton;

    private Context mContext;
    private List<String> emailList;

    public AddMemberAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<String> list){
        super(context,0,list);
        mContext = context;
        emailList = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_addmember,parent,false);

        //findViewById
        email = listItem.findViewById(R.id.memberEmail);
        deleteEmailButton = listItem.findViewById(R.id.deleteEmail);

        String currentEmail = emailList.get(position);

        //Set View
        email.setText(currentEmail);

        deleteEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailList.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItem;
    }
}

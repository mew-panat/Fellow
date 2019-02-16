package com.unique.fellow;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberRecyclerViewAdapter.memberHolder> {

    public static class memberHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageView photo;
        ImageView favorite;

        public memberHolder(View v) {
            super(v);
            name = v.findViewById(R.id.projectName);
            description = v.findViewById(R.id.projectDescription);
            photo = v.findViewById(R.id.projectPhoto);
            favorite = v.findViewById(R.id.favoriteProject);
        }
    }

    private List<String> mData;

    private UserCollections userList;

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    MemberRecyclerViewAdapter(List<String> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public memberHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        return new memberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final memberHolder viewHolder, int i) {
        String currentMember = mData.get(i);
        mFirebaseDatabaseReference.child("User Collection").orderByChild("userID").equalTo(currentMember).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    userList = user.getValue(UserCollections.class);
                }
                Log.d("name", userList.getname());
                viewHolder.name.setText(userList.getname());
                viewHolder.description.setText(userList.getEmail());
                Glide.with(viewHolder.photo.getContext()).load(userList.getProfileURL()).into(viewHolder.photo);
                viewHolder.favorite.setVisibility(ImageView.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        else
            return mData.size();
    }

}

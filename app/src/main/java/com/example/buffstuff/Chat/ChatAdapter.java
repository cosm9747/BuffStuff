package com.example.buffstuff.Chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buffstuff.R;

import java.util.List;

//Adapter to create card for items and add them to recyclerView in buy page
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    List<User> UserList;
    Context context;
    String TAG = "Testing";

    public ChatAdapter(List<User>ItemList)
    {
        this.UserList = ItemList;
    }

    @Override
    //When new view is made, make a new view holder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    //When this adapter is bound to a view, set the holder title and price
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User thing = UserList.get(position);
        Log.d(TAG, "Doing something");
        holder.name.setText(thing.getName());

        // Display chat messages when card is clicked
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayChat.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        CardView cv;

        //Create new itemView for each item in passed item list
        public ViewHolder(View userView)
        {
            super(userView);
            name = (TextView)userView.findViewById(R.id.text_view_name);
            cv = (CardView)userView.findViewById(R.id.cv);
        }

    }
}
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

    public ChatAdapter(List<User>UserList)
    {
        this.UserList = UserList;
    }

    @Override
    //When new view is made, make a new view holder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    //When this adapter is bound to a view, set the holder title and price
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User thing = UserList.get(position);

        holder.name.setText(thing.getName());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), DisplayUserActivity.class);
                intent.putExtra("ID", thing.getName());
                Log.d("working", "There");
                view.getContext().startActivity(intent);
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
        public ViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.user_view_name);
            cv = (CardView)itemView.findViewById(R.id.usercv);
        }

    }
}
package com.example.buffstuff.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.buffstuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>
{
    // This adapter is for the actual messages screen, used to display all of the messages in RV

    List<Messages> userMessagesList;
    Context context;

    public MessagesAdapter(List<Messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder
    {

        public TextView senderMessageText, receiverMessageText, senderTime, receiverTime;
        public MessageViewHolder(View itemView) {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_text);
            senderTime = itemView.findViewById(R.id.sender_time);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_text);
            receiverTime = itemView.findViewById(R.id.receiver_time);
        }
    }

    @Override
    //When new view is made, make a new view holder
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    //When this adapter is bound to a view, set the message information
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        final Messages thing = userMessagesList.get(position);

        SimpleDateFormat simpleDate =  new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        String time = simpleDate.format(thing.getSentAt()) + "\n";

        if(thing.getIsSenderSelf()) {
            holder.receiverMessageText.setVisibility(View.INVISIBLE);
            holder.receiverTime.setVisibility(View.INVISIBLE);

            holder.senderMessageText.setText(thing.getText());
            holder.senderTime.setText(time);

        } else {
            holder.senderMessageText.setVisibility(View.INVISIBLE);
            holder.senderTime.setVisibility(View.INVISIBLE);

            holder.receiverMessageText.setText(thing.getText());
            holder.receiverTime.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }
    

}

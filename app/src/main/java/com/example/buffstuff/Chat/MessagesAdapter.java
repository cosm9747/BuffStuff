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
//    private List<Messages> userMessagesList;
//    private FirebaseAuth mAuth;
//    private Task<QuerySnapshot> userRef;
//
//    public MessagesAdapter(List<Messages> userMessagesList)
//    {
//        this.userMessagesList = userMessagesList;
//    }
//
//
//    public class MessageViewHolder extends RecyclerView.ViewHolder
//    {
//
//        public TextView senderMessageText, receiverMessageText;
//
//
//        public MessageViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            senderMessageText = (TextView) itemView.findViewById(R.id.their_message_body);
//            receiverMessageText = (TextView) itemView.findViewById(R.id.message_body);
//        }
//    }
//
//    @NonNull
//    @Override
//    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//    {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_chat, parent, false);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        return new MessageViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position)
//    {
//        final String messageSenderId = mAuth.getCurrentUser().getUid();
//        final Messages messages = userMessagesList.get(position);
//
//        final String fromUserId = messages.getSender();
//
//        userRef = FirebaseFirestore.getInstance()
//                .collection("chats")
//                .document()
//                .collection("messages")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//
//
//
//                                    holder.receiverMessageText.setVisibility(View.INVISIBLE);
//
//                                    if (fromUserId.equals(messageSenderId)) { // If you are the sender
//
//                                        holder.senderMessageText.setBackgroundResource(R.drawable.my_message);
//                                        holder.senderMessageText.setTextColor(Color.BLACK);
//                                        holder.senderMessageText.setText(messages.getText());
//                                    }
//
//                                    else { // You are the receiver
//
//                                        holder.senderMessageText.setVisibility(View.INVISIBLE);
//                                        holder.receiverMessageText.setVisibility(View.VISIBLE);
//
//
//                                        holder.receiverMessageText.setBackgroundResource(R.drawable.their_message);
//                                        holder.receiverMessageText.setTextColor(Color.BLACK);
//                                        holder.receiverMessageText.setText(messages.getText());
//
//                                    }
//                                }
//
//
//
//                        }
//                    }
//                });
//
//    }
//    @Override
//    public int getItemCount()
//    {
//        return userMessagesList.size();
//    }



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
    //When this adapter is bound to a view, set the holder title and price
    public void onBindViewHolder(MessageViewHolder holder, final int position) {
        final Messages thing = userMessagesList.get(position);

        SimpleDateFormat simpleDate =  new SimpleDateFormat("MM/dd/yyyy");
        String time = simpleDate.format(thing.getSentAt());

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

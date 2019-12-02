package com.example.buffstuff.Chat;

import android.os.Message;
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

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>
{
    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore userRef;



    public MessagesAdapter(List<Messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder
    {

        public TextView senderMessageText, receiverMessageText;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.their_message_body);
            receiverMessageText = (TextView) itemView.findViewById(R.id.message_body);
        }
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_chat, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position)
    {
        String messageSenderId = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(position);

        String fromUserId = messages.getSender();


    }




    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }





}

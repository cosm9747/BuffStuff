package com.example.buffstuff.Chat;

import android.graphics.Color;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>
{
    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private Task<QuerySnapshot> userRef;



    public MessagesAdapter(List<Messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder
    {

        public TextView senderMessageText, receiverMessageText;

        // Used to display chat messages
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
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position)
    {
        final String messageSenderId = mAuth.getCurrentUser().getUid();
        final Messages messages = userMessagesList.get(position);

        final String fromUserId = messages.getSender();
        final String fromMessageType = messages.getType();

        userRef = FirebaseFirestore.getInstance()
                .collection("chats")
                .document()
                .collection("messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if (fromMessageType.equals("text")) {

                                    holder.receiverMessageText.setVisibility(View.INVISIBLE);

                                    if (fromUserId.equals(messageSenderId)) { // If you are the sender

                                        holder.senderMessageText.setBackgroundResource(R.drawable.my_message);
                                        holder.senderMessageText.setTextColor(Color.BLACK);
                                        holder.senderMessageText.setText(messages.getText());
                                    }

                                    else {

                                        holder.senderMessageText.setVisibility(View.INVISIBLE);
                                        holder.receiverMessageText.setVisibility(View.VISIBLE);


                                        holder.receiverMessageText.setBackgroundResource(R.drawable.their_message);
                                        holder.receiverMessageText.setTextColor(Color.BLACK);
                                        holder.receiverMessageText.setText(messages.getText());

                                    }
                                }


                            }

                        }
                    }
                });

    }




    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }





}

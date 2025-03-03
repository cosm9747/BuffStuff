package com.example.buffstuff.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buffstuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayChat extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String id;


    // Used to display messages
    List<Messages> messagesList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    MessagesAdapter messagesAdapter;
    RecyclerView userMessagesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When this function is called
        super.onCreate(savedInstanceState);
        Intent loadIntent = getIntent();
        //Find out what the item's id is
        id = loadIntent.getStringExtra("ID");

//        setContentView(R.layout.activity_chat);


//        InitializeControllers();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        //chat test
        //Hold this context
        final RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //Get all chat messages from Firebase
        db.collection("chats").document(id).collection("messages")
                .orderBy("sentAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //If succesfully accessed firebase
                        if (task.isSuccessful()) {
                            //For every chat in the database
                            //Add messages
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Messages message = new Messages();
                                message.setSender(document.getString("sender"));
                                message.setSentAt(document.getDate("sentAt"));
                                message.setText(document.getString("text"));
                                if(currentUser.getUid().equals(message.getSender())){
                                    message.setIsSenderSelf(true);
                                } else {
                                    message.setIsSenderSelf(false);
                                }
                                messagesList.add(message);
                            }
                        }
                        //If failed to access firebase
                        else {
                            Log.d("Testing", "Problem");
                        }
                        //Display chat activity on screen
                        setContentView(R.layout.activity_chat);

                        //Set the adapter to add all the chats to the recycler view

                        userMessagesList = (RecyclerView) findViewById(R.id.messages_view);
                        userMessagesList.setLayoutManager(linearLayoutManager);
                        messagesAdapter = new MessagesAdapter(messagesList);
                        userMessagesList.setAdapter(messagesAdapter);

                    }
                });
        ((LinearLayoutManager) linearLayoutManager).setReverseLayout(true);
    }


    // Method used to send messages from the app to the Firebase database.
    public void sendMessage(View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        EditText m = findViewById(R.id.editText);
        String message = m.getText().toString();
        Date date = Calendar.getInstance().getTime();

        //writing message to db
        Map<String, Object> data = new HashMap<>();
        data.put("sender", userId);
        data.put("sentAt", date);
        data.put("text", message);

        db.collection("chats").document(id).collection("messages")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TEST", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TEST", "Error adding document", e);
                    }
                });
        m.setText("");
        Intent intent = new Intent(this, DisplayChat.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

}
package com.example.buffstuff.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.buffstuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DisplayChat extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When this function is called
        super.onCreate(savedInstanceState);
        Intent loadIntent = getIntent();
        //Find out what the item's id is
        id = loadIntent.getStringExtra("ID");
        setContentView(R.layout.activity_chat);
//        db.collection("chats")
//                .document(id)
//                .collection("messages")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        //If succesfully accessed firebase
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            //Set name
//                            TextView use = findViewById(R.id.their_message_body);
//                            use.setText(document.getString("text"));
//
//                        }
//                        //If failed to access firebase
//                        else {
//                            Log.d("Debug", "Problem");
//                        }
//                    }
//                });


    }

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
    }
}
package com.example.buffstuff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DisplayItemActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When this function is called
        super.onCreate(savedInstanceState);
        Intent loadIntent = getIntent();
        //Find out what the item's id is
        final String id = loadIntent.getStringExtra("ID");
        setContentView(R.layout.activity_display_item);
        db.collection("items")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //If succesfully accessed firebase
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            TextView use = findViewById(R.id.name);
                            use.setText(document.getString("name"));
                            use = findViewById(R.id.price);
                            use.setText("Price: " + document.getDouble("price").toString());

                        }
                        //If failed to access firebase
                        else {
                            Log.d("Debug", "Problem");
                        }
                    }
                });


    }
}

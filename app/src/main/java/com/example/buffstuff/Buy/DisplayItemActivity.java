package com.example.buffstuff.Buy;

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

import com.example.buffstuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.bumptech.glide.Glide;

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
                            //Set name
                            TextView use = findViewById(R.id.name);
                            use.setText(document.getString("name"));
                            //Set price
                            use = findViewById(R.id.price);
                            use.setText("Price: " + document.getDouble("price").toString());
                            //Set Condition
                            use = findViewById(R.id.condition);
                            use.setText("Condition: " + document.getString("condition"));
                            //Set Category
                            use = findViewById(R.id.category);
                            use.setText("Category: " + document.getString("category"));
                            //Set image
                            ImageView image = findViewById(R.id.image);
                            String URL = document.getString("image");
                            Glide.with(getApplicationContext()).load(URL).into(image);
                        }
                        //If failed to access firebase
                        else {
                            Log.d("Debug", "Problem");
                        }
                    }
                });


    }
}

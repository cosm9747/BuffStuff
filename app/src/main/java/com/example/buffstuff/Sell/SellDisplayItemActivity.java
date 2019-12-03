package com.example.buffstuff.Sell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SellDisplayItemActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When this function is called
        super.onCreate(savedInstanceState);
        Intent loadIntent = getIntent();
        //Find out what the item's id is
        id = loadIntent.getStringExtra("ID");
        setContentView(R.layout.activity_sell_display_item);
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
                            TextView use = findViewById(R.id.name2);
                            use.setText(document.getString("name"));
                            //Set price
                            use = findViewById(R.id.price2);
                            use.setText("Price: " + document.getDouble("price").toString());
                            //Set Condition
                            use = findViewById(R.id.condition3);
                            use.setText("Condition: " + document.getString("condition"));
                            //Set Category
                            use = findViewById(R.id.category2);
                            use.setText("Category: " + document.getString("category"));
                            //Set image
                            ImageView image = findViewById(R.id.image2);
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

    public void deleteItem(View view) {
        db.collection("items").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DELETE", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DELETE", "Error deleting document", e);
                    }
                });
        Intent intent = new Intent(this, ViewItemsActivity.class);
        startActivity(intent);
    }
}

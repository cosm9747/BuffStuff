package com.example.buffstuff.Sell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.Login.SignUpActivity;
import com.example.buffstuff.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "Testing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void goToSell(View view) {

        EditText n = (EditText) findViewById(R.id.inputName);
        EditText p = (EditText) findViewById(R.id.price);
        Spinner con = (Spinner) findViewById(R.id.condition_spinner);
        Spinner cat = (Spinner) findViewById(R.id.category_spinner);
        //If some information left unfilled, make them fill
        if(n.getText().toString().equals("") || p.getText().toString().equals("") || con.getSelectedItem().toString().equals("Select") || cat.getSelectedItem().toString().equals("Select")){
            Log.d(TAG, "goToSell: not all things entered");
            Toast.makeText(this, "Please fill out all categories",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            String inputName = n.getText().toString();
            Double price = Double.parseDouble(p.getText().toString());
            String condition = con.getSelectedItem().toString();
            String category = cat.getSelectedItem().toString();
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String userId = currentUser.getUid();

            Map<String, Object> item = new HashMap<>();

            item.put("name", inputName);
            item.put("price", price);
            item.put("condition", condition);
            item.put("category", category);
            item.put("user", userId);

            final Intent intent = new Intent(this, SellActivity.class);

            db.collection("items")
                    .add(item)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            startActivity(intent);
                            Log.d("dbSuccess", "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("dbFailure", "Error adding document", e);
                        }
                    });
        }

    }
}

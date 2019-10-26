package com.example.buffstuff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //    private DatabaseReference mDatabase;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }



    public void goToHomeScreen(View view) {

        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        String email = editText.getText().toString();
        String password = editText2.getText().toString();

        if(email.length() != 0 && password.length() != 0){
            final Intent intent = new Intent(this, BuyActivity.class);
            intent.putExtra("SEARCH_NAME", " ");
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Success", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Error", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Invalid Email or Password.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else {
            Toast.makeText(MainActivity.this, "Invalid Email or Password.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}

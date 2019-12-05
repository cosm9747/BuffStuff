package com.example.buffstuff.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; //to access Firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //setting the view associated with this activity (the UI)

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser(); //get the current user's information
        if (currentUser != null){ //if user is already logged in, then go straight to the Buy Activity
            //intents are how you transition to a different activity
            Intent intent = new Intent(this, BuyActivity.class);
            startActivity(intent);
        }
    }

    public void goToSignUp(View view) {
        //this method gets triggered if they press the new user? button
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }



    public void goToHomeScreen(View view) {

        //these are the checks that happen when user presses login

        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        String email = editText.getText().toString();
        String password = editText2.getText().toString();
        //gets the values of the text fields they entered their email and password in

        if(email.length() != 0 && password.length() != 0){
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            final Intent intent = new Intent(this, BuyActivity.class);
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
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
    }

}
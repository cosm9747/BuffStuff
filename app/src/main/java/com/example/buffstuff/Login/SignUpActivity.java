package com.example.buffstuff.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //variable that gives me access to the database

    @Override
    protected void onCreate(Bundle savedInstanceState) { //method gets triggered as soon as the activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
    }

    public void goToLogin(View view) { //if they press already a user? button
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToHomeScreen(View view) {

        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText2);
        EditText editText3 = findViewById(R.id.editText3);
        EditText editText4 = findViewById(R.id.editText4);
        final String email = editText.getText().toString();
        String password = editText2.getText().toString();
        String confirmPassword = editText3.getText().toString();
        final String fullName = editText4.getText().toString();
        //getting string values for all the fields the user entered

        //makes sure there is valid values for the text fields
        if(email.length() != 0 && password.length() != 0 && confirmPassword.length() != 0){
            //all emails have to be CU emails, and password and confirm password have to match
            if(!email.contains("@colorado.edu")){
                Toast.makeText(SignUpActivity.this, "Please enter your CU Email.",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!password.equals(confirmPassword)) {
                Toast.makeText(SignUpActivity.this, "Passwords must match.",
                        Toast.LENGTH_SHORT).show();
            }
            final Intent intent = new Intent(this, BuyActivity.class);
            //once it meets the previous criteria, it can attempt to create a new account in Firebase
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                //write user info to database
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("name", fullName);
                                userInfo.put("bio", "");

                                //this is where user info is stored in db
                                db.collection("users").document(user.getUid()).collection("profile").document(user.getUid())
                                        .set(userInfo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startActivity(intent);
                                                Log.d("tag", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("tag", "Error writing document", e);
                                            }
                                        });



                            } else {

                                //these are all the different issues that can happen when attempting to make a Firebase account
                                try {
                                    throw task.getException();
                                }
                                catch (FirebaseAuthWeakPasswordException weakPassword){
                                    Log.d("INVPASS", "onComplete: weakPassword");
                                    Toast.makeText(SignUpActivity.this, "Password must be at least 6 characters.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (FirebaseAuthUserCollisionException existEmail)
                                {
                                    Log.d("USEREXISTS", "onComplete: exist_email");

                                    Toast.makeText(SignUpActivity.this, "A user with this email already exists.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                {
                                    Log.d("INVEMAIL", "onComplete: malformed_email");

                                    Toast.makeText(SignUpActivity.this, "Must be a valid email address.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e)
                                {
                                    Log.d("ERROR", "onComplete: " + e.getMessage());
                                    Toast.makeText(SignUpActivity.this, "Unable to Sign Up.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }

                        }
                    });
        }
        else {
            //if user leaves some of the fields empty
            Toast.makeText(SignUpActivity.this, "Unable to Sign Up.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

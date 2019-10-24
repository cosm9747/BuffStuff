package com.example.buffstuff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToHomeScreen(View view) {

        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        String email = editText.getText().toString();
        String password = editText2.getText().toString();
        String confirmPassword = editText3.getText().toString();

        if(!email.contains("@colorado.edu")){
            Toast.makeText(SignUpActivity.this, "Please enter your CU Email.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Passwords must match.",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            final Intent intent = new Intent(this, BuyActivity.class);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(intent);
                            } else {

                                try {
                                    throw task.getException();
                                }
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                {
                                    Log.d("INVEMAIL", "onComplete: malformed_email");

                                    Toast.makeText(SignUpActivity.this, "Must be a valid email address.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                catch (FirebaseAuthUserCollisionException existEmail)
                                {
                                    Log.d("USEREXISTS", "onComplete: exist_email");

                                    Toast.makeText(SignUpActivity.this, "A user with this email already exists.",
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
    }
}

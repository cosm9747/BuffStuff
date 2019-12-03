package com.example.buffstuff.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.Chat.ChatActivity;
import com.example.buffstuff.Login.MainActivity;
import com.example.buffstuff.Login.SignUpActivity;
import com.example.buffstuff.R;
import com.example.buffstuff.Sell.SellActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();

        final TextView e = (TextView) findViewById(R.id.textView9); //email
        final EditText n = (EditText) findViewById(R.id.editText5); //name
        final EditText b = (EditText) findViewById(R.id.editText6); //bio

        String email = currentUser.getEmail();
        e.setText(email);

        DocumentReference docRef = db.collection("users").document(id).collection("profile").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String name = document.getString("name");
                        n.setText(name);

                        String bio = document.getString("bio");
                        b.setText(bio);

                        Log.d("tag", "DocumentSnapshot data: " + document.getData());

                    } else {
                        Log.d("tag", "No such document");
                    }
                } else {
                    Log.d("tag", "get failed with ", task.getException());
                }
            }
        });
    }

    public void updateInfo(View view) {

        final FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();

        final TextView e = (TextView) findViewById(R.id.textView9); //email
        final EditText n = (EditText) findViewById(R.id.editText5); //name
        final EditText b = (EditText) findViewById(R.id.editText6); //bio
        final EditText cp = findViewById(R.id.editpassword1);
        final EditText np = findViewById(R.id.editpassword2);

        final String currentPassword = cp.getText().toString();
        final String newPassword = np.getText().toString();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", n.getText().toString());
        userInfo.put("bio", b.getText().toString());
        db.collection("users").document(id).collection("profile").document(id)
                .set(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(newPassword.length() == 0 || currentPassword.length() == 0) {
                            Toast.makeText(UserActivity.this, "Successfully updated profile.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Log.d("tag", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("tag", "Error writing document", e);
                    }
                });
        //update password method
        String email = currentUser.getEmail();
        if(newPassword.length() != 0 && currentPassword.length() != 0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            AuthCredential credential = EmailAuthProvider
                    .getCredential(email, currentPassword);

            // Prompt the user to re-provide their sign-in credentials
            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                currentUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(UserActivity.this, "Successfully updated profile.",
                                                    Toast.LENGTH_SHORT).show();
                                            Log.d("PASS", "Password updated");
                                        } else {
                                            Log.d("PASS", "Error password not updated");
                                        }
                                    }
                                });
                            } else {
                                Log.d("PASS", "Error auth failed");
                            }
                        }
                    });
        }
        else if(newPassword.length() != 0 || currentPassword.length() != 0) {
            Toast.makeText(UserActivity.this, "Can't change password when one field is empty",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Create an options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Called when the user selects menu item
     */
    public void menuSelect(MenuItem item) {
        int id = item.getItemId();
        //Buy menu option
        if (id == R.id.buy) {
            final Intent intent = new Intent(this, BuyActivity.class);
            startActivity(intent);
        }
        //Sell menu option
        else if (id == R.id.sell) {
            Intent intent = new Intent(this, SellActivity.class);
            startActivity(intent);
        }
        //Chat menu option
        else if (id == R.id.chat) {
            Intent chatIntent = new Intent(this, ChatActivity.class);
            startActivity(chatIntent);
        }
        //User menu option
        else if (id == R.id.user) {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.signout) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

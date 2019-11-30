package com.example.buffstuff.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.Login.MainActivity;
import com.example.buffstuff.R;
import com.example.buffstuff.Sell.SellActivity;
import com.example.buffstuff.User.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//Load page that shows buy options
public class ChatActivity extends AppCompatActivity{
    //For prints
    String TAG = "Testing";
    RecyclerView recyclerView;
    com.example.buffstuff.Chat.ChatAdapter adapter;
    ArrayList<User> Users = new ArrayList<User>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When this function is called
        super.onCreate(savedInstanceState);
        final Intent loadIntent = getIntent();
        //Hold this context
        final RecyclerView.LayoutManager hold = new LinearLayoutManager(this);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String userId = currentUser.getUid();
        //Get all items from firebase
        db.collection("chats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //If succesfully accessed firebase
                        if (task.isSuccessful()) {
                            //For every item in the database
                            //Create an item card, set its name and price
                            //Add item card to item list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = new User();
                                user.setName(document.getString("about"));
                                if(userId.equals(document.getString("sender")) || userId.equals(document.getString("receiver"))){
                                    Users.add(user);
                                }
                            }
                        }
                        //If failed to access firebase
                        else {
                            Log.d(TAG, "Problem");
                        }
                        //Display chat activity on screen
                        setContentView(R.layout.chat_list);

                        //Set the adapter to add all the chat cards to the recycler view
                        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler_view);
                        recyclerView.setLayoutManager(hold);
                        adapter = new com.example.buffstuff.Chat.ChatAdapter(Users);

                        recyclerView.setAdapter(adapter);
                    }
                });


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

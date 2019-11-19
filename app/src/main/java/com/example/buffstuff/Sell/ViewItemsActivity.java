package com.example.buffstuff.Sell;

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
import android.widget.Adapter;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.Buy.Item;
import com.example.buffstuff.Chat.ChatActivity;
import com.example.buffstuff.Login.MainActivity;
import com.example.buffstuff.R;
import com.example.buffstuff.Sell.SellActivity;
import com.example.buffstuff.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//Load page that shows buy options
public class ViewItemsActivity extends AppCompatActivity{
    //For prints
    String TAG = "Testing";
    RecyclerView recyclerView;
    com.example.buffstuff.Sell.Adapter adapter;
    ArrayList<Item> Items = new ArrayList<Item>();
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
        db.collection("items")
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
                                Item item = new Item();
                                item.setName(document.getString("name"));
                                item.setPrice(document.getDouble("price"));
                                item.setCondition(document.getString("condition"));
                                item.setCategory(document.getString("category"));
                                item.setId(document.getId());

                                //If search name was entered, add to list iff name is equal
                                //If search name not entered, add all items
                                Log.d(TAG, "document: " + document.getString("user"));
                                Log.d(TAG, "current: " + userId);
                                if(userId.equals(document.getString("user"))){
                                    Log.d(TAG, "onComplete: item added");
                                    Items.add(item);
                                }
                            }
                        }
                        //If failed to access firebase
                        else {
                            Log.d("Testing", "Problem");
                        }
                        //Display buy activity on screen
                        setContentView(R.layout.activity_view_items);

                        //Set the adapter to add all the item cards to the recycler view
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(hold);
                        adapter = new com.example.buffstuff.Sell.Adapter(Items);

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

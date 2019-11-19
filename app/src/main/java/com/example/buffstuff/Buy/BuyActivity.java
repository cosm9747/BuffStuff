package com.example.buffstuff.Buy;

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
import android.widget.EditText;

import com.example.buffstuff.Chat.ChatActivity;
import com.example.buffstuff.Login.MainActivity;
import com.example.buffstuff.R;
import com.example.buffstuff.Sell.SellActivity;
import com.example.buffstuff.User.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//Load page that shows buy options
public class BuyActivity extends AppCompatActivity{
    //For prints
    String TAG = "Testing";
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Item> Items = new ArrayList<Item>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When this function is called
        super.onCreate(savedInstanceState);
        final Intent loadIntent = getIntent();
        //Find out what the search term is
        // Get the extras (if there are any)
        final Bundle extras = loadIntent.getExtras();
        final String searchName;
        final Double minPrice;
        final Double maxPrice;
        final ArrayList<String> condition;
        final ArrayList<String> category;
        //If there are extras, grab them all
        if (extras != null) {
            //Search name
            searchName = loadIntent.getStringExtra("SEARCH_NAME");
            //Min price
            if(loadIntent.getStringExtra("MIN_PRICE")!= null){
                minPrice = Double.parseDouble(loadIntent.getStringExtra("MIN_PRICE"));
            }
            else{
                minPrice = null;
            }
            //Max price
            if(loadIntent.getStringExtra("MAX_PRICE")!= null){
                maxPrice = Double.parseDouble(loadIntent.getStringExtra("MAX_PRICE"));
            }
            else{
                maxPrice = null;
            }
            //Condition
            if(loadIntent.getStringArrayListExtra("CONDITION") != null){
                Log.d(TAG, "There is condition");
                condition = loadIntent.getStringArrayListExtra("CONDITION");
            }
            else{
                condition = new ArrayList<String>();
                condition.add("Poor");
                condition.add("Acceptable");
                condition.add("Good");
                condition.add("Excellent");
            }
            //Condition
            if(loadIntent.getStringArrayListExtra("CATEGORY") != null){
                category = loadIntent.getStringArrayListExtra("CATEGORY");
            }
            else{
                category = new ArrayList<String>();
                category.add("Textbook");
                category.add("Furniture");
                category.add("Electronic");
                category.add("Other");
            }
        }
        //If no intents placed, put in default values
        else{
            //Search name
            searchName = null;
            //Min price
            minPrice = null;
            //Max price
            maxPrice = null;
            //Condition
            condition = new ArrayList<String>();
            condition.add("Poor");
            condition.add("Acceptable");
            condition.add("Good");
            condition.add("Excellent");
            //Category
            category = new ArrayList<String>();
            category.add("Textbook");
            category.add("Furniture");
            category.add("Electronic");
            category.add("Other");
        }
        //Hold this context
        final RecyclerView.LayoutManager hold = new LinearLayoutManager(this);
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
                            if(extras == null){
                                Items.add(item);
                            }
                            //If item meets all search criteria, then add it
                            else if(searchName == null || (searchName.toLowerCase()).equals(item.getName().toLowerCase())) {
                                if(minPrice == null || minPrice <= item.getPrice()) {
                                    if(maxPrice == null || maxPrice >= item.getPrice()) {
                                        if(condition.contains(item.getCondition())) {
                                            if (category.contains((item.getCategory()))) {
                                                Items.add(item);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //If failed to access firebase
                    else {
                        Log.d("Testing", "Problem");
                    }
                    //Display buy activity on screen
                    setContentView(R.layout.activity_buy);
                    if(searchName != null){
                        //Put right text in search box
                        EditText editText = (EditText)findViewById(R.id.searchInput);
                        editText.setText(searchName);
                    }

                    //Set the adapter to add all the item cards to the recycler view
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(hold);
                    adapter = new Adapter(Items);

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
    //Functions for each button pushed
    public void buttonSelect(View item) {
        int id = item.getId();
        //If filter button pushed, open up filter page
        if (id == R.id.filter) {
            //Create intent
            Intent intent = new Intent(this, FilterActivity.class);
            //Pass SEARCH_NAME
            EditText editText = findViewById(R.id.searchInput);
            String search = editText.getText().toString();
            intent.putExtra("SEARCH_NAME", search);
            startActivity(intent);
        }
        //If search button clicked, reload buy page with new search term
        if(id == R.id.search){
            Intent intent = new Intent(this, BuyActivity.class);
            EditText editText = findViewById(R.id.searchInput);
            String search = editText.getText().toString();
            intent.putExtra("SEARCH_NAME", search);
            startActivity(intent);
        }
    }
}

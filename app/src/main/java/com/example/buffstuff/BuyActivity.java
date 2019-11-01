package com.example.buffstuff;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//Load page that shows buy options
public class BuyActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Item> Items = new ArrayList<Item>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When this function is called
        super.onCreate(savedInstanceState);
        Intent loadIntent = getIntent();
        //Find out what the search term is
        // Get the extras (if there are any)
        Bundle extras = loadIntent.getExtras();
        final String searchName;
        if (extras == null) {
            searchName = " ";
            loadIntent.putExtra("SEARCH NAME", " ");
        }
        else{
            searchName = loadIntent.getStringExtra("SEARCH_NAME");
        }
        //Hold this context
        //Find out what the searc term is
        final RecyclerView.LayoutManager hold = new LinearLayoutManager(this);
        //If there was no search term...
        if (searchName.equals(" ")){
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
                                item.setId(document.getId());

                                Items.add(item);
                            }
                        }
                        //If failed to access firebase
                        else {
                            Log.d("Debug", "Problem");
                        }
                        //Display buy activity on screen
                        setContentView(R.layout.activity_buy);

                        //Set the adapter to add all the item cards to the recycler view
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(hold);
                        adapter = new Adapter(Items);

                        recyclerView.setAdapter(adapter);
                    }
                });
        }
        //If a search term was entered
        else{
            //Search database for all items with name that has search term
            db.collection("items")
                .whereEqualTo("name", searchName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //For every item in the database
                            //Create an item card, set its name and price
                            //Add item card to item list
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Item item = new Item();
                                item.setName(document.getString("name"));
                                item.setPrice(document.getDouble("price"));
                                item.setId(document.getId());

                                Items.add(item);
                            }
                        }
                        //If failed to access firebase
                        else {
                            Log.d("Debug", "Problem");
                        }
                        //Display buy activity on screen
                        setContentView(R.layout.activity_buy);

                        //Set the adapter to add all the item cards to the recycler view
                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(hold);
                        adapter = new Adapter(Items);

                        recyclerView.setAdapter(adapter);
                    }
                });
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
            intent.putExtra("SEARCH_NAME", " ");
            startActivity(intent);
        }
        //Sell menu option
        else if (id == R.id.sell) {
            setContentView(R.layout.activity_sell);
        }
        //Chat menu option
        else if (id == R.id.chat) {
            Intent chatIntent = new Intent(this, ChatActivity.class);
            startActivity(chatIntent);
        }
        //User menu option
        else if (id == R.id.user) {
            setContentView(R.layout.user);
        }
    }
    //Functions for each button pushed
    public void buttonSelect(View item) {
        int id = item.getId();
        //If filter button pushed, open up filter page
        if (id == R.id.filter) {
            Intent intent = new Intent(this, FilterActivity.class);
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

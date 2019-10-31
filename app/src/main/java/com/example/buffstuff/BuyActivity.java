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

public class BuyActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Item> Items = new ArrayList<Item>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( "Somewhat success", "In onCreate");
        super.onCreate(savedInstanceState);
        Intent loadIntent = getIntent();
<<<<<<< Updated upstream
        Log.d( "Somewhat success", "And then");
        final String searchName = loadIntent.getStringExtra("SEARCH_NAME");
        Log.d( "Somewhat success", "Before");
        Log.d( "Somewhat success", searchName);
        Log.d( "Somewhat success", "After");
=======
        //Find out what the search term is
        final String searchName = loadIntent.getStringExtra("SEARCH_NAME");
        //Hold this context
>>>>>>> Stashed changes
        final RecyclerView.LayoutManager hold = new LinearLayoutManager(this);
        if (searchName.equals(" ")){
            Log.d("Somewhat success", "Here");
            db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Item item = new Item();
                                item.setName(document.getString("name"));
                                item.setPrice(document.getDouble("price"));
                                item.setId(document.getId());

                                Items.add(item);
                                String print = "" + Items.size();

                            }
                        } else {
                            Item item = new Item();
                            item.setName("Problem");

                            Items.add(item);
                        }
                        setContentView(R.layout.activity_buy);

                        String print = "" + Items.size();

                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(hold);
                        adapter = new Adapter(Items);

                        recyclerView.setAdapter(adapter);
                    }
                });
        }
        else{
            db.collection("items")
                .whereEqualTo("name", searchName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Item item = new Item();
                                item.setName(document.getString("name"));
                                Double priceHold = (document.getDouble("price"));
                                item.setPrice(priceHold);

                                Items.add(item);
                                String print = "" + Items.size();

                            }
                        } else {
                            Item item = new Item();
                            item.setName("Problem");

                            Items.add(item);
                        }
                        setContentView(R.layout.activity_buy);

                        String print = "" + Items.size();

                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(hold);
                        adapter = new Adapter(Items);

                        recyclerView.setAdapter(adapter);
                        EditText editText = findViewById(R.id.searchInput);
                        editText.setText(searchName);
                    }
                });
        }
    }

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
        if (id == R.id.buy) {
            Intent intent = new Intent(this, BuyActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.sell) {
            setContentView(R.layout.activity_sell);
        }
        else if (id == R.id.chat) {
            setContentView(R.layout.activity_chat);
        }
        else if (id == R.id.user) {
            setContentView(R.layout.user);
        }
    }
    public void buttonSelect(View item) {
        int id = item.getId();
        if (id == R.id.filter) {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
        }
<<<<<<< Updated upstream
        if(id == R.id.search){
=======
        //If search button clicked, reload buy page with new search term
        else if(id == R.id.search){
>>>>>>> Stashed changes
            Intent intent = new Intent(this, BuyActivity.class);
            EditText editText = findViewById(R.id.searchInput);
            String search = editText.getText().toString();
            intent.putExtra("SEARCH_NAME", search);
            startActivity(intent);
        }
    }
    //When a card clicked, open of view of card item
    public void itemSelect(String id) {

    }
}

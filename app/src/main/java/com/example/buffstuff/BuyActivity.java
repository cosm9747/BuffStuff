package com.example.buffstuff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class BuyActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Item> Items = new ArrayList<Item>();
    FirebaseFirestore db;

    public static final String[] Names= {"Textbook1","Clicker", "Pens","Couch","Textbook2","Item","Item1","Item2","Item3","Item4","Item5", "Item6", "Item7", "Item8"};
    public static final Double[] Prices= {1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0,11.0,12.0,13.0,14.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        for(int i=0;i<Names.length;i++)
        {
            Item item = new Item();
            item.setName(Names[i]);
            item.setPrice(Prices[i]);

            Items.add(item);
        }


        adapter = new Adapter(Items);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
            setContentView(R.layout.filter);
        }
    }
}

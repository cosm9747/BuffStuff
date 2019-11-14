package com.example.buffstuff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FilterActivity extends AppCompatActivity {
    String TAG = "Testing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Testing", "onCreate: filter");
        //When this function is called load up sent intent
        super.onCreate(savedInstanceState);
        Intent loadIntent = getIntent();
        //Set display
        setContentView(R.layout.filter);
        //Get any "arguments" passed to this screen
        final String searchName;
        Bundle extras = loadIntent.getExtras();
        if (extras == null) {
            searchName = " ";
            loadIntent.putExtra("SEARCH_NAME", " ");
        }
        //If extras...
        else{
            //If search name entered
            if (loadIntent.getStringExtra("SEARCH_NAME") != null) {
                searchName = loadIntent.getStringExtra("SEARCH_NAME");
                //Put right text in search box
                EditText editText = (EditText)findViewById(R.id.searchInput);
                editText.setText(searchName);
            }
            //default search name
            else{
                searchName = " ";
                loadIntent.putExtra("SEARCH_NAME", " ");
            }
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

    //Functions for each button pushed
    public void buttonSelect(View item) {
        int id = item.getId();
        Log.d("Testing", "function called");
        //If filter button pushed, open up filter page
        if (id == R.id.applyFilter) {
            Log.d("Testing", "register intent");
            Intent intent = new Intent(this, BuyActivity.class);
            //Send search term
            EditText editText = findViewById(R.id.searchInput);
            String data = editText.getText().toString();
            if(!data.equals("")){
                intent.putExtra("SEARCH_NAME", data);
            }
            //Send Min price
            editText = findViewById(R.id.priceMin);
            data = editText.getText().toString();
            if(!data.equals("")){
                intent.putExtra("MIN_PRICE", data);
            }

            //Send max price
            editText = findViewById(R.id.priceMax);
            data = editText.getText().toString();
            if(!data.equals("")){
                intent.putExtra("MAX_PRICE", data);
            }
            //Send back to buy
            startActivity(intent);

        }
    }
}

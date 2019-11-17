package com.example.buffstuff.Buy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.Chat.ChatActivity;
import com.example.buffstuff.Login.MainActivity;
import com.example.buffstuff.R;
import com.example.buffstuff.Sell.SellActivity;
import com.example.buffstuff.UserActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    String TAG = "Testing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (id == R.id.applyFilter) {
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
            //Construct array for condition and if anything checked put as intent
            CheckBox Poor = (CheckBox) findViewById(R.id.Poor);
            CheckBox Acceptable = (CheckBox) findViewById(R.id.Acceptable);
            CheckBox Good = (CheckBox) findViewById(R.id.Good);
            CheckBox Excellent = (CheckBox) findViewById(R.id.Excellent);
            Log.d(TAG, Poor.isChecked() + ": poor");
            if(Poor.isChecked() || Acceptable.isChecked() || Good.isChecked() || Excellent.isChecked()){
                List condition = new ArrayList<String>();
                if(Poor.isChecked()){
                    Log.d(TAG, "Poor was checked");
                    condition.add("Poor");
                }
                if(Acceptable.isChecked()){
                    condition.add("Acceptable");
                }
                if(Good.isChecked()){
                    condition.add("Good");
                }
                if(Excellent.isChecked()){
                    condition.add("Excellent");
                }
                intent.putStringArrayListExtra("CONDITION", (ArrayList<String>)condition);
                Log.d(TAG, "put condition: " + condition);
            }
            //Construct array for category and if anything checked put as intent
            CheckBox Textbook = (CheckBox) findViewById(R.id.Textbook);
            CheckBox Furniture = (CheckBox) findViewById(R.id.Furniture);
            CheckBox Electronic = (CheckBox) findViewById(R.id.Electronic);
            CheckBox Other = (CheckBox) findViewById(R.id.Other);
            if(Textbook.isChecked() || Furniture.isChecked() || Electronic.isChecked() || Other.isChecked()){
                List category = new ArrayList<String>();
                if(Textbook.isChecked()){
                    category.add("Textbook");
                }
                if(Furniture.isChecked()){
                    category.add("Furniture");
                }
                if(Electronic.isChecked()){
                    category.add("Electronic");
                }
                if(Other.isChecked()){
                    category.add("Other");
                }
                intent.putStringArrayListExtra("CATEGORY", (ArrayList<String>)category);
            }
            //Send back to buy
            startActivity(intent);
        }
    }
}

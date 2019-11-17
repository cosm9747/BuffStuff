package com.example.buffstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.Chat.ChatActivity;
import com.example.buffstuff.Login.MainActivity;
import com.example.buffstuff.Sell.SellActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
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

package com.example.buffstuff.Sell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.buffstuff.Buy.BuyActivity;
import com.example.buffstuff.R;

public class UploadActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void goToSell(View view) {
        Intent intent = new Intent(this, SellActivity.class);
        startActivity(intent);
    }
}

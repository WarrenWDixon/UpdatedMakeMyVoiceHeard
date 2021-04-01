package com.example.android.makemyvoiceheard;

import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WWD", "in Detail Activity onCreate");
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Integer index = intent.getIntExtra(MainActivity.IMAGE_SELECTION, 0);
        Log.d("WWD", "the selection index was " + index); 
    }
}
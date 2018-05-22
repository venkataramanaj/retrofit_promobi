package com.example.retofit_promobi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.retofit_promobi.database.DatabaseHandler;
import com.example.retofit_promobi.pojo.Data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_next, btn_get;
    DatabaseHandler db;
    List<Data> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_next = findViewById(R.id.next);
        btn_get = findViewById(R.id.get);

        db = new DatabaseHandler(MainActivity.this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Intent intent = new Intent(MainActivity.this, DataActivity.class);
                    intent.putExtra("from", "next");
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = db.getAllData();
                if (data.size() > 0) {
                    if (isNetworkAvailable()) {
                        Intent intent = new Intent(MainActivity.this, DataActivity.class);
                        intent.putExtra("from", "get");
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Please check your network connection for loading images", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "First get data through online", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

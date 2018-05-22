package com.example.retofit_promobi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.retofit_promobi.Adapter.DataAdapter;
import com.example.retofit_promobi.database.DatabaseHandler;
import com.example.retofit_promobi.network.Api;
import com.example.retofit_promobi.network.RetrofitClient;
import com.example.retofit_promobi.pojo.Data;
import com.example.retofit_promobi.pojo.Example;
import com.example.retofit_promobi.pojo.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends Activity {
    RecyclerView list;
    Api apiService;
    DatabaseHandler db;
    List<Result> result=new ArrayList<>();
    List<Data> data=new ArrayList<>();
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("<><>","onCreate");
        setContentView(R.layout.activity_data);

        progress = new ProgressDialog(this);

        db=new DatabaseHandler(DataActivity.this);

        list=findViewById(R.id.list);

        if(getIntent().getStringExtra("from").equals("next")) {
            apiService = RetrofitClient.getClient().create(Api.class);
            callingCTEAPI();
        }else{
            data=db.getAllData();
            DataAdapter mAdapter = new DataAdapter("get",result,data,DataActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            list.setLayoutManager(mLayoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("<><>","onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("<><>","onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("<><>","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("<><>","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("<><>","onDestroy");
    }

    public void callingCTEAPI() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("api-key", "57af699f5c0f484bbce7e7204c8d86e6");

//        ProgressDialogLoader.progressdialog_creation(this, "Loading");

        progress.setMessage("Downloading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setIndeterminate(true);
        progress.show();

        Call<Example> call = apiService.getData(queryParams);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Log.e("response is", "<><><" + response.body());
                Example e=response.body();
                String status=e.getStatus();
                result=e.getResults();


                db.insertData(result);

                progress.dismiss();
                DataAdapter mAdapter = new DataAdapter("next",result,data,DataActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                list.setLayoutManager(mLayoutManager);
                list.setItemAnimator(new DefaultItemAnimator());
                list.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("data", t.toString());
                Log.e("fail is", "<><><" + call.request().toString());
//                ProgressDialogLoader.progressdialog_dismiss();
            }
        });
    }
}

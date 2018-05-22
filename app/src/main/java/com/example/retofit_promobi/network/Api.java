package com.example.retofit_promobi.network;

/**
 * Created by Ramana on 5/20/2018.
 */

import com.example.retofit_promobi.pojo.Example;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Api {

    @GET("svc/topstories/v2/home.json?")
    Call<Example> getData(@QueryMap Map<String, String> fields);
}

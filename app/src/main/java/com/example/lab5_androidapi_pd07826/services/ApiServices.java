package com.example.lab5_androidapi_pd07826.services;

import com.example.lab5_androidapi_pd07826.models.Distributor;
import com.example.lab5_androidapi_pd07826.models.Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    public static String BASE_URL="http://192.168.1.240:3000/api/";
    @GET("get-list-distributor")
    Call<Response<List<Distributor>>> getListDistributor();

    @GET("search-distributors")
    Call<Response<List<Distributor>>> getSearchDistributors(@Query("key") String key);

    @DELETE("delete-distributors/{id}")
    Call<Response<Distributor>> getDeleteDistributor(@Path("id") String id);

    @POST("add-distributor")
    Call<Response<Distributor>> addDistributor(@Body Distributor distributor);
    @PUT("update-distributors/{id}")
    Call<Response<Distributor>> getUpdateDistributor(@Path("id") String id, @Body Distributor distributor);
}

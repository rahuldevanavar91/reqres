package com.android.pharmaeasytest.network;

import android.telecom.Call;

import com.android.pharmaeasytest.model.ReqresResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rahul on 6/2/18.
 */

public interface ApiInterface {

    @GET("api/users")
    retrofit2.Call<ReqresResponse> getUserList(@Query("page") int page);
}

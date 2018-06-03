package com.android.pharmaeasytest.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rahul on 6/2/18.
 */
public class ApiClient {

    private static final String BASE_URL = "http://reqres.in/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public interface BasePresenter {
        void showLoading();

        void hideLoading();

        void showError(String message);
    }

}
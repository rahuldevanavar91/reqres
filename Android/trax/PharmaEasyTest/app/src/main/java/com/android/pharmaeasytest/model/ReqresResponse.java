package com.android.pharmaeasytest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 6/2/18.
 */

public class ReqresResponse {
    private int total;

    @SerializedName("per_page")
    private int perPage;

    private int page;

    private List<Data> data;

    public int getPerPage() {
        return perPage;
    }

    public int getPage() {
        return page;
    }

    public List<Data> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }
}

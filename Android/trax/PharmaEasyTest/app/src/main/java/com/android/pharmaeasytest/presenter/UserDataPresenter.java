package com.android.pharmaeasytest.presenter;

import com.android.pharmaeasytest.model.Data;
import com.android.pharmaeasytest.model.ReqresResponse;
import com.android.pharmaeasytest.network.ApiClient;
import com.android.pharmaeasytest.network.ApiInterface;
import com.android.pharmaeasytest.view.adapter.UserListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 6/2/18.
 */

public class UserDataPresenter {


    private final ApiInterface mApiService;
    private final UserDataListener mUserDataListener;
    private List<Data> mUserDataList;

    public UserDataPresenter(UserDataListener dataListener) {
        mApiService = ApiClient.getClient().create(ApiInterface.class);
        mUserDataList = new ArrayList<>();
        mUserDataListener = dataListener;
    }

    public void getUserList(int pageNumber) {
        mUserDataListener.showLoading();
        Call<ReqresResponse> call = mApiService.getUserList(pageNumber);
        call.enqueue(new Callback<ReqresResponse>() {
            @Override
            public void onResponse(Call<ReqresResponse> call, Response<ReqresResponse> response) {
                ReqresResponse reqresResponse = response.body();
                removeMoreLoading();
                if (reqresResponse != null) {
                    updateData(reqresResponse);
                }
                mUserDataListener.hideLoading();
                mUserDataListener.onResult(mUserDataList);
            }

            @Override
            public void onFailure(Call<ReqresResponse> call, Throwable t) {
                removeMoreLoading();
                mUserDataListener.hideLoading();
                if (t instanceof IOException) {
                    mUserDataListener.showError("Network failure");
                } else {
                    mUserDataListener.showError("Something went wrong");
                }
            }
        });
    }

    private void removeMoreLoading() {
        if (!mUserDataList.isEmpty() &&
                mUserDataList.get(mUserDataList.size() - 1).getViewType() ==
                        UserListAdapter.VIEW_TYPE_LOAD_MORE) {
            mUserDataList.remove(mUserDataList.size() - 1);
        }

    }

    private void updateData(ReqresResponse reqresResponse) {
        if (reqresResponse.getData() != null) {
            mUserDataList.addAll(reqresResponse.getData());
            if (mUserDataList.size() != reqresResponse.getTotal()) {
                Data loadMore = new Data();
                loadMore.setViewType(UserListAdapter.VIEW_TYPE_LOAD_MORE);
                mUserDataList.add(loadMore);
            }
        }
    }

    public interface UserDataListener extends ApiClient.BasePresenter {
        void onResult(List<Data> userData);
    }
}

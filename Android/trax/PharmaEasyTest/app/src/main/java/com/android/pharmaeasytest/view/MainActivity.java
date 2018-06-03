package com.android.pharmaeasytest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.pharmaeasytest.R;
import com.android.pharmaeasytest.model.Data;
import com.android.pharmaeasytest.presenter.UserDataPresenter;
import com.android.pharmaeasytest.utils.Utils;
import com.android.pharmaeasytest.view.adapter.UserListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UserDataPresenter.UserDataListener, UserListAdapter.UserListAdapterListener {

    @BindView(R.id.user_recycer_list)
    RecyclerView mUserRecycler;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private UserDataPresenter mUserDataPresenter;
    private int mPageNumber;
    private UserListAdapter mUseDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestForUserList();
    }

    private void requestForUserList() {
        if (mUserDataPresenter == null) {
            mUserDataPresenter = new UserDataPresenter(this);
        }
        if (Utils.isNetworkAvailable(getBaseContext())) {
            mUserDataPresenter.getUserList(++mPageNumber);
        } else {
            Toast.makeText(getBaseContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showLoading() {
        if (mPageNumber == 1) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResult(List<Data> dataList) {
        if (mUseDataAdapter != null) {
            mUseDataAdapter.updateList(dataList);
        } else {
            mUseDataAdapter = new UserListAdapter(dataList, getBaseContext(), this);
            mUserRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            mUserRecycler.setAdapter(mUseDataAdapter);
        }
    }


    @Override
    public void onLoadMore(int position) {
        requestForUserList();
    }

    @Override
    public void onItemClick(View view, Data data) {
        Intent intent = new Intent(getBaseContext(), UserDetailActivity.class);
        intent.putExtra(getString(R.string.user_data), data);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                ViewCompat.getTransitionName(view));
        startActivity(intent, options.toBundle());
    }
}

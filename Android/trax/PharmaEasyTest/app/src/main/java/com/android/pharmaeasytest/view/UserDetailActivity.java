package com.android.pharmaeasytest.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pharmaeasytest.R;
import com.android.pharmaeasytest.model.Data;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rahul on 6/3/18.
 */

public class UserDetailActivity extends AppCompatActivity {

    @BindView(R.id.user_detail_avatar)
    ImageView mUserAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        setActionBar();
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            updateData();
        }

    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateData() {
        Data userData = getIntent().getParcelableExtra(getString(R.string.user_data));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mUserAvatar.setTransitionName(userData.getId());
        }
        Picasso.with(getBaseContext())
                .load(userData.getAvatar())
                .into(mUserAvatar);

        mUserName.setText(String.format("%s %s", userData.getFirstName(), userData.getLastName()));
    }
}

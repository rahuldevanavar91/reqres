package com.android.pharmaeasytest.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pharmaeasytest.R;
import com.android.pharmaeasytest.model.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rahul  on 6/2/18.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private static final int VIEW_TYPE_LIST_ITEM = 0;
    public static final int VIEW_TYPE_LOAD_MORE = R.layout.more_laoding_layout;
    private final Context mContext;

    private List<Data> mUserData;
    private UserListAdapterListener mChangeListener;
    private int mLastMoreRequestPos;

    public UserListAdapter(List<Data> userData, Context context, UserListAdapterListener listener) {
        mUserData = userData;
        mContext = context;
        mChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int view;
        switch (viewType) {
            case VIEW_TYPE_LIST_ITEM:
                view = R.layout.user_list_item;
                break;
            default:
                view = viewType;
        }
        return new ViewHolder((LayoutInflater
                .from(parent.getContext())
                .inflate(view, parent, false)), viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        switch (getItemViewType(position)) {
            case VIEW_TYPE_LIST_ITEM:
                updateListItem(holder);
                break;
            case VIEW_TYPE_LOAD_MORE:
                if (mLastMoreRequestPos != position) {
                    mChangeListener.onLoadMore(position);
                    mLastMoreRequestPos = position;
                }
                break;
        }
    }

    private void updateListItem(ViewHolder holder) {
        Data item = mUserData.get(holder.getAdapterPosition());
        ViewCompat.setTransitionName(holder.userImage, item.getId());
        Picasso.with(mContext)
                .load(item.getAvatar())
                .into(holder.userImage);
        holder.userName.setText(String.format("%s %s", item.getFirstName(), item.getLastName()));
        holder.itemView.setTag(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mUserData != null ? mUserData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mUserData.get(position).getViewType();
    }

    public void updateList(List<Data> dataList) {
        mUserData = dataList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_avatar)
        ImageView userImage;
        @BindView(R.id.user_name)
        TextView userName;

        ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case VIEW_TYPE_LIST_ITEM:
                    ButterKnife.bind(this, itemView);
                    break;
            }
        }

        @OnClick(R.id.root)
        public void submit(View view) {
            mChangeListener.onItemClick(userImage, mUserData.get((int) view.getTag()));
        }
    }

    public interface UserListAdapterListener {
        void onLoadMore(int position);

        void onItemClick(View view, Data data);
    }

}

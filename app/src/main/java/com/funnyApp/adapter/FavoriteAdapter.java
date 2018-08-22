package com.funnyApp.adapter;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyApp.R;
import com.funnyApp.data.model.Favorite;
import com.funnyApp.helper.UIHelper;
import com.funnyApp.view.fragment.FavoritePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hussein on 1/16/17.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyHolder> implements FavoritePresenter.View {
    private List<Favorite> mFavoriteList;
    public Activity mActivity;

    public FavoriteAdapter(List<Favorite> favoriteList, Activity activity)
    {

        this.mActivity = activity;
        this.mFavoriteList = favoriteList;
    }



    class MyHolder extends RecyclerView.ViewHolder{
        public ImageView mArticleImage;
        public TextView mArticleDesc;
        public Button mDeleteFavorite;
        public MyHolder(View itemView) {
            super(itemView);
            mArticleImage=(ImageView)itemView.findViewById(R.id.imArticle);
            mArticleDesc=(TextView)itemView.findViewById(R.id.mArticleDesc);
            mDeleteFavorite =(Button)itemView.findViewById(R.id.btnDeleteFavorite);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_favorite,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final Favorite favorite = mFavoriteList.get(position);
        //set the model with View

        holder.mArticleDesc.setText(favorite.getArticleTitle());
        holder.mDeleteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.simpleDialogBox("مورد با موفقیت حذف شد", mActivity,R.layout.dialog_ok_box,"پیام");

                FavoritePresenter favoritePresenter=new FavoritePresenter(FavoriteAdapter.this);
                favoritePresenter.deleteFavorite(favorite);
            }
        });
    }

    @Override
    public void deleteFavorite(ArrayList<Favorite> id) {

    }

    @Override
    public void updateFavoriteRecycler(ArrayList<Favorite> info) {
        RecyclerView recyclerView=(RecyclerView) mActivity.findViewById(R.id.favorite_recyclerview);
        FavoriteAdapter adapterClass;
        adapterClass=new FavoriteAdapter(info, mActivity);
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new
                DividerItemDecoration(mActivity,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapterClass);
        adapterClass.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }
}

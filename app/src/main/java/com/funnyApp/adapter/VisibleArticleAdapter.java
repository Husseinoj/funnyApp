package com.funnyApp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.funnyApp.R;
import com.funnyApp.data.model.Article;

import java.util.ArrayList;

/**
 * Created by hussein on 6/4/17.
 */

public class VisibleArticleAdapter extends RecyclerView.Adapter<VisibleArticleAdapter.MyHolder>{
    private ArrayList<Article> mArticleList;
    View mView;
    public VisibleArticleAdapter(View view){
        this.mView =view;
        mArticleList =new ArrayList<>();
    }
    public VisibleArticleAdapter( ArrayList<Article> mArticleList)
    {
        this.mArticleList = mArticleList;

    }


    class MyHolder extends RecyclerView.ViewHolder {

        public Button mContinue;
        public MyHolder(View itemView) {
            super(itemView);
            mContinue =(Button)itemView.findViewById(R.id.btn_continue);

        }


    }

    @Override
    public VisibleArticleAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_search_result,parent,false);
        return new VisibleArticleAdapter.MyHolder(view);
    }
    @Override
    public void onBindViewHolder(VisibleArticleAdapter.MyHolder holder, final int position) {
        Article addressSearchModel = mArticleList.get(position);
        /*holder.tvname.setText(addressSearchModel.getName());
        holder.tvRegions.setText(addressSearchModel.getRegions());
        holder.tvAddress.setText(addressSearchModel.getAddress());
        holder.tvTelephone.setText(addressSearchModel.getTelephone());
        holder.tvWorkingHours.setText(addressSearchModel.getWorkingHours());*/
        holder.mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}

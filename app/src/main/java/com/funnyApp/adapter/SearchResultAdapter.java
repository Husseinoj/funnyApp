package com.funnyApp.adapter;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.funnyApp.R;
import com.funnyApp.data.model.Article;

import java.util.List;


/**
 * Created by hussein on 6/4/17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyHolder>{
    private List<Article> mSearchResultList;
    FragmentTransaction mFragmentTransition;

    public SearchResultAdapter(FragmentTransaction fragmentTransaction,List<Article> mSearchResultList)
    {
        this.mSearchResultList = mSearchResultList;
        this.mFragmentTransition=fragmentTransaction;
    }


    class MyHolder extends RecyclerView.ViewHolder {

        public Button btnContinue;
        public MyHolder(View itemView) {
            super(itemView);
            btnContinue =(Button)itemView.findViewById(R.id.btn_continue);

        }


    }

    @Override
    public SearchResultAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_search_result,parent,false);
        return new SearchResultAdapter.MyHolder(view);
    }
    @Override
    public void onBindViewHolder(SearchResultAdapter.MyHolder holder, final int position) {
        Article article = mSearchResultList.get(position);
        //Set the result here in and access via holder
        holder.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentTransition.addToBackStack("back");
                mFragmentTransition.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }
}

package com.funnyApp.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funnyApp.R;
import com.funnyApp.adapter.SearchResultAdapter;
import com.funnyApp.data.model.Article;
import com.funnyApp.data.model.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment implements SearchResultPresenter.View{
    @BindView(R.id.search_result_recyclerview) RecyclerView  mSeachRecyclerView;
    SearchResultPresenter mSearchResultPresenter;

    SearchResultAdapter mSearchResultAdapter;

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_result,container,false);
        ButterKnife.bind(this,view);
        EventBus setTitleEvent;
        setTitleEvent=EventBus.getDefault();
        setTitleEvent.post(new Message("نتیجه جست و جو"));
        /*PreparData();*/

        mSearchResultPresenter=new SearchResultPresenter(this);
        mSearchResultPresenter.getKnowledgeModels();


        return view;
    }


    @Override
    public void updateKnowledgeRecycler(ArrayList<Article> articles) {
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_search_fragment,new ArticleFragment());

        mSearchResultAdapter=new SearchResultAdapter(transaction, articles);

        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getContext());
        mSeachRecyclerView.setLayoutManager(LayoutManager);
        mSeachRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSeachRecyclerView.setAdapter(mSearchResultAdapter);
        mSearchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void addFavorite(Article articleModels) {

    }

    @Override
    public void deleteFavorite(Article articleModels) {

    }
}

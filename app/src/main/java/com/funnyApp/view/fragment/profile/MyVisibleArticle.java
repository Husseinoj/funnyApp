package com.funnyApp.view.fragment.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funnyApp.R;
import com.funnyApp.adapter.VisibleArticleAdapter;
import com.funnyApp.data.model.Article;
import com.funnyApp.data.model.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyVisibleArticle extends Fragment implements VisibleArticlePresenter.View{

    View rootView;
    VisibleArticlePresenter mVisibleArticlePresenter;
    public MyVisibleArticle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_visible_article, container, false);


        rootView=view;
        mVisibleArticlePresenter=new VisibleArticlePresenter(this);
        mVisibleArticlePresenter.getKnowledgeModels();
        EventBus eventBus=EventBus.getDefault();
        eventBus.post(new Message("مطالب تایید شده"));
        return view;
    }


    @Override
    public void updateKnowledgeRecycler(ArrayList<Article> articles) {
        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.knowledge_recyclerview);
        VisibleArticleAdapter adapterClass;
        adapterClass=new VisibleArticleAdapter(articles);
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new
                DividerItemDecoration(rootView.getContext(),LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapterClass);
        adapterClass.notifyDataSetChanged();
    }

    @Override
    public void addFavorite(Article articleModels) {

    }

    @Override
    public void deleteFavorite(Article articleModels) {

    }
}

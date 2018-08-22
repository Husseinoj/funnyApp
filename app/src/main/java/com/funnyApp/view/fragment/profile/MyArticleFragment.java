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
import com.funnyApp.adapter.MyArticleAdapter;
import com.funnyApp.data.model.Article;
import com.funnyApp.data.model.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyArticleFragment extends Fragment implements MyArticlePresenter.View {

    View rootView;
    MyArticlePresenter mArticlePresenter;
    public MyArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_article, container, false);
        rootView=view;
        mArticlePresenter=new MyArticlePresenter(this);
        mArticlePresenter.myArticlePresenter();
        EventBus eventBus=EventBus.getDefault();
        eventBus.post(new Message("مطالب من"));
        return view;
    }


    @Override
    public void updateArticleRecycler(ArrayList<Article> articles) {
        //PreparData();
        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.my_article_recyclerview);
        MyArticleAdapter adapterClass;
        adapterClass=new MyArticleAdapter(articles,getActivity());
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapterClass);
        adapterClass.notifyDataSetChanged();
    }

    @Override
    public void deleteFavorite(Article articleModels) {

    }

    @Override
    public void editFavorite(Article articleModels) {

    }
}

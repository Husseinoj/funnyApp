package com.funnyApp.view.fragment;


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
import com.funnyApp.adapter.FavoriteAdapter;
import com.funnyApp.data.model.Favorite;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment implements FavoritePresenter.View{

    View rootView;

    FavoritePresenter mFavoritePresenter;
    public FavoritFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);
        rootView=view;
        mFavoritePresenter =new FavoritePresenter(this);

        mFavoritePresenter.getFavoriteModel();

    return view;
    }



    @Override
    public void updateFavoriteRecycler(ArrayList<Favorite> info) {

        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.favorite_recyclerview);
        FavoriteAdapter adapterClass;
        adapterClass=new FavoriteAdapter(info,getActivity());
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapterClass);
        adapterClass.notifyDataSetChanged();

    }

    @Override
    public void deleteFavorite(ArrayList<Favorite> id) {

    }

}

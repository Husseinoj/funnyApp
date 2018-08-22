package com.funnyApp.view.fragment.root;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.funnyApp.R;
import com.funnyApp.view.fragment.SearchFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRoot extends Fragment {


    public SearchRoot() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_root, container, false);
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_search_fragment,new SearchFragment());
        transaction.commit();
        return view;
    }

}

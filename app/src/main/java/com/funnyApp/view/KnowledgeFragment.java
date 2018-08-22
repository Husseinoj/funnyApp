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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.funnyApp.R;
import com.funnyApp.data.model.Article;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;
import com.funnyApp.view.fragment.KnowledgePresenter;
/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeFragment extends Fragment implements KnowledgePresenter.View{
    View rootView;
    @BindView(R.id.knowledge_recyclerview) RecyclerView mRvKnowledge;

    @BindView(R.id.sp_category) SearchableSpinner mSpCategory;

    ArrayList<Article> mSearchResults;

    KnowlageAdapter adapterClass;
    KnowledgePresenter mKnowledgePresenter;
    String[] mCategoryList = {"فیلتر بر اساس دسته بندی مطلب", "مولتی مدیا","سخت افزار","برنامه نویسی","شبکه","گرافیک","نرم افزار"};
    public KnowledgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_knowledge, container, false);
        ButterKnife.bind(this,view);
        rootView=view;
        mSearchResults =new ArrayList<>();
        mKnowledgePresenter=new KnowledgePresenter(this);

        mKnowledgePresenter.getKnowledgeModels();
        //segment is a tools that help me to implement the tab easily
        configSegment(view);
        configeSpinner();


        return view;
    }

    private void configeSpinner() {
        ArrayAdapter<String> adapteRregion = new ArrayAdapter<String>(getActivity(), R.layout.spineer_item, mCategoryList);
        adapteRregion.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        mSpCategory.setAdapter(adapteRregion);
        mSpCategory.setTitle("انتخاب");
        mSpCategory.setPositiveButton("خروج");
        mSpCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void configSegment(View view) {
        SegmentedGroup tab = (SegmentedGroup) view.findViewById(R.id.status_tab);
        tab.setTintColor(getActivity().getResources().getColor(R.color.colorBlue));
        tab.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
        tab.check(R.id.visit);
    }





    @Override
    public void updateKnowledgeRecycler(ArrayList<Article> articles) {
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_knowlage_fragment,new ArticleFragment());
        mRvKnowledge=(RecyclerView)rootView.findViewById(R.id.knowledge_recyclerview);
        adapterClass=new KnowlageAdapter(transaction, articles);
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getContext());
        mRvKnowledge.setLayoutManager(LayoutManager);
        mRvKnowledge.setItemAnimator(new DefaultItemAnimator());
        mRvKnowledge.setAdapter(adapterClass);
        adapterClass.notifyDataSetChanged();
    }

    @Override
    public void addFavorite(Article articleModels) {

    }

    @Override
    public void deleteFavorite(Article articleModels) {

    }
}

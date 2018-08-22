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
 * Created by hussein on 1/15/17.
 */

public class KnowlageAdapter extends RecyclerView.Adapter<KnowlageAdapter.MyHolder> {
    private List<Article> mKnowledgeList;

    //use the Fragment Transition to go to another fragment
    FragmentTransaction mFragmentTransition;

    public KnowlageAdapter(FragmentTransaction fragmentTransaction, List<Article> articles)
    {
        this.mKnowledgeList = articles;
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
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_search_result,parent,false);
        return new MyHolder(view);
    }
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        Article article = mKnowledgeList.get(position);

        /*
        do it later
        set the CardView views like this

        holder.tvTitle.setText(article.getTitle());
       */
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
        return mKnowledgeList.size();
    }

}

package com.funnyApp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyApp.R;
import com.funnyApp.data.model.Article;
import com.funnyApp.view.fragment.profile.MyArticlePresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hussein on 1/16/17.
 */

public class MyArticleAdapter extends RecyclerView.Adapter<MyArticleAdapter.MyHolder> implements MyArticlePresenter.View {

    private List<Article> mArticles;
    MyArticlePresenter mArticlePresenter;
    public Activity mActivity;

    public MyArticleAdapter(List<Article> mArticles, Activity activity)
    {
        this.mActivity = activity;
        this.mArticles = mArticles;
    }

    @Override
    public void updateArticleRecycler(ArrayList<Article> articles) {
        //setting Adapter
        RecyclerView recyclerView=(RecyclerView) mActivity.findViewById(R.id.my_article_recyclerview);
        MyArticleAdapter adapterClass;
        adapterClass=new MyArticleAdapter(articles, mActivity);
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new
                DividerItemDecoration(mActivity,LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapterClass);
        adapterClass.notifyDataSetChanged();
    }

    @Override
    public void deleteFavorite(Article articleModels) {

    }

    @Override
    public void editFavorite(Article articleModels) {

    }

    class MyHolder extends RecyclerView.ViewHolder{
        public ImageView mArticleImage;
        public TextView mTitle;
        public Button mDeleteArticle;
        public Button mEditArticle;

        public MyHolder(View itemView) {
            super(itemView);
            mArticleImage=(ImageView)itemView.findViewById(R.id.imArticle);
            mTitle =(TextView)itemView.findViewById(R.id.mArticleDesc);
            mDeleteArticle =(Button)itemView.findViewById(R.id.btnDeleteArticle);
            mEditArticle=(Button)itemView.findViewById(R.id.btnEditArticle);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_my_article,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final Article article = mArticles.get(position);

        //holder.ImageView.setText(addressSearchModel.getName());
        holder.mTitle.setText(article.getTitle());
        holder.mEditArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(article.getCategoryName(), article.getDescription());

                mArticlePresenter=new MyArticlePresenter(MyArticleAdapter.this);
                //do the rest of story here<call presenter delete>
            }
        });
        holder.mDeleteArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskQuestion();
                mArticlePresenter=new MyArticlePresenter(MyArticleAdapter.this);
                //do the rest of story here<call presenter delete>
            }
        });
    }
    //open up a Dialog and filled the EDITTEXT to set update
    public  void editDialog(String title, String description){
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_edit_article);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.draw_all_round_corner);
        // set the custom dialog components - text, image and draw_button
        EditText etTitle=(EditText)dialog.findViewById(R.id.article_title_edittext);
        EditText etDescription=(EditText)dialog.findViewById(R.id.et_description);

        etTitle.setText(title);
        etDescription.setText(description);

        TextView dialogButton = (TextView) dialog.findViewById(R.id.dialogOK);
        // if draw_button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
// custom dialog

    }
    private boolean state=true;
    public  boolean AskQuestion(){

        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_ask_v1);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.draw_radius_nocolor);
        int width = (int)(mActivity.getResources().getDisplayMetrics().widthPixels*0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnno);
        // if draw_button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=false;
                dialog.dismiss();
            }
        });
        dialog.show();
// custom dialog
        return state;
    }
    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}

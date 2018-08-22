package com.funnyApp.view.fragment.profile;

import com.funnyApp.data.model.Article;

import java.util.ArrayList;

/**
 * Created by hussein on 6/7/17.
 */

public class MyArticlePresenter {

    View mView;

    ArrayList<Article> articles;
    public MyArticlePresenter(View view){
        this.mView =view;
        articles =new ArrayList<>();
    }

    public void myArticlePresenter()
    {
        articles = preparData();
        mView.updateArticleRecycler(this.articles);
    }
    //use this method to delete data and update data

    private ArrayList<Article> deleteData() {
        ArrayList<Article> articleList=new ArrayList<>();;
        Article model=new Article();
        model.setId("1232131232");
        model.setTitle("asdasdasdasdsad");
        model.setDescription("asdasdasdas asasdasdsaasas  assdsad");
        model.setCategoryId("23123123123444");

        articleList.add(model);
        model=new Article();
        model.setId("1232131232");
        model.setTitle("asdasdasdasdsad");
        model.setDescription("asdasdasdas asasdasdsaasas  assdsad");
        model.setCategoryId("23123123123444");

        return articleList;
    }
    //call network call using this method to update recyclerview
    private ArrayList<Article> preparData() {
        ArrayList<Article> articleList=new ArrayList<>();;
        Article model=new Article();
        model.setId("1232131232");
        model.setTitle("asdasdasdasdsad");
        model.setDescription("asdasdasdas asasdasdsaasas  assdsad");
        model.setCategoryId("23123123123444");

        articleList.add(model);
        model=new Article();
        model.setId("1232131232");
        model.setTitle("asdasdasdasdsad");
        model.setDescription("asdasdasdas asasdasdsaasas  assdsad");
        model.setCategoryId("23123123123444");


        articleList.add(model);
        model=new Article();
        model.setId("1232131232");
        model.setTitle("asdasdasdasdsad");
        model.setDescription("asdasdasdas asasdasdsaasas  assdsad");
        model.setCategoryId("23123123123444");


        return articleList;
    }
    public interface  View
    {
        void updateArticleRecycler(ArrayList<Article> articles);

        //i get String as id because of UUID type format in ruby on rails
        void deleteFavorite(Article articleModels);
        void editFavorite(Article articleModels);
    }
}

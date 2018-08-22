package com.funnyApp.view.fragment;


import com.funnyApp.data.model.Favorite;

import java.util.ArrayList;

/**
 * Created by hussein on 6/6/17.
 * use this class to make networkCall Catch data and so on
 */

public class FavoritePresenter {

    View mView;

    ArrayList<Favorite> favorites;
    public FavoritePresenter(View view){
        this.mView =view;
        favorites =new ArrayList<>();
    }

    public void getFavoriteModel()
    {
        favorites =PreparData();
        mView.updateFavoriteRecycler(this.favorites);
    }
    /**
     *   do the network call and delete favorite and then do another network call to update it.
     *      The better ways is to catch data and sort it base on update field and then send update field
     *   to ruby part and in ruby take the rest of field base on update_at field (better performance)
     */
    public void deleteFavorite(Favorite favoriteId){
        favorites =deleteData();
        mView.deleteFavorite(this.favorites);
        mView.updateFavoriteRecycler(favorites);
    }
    //dummy data
    private ArrayList<Favorite> deleteData() {
        ArrayList<Favorite> lst_SearchResult=new ArrayList<>();;
        Favorite model=new Favorite();
        model.setId("234124234");
        model.setArticleId("23411124234");
        model.setArticleTitle("asdkdslfkdsklf");

        lst_SearchResult.add(model);
        model=new Favorite();
        model.setId("234124234");
        model.setArticleId("23411124234");
        model.setArticleTitle("asdkdslfkdsklf");

        return lst_SearchResult;
    }
    //call network call using this method to update recyclerview
    private ArrayList<Favorite> PreparData() {
        ArrayList<Favorite> lst_SearchResult=new ArrayList<>();;
        Favorite model=new Favorite();
        model.setId("23421234");
        model.setArticleId("232124234");
        model.setArticleTitle("asdsdfdfkdsklf");

        lst_SearchResult.add(model);
        model=new Favorite();
        model.setId("234124234");
        model.setArticleId("23411124234");
        model.setArticleTitle("asdfdddklf");


        model=new Favorite();
        model.setId("234124234");
        model.setArticleId("23423324234");
        model.setArticleTitle("asdkdaaaadsklf");
        lst_SearchResult.add(model);


        return lst_SearchResult;
    }
    public interface  View
    {
        void updateFavoriteRecycler(ArrayList<Favorite> favorites);

        //i get String as id because of UUID type format in ruby on rails
        void deleteFavorite(ArrayList<Favorite> favorites);
    }
}

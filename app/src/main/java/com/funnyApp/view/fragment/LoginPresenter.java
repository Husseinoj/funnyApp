package com.funnyApp.view.fragment;


import android.util.Log;

import com.funnyApp.data.model.Profile;
import com.funnyApp.data.remote.ApiUtils;
import com.funnyApp.data.remote.appService;

import java.util.HashMap;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by root on 6/10/17.
 */

public class LoginPresenter {
    View mView;
    private int mErrorCode;

    public LoginPresenter(View view){
        this.mView =view;
    }
    public void login(String userName,String password){
        Observer<Response<HashMap<String,String>>> myObserver= new Observer<Response<HashMap<String, String>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<HashMap<String, String>> loginResult) {
                mErrorCode =loginResult.code();
                switch (loginResult.code()){
                    case 400:
                        mView.errorResult(404);
                        break;
                    case 401:
                        mView.errorResult(401);
                        break;
                    case 403:
                        mView.errorResult(403);
                        break;
                    case 404:
                        mView.errorResult(404);
                        break;
                    case 500:
                        mView.errorResult(500);
                        break;
                    case 201:
                    case 200:
                        String Token = null;
                        if (loginResult.body().get("access_token")!=null)
                            Token=loginResult.body().get("access_token");
                        mView.successResult(Token);
                        break;

                    default:
                        mView.errorResult(-1);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("ErrorMessage",e.getMessage());
                mView.errorResult(mErrorCode);
            }

            @Override
            public void onComplete() {

            }
        };

        //setting api input parameter
        HashMap<String,String> loginParameter=new HashMap<>();
        loginParameter.put("username",userName);
        loginParameter.put("password",password);
        loginParameter.put("grant_type","password");

        appService service;
        service= ApiUtils.getAppService();
        service.login(loginParameter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);
    }
    public void sendNewPassword(String userName){

        Observer<Response<HashMap<String,String>>> myObserver= new Observer<Response<HashMap<String, String>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<HashMap<String, String>> passwordResult) {
                mErrorCode =passwordResult.code();
                switch (mErrorCode){
                    case 400:
                        mView.errorResult(400);
                        break;
                    case 401:
                        mView.errorResult(401);
                        break;
                    case 403:
                        mView.errorResult(403);
                        break;
                    case 404:
                        mView.errorResult(404);
                        break;
                    case 500:
                        mView.errorResult(500);
                        break;
                    case 201:

                        String password = null;
                        if (passwordResult.body().get("password")!=null)
                            password=passwordResult.body().get("password");
                        mView.passwordSuccessResult(password);
                        break;

                    default:
                        mView.errorResult(-1);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("ErrorMessage",e.getMessage());
                mView.errorResult(mErrorCode);
            }

            @Override
            public void onComplete() {

            }
        };

        //setting api input parameter
        HashMap<String,String> passwordParameter=new HashMap<>();
        passwordParameter.put("username",userName);

        //subscribe Observer 
        appService service;
        service= ApiUtils.getAppService();
        service.sendNewPassword(passwordParameter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);
    }
    public void getProfile(String token){
        Observer<Response<Profile>> myObserver= new Observer<Response<Profile>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<Profile> profileResponse) {
                mErrorCode =profileResponse.code();
                switch (profileResponse.code()){
                    case 400:
                        mView.errorResult(404);
                        break;
                    case 401:
                        mView.errorResult(401);
                        break;
                    case 403:
                        mView.errorResult(403);
                        break;
                    case 404:
                        mView.errorResult(404);
                        break;
                    case 500:
                        mView.errorResult(500);
                        break;
                    case 201:
                    case 200:

                        mView.getUserProfile(profileResponse.body());
                        break;

                    default:
                        mView.errorResult(-1);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("ErrorMessage",e.getMessage());
                mView.errorResult(mErrorCode);
            }

            @Override
            public void onComplete() {

            }
        };


        appService service;
        service= ApiUtils.getAppService();
        service.myProfile(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);

    }
    public interface View{
        void errorResult(int errorCode);
        void successResult(String token);

        void passwordSuccessResult(String password);
        void getUserProfile(Profile profile);
    }
}

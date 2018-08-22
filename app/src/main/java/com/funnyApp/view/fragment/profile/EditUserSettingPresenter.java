package com.funnyApp.view.fragment.profile;

import android.util.Log;


import com.funnyApp.data.model.Profile;
import com.funnyApp.data.model.User;
import com.funnyApp.data.remote.ApiUtils;
import com.funnyApp.data.remote.appService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by root on 6/12/17.
 */

public class EditUserSettingPresenter {
    View mView;
    private int mErrorCode;
    public  EditUserSettingPresenter(View view){
        mView=view;
    }

    public void updateProfile(String token,Profile profile){

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

                        mView.updateProfile(profileResponse.body());
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
        service.editProfile(token,profile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);

    }
    public void UpdatePassword(String token,User user){

        Observer<Response<User>> myObserver= new Observer<Response<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<User> userResponse) {
                mErrorCode =userResponse.code();
                switch (mErrorCode){
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

                        mView.UpdatePassword();

                        break;

                    default:
                        mView.errorResult(-1);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };



        appService service;
        service= ApiUtils.getAppService();
        service.sendNewPassword(token,user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);

    }
    public void UpdateImage(String token,Profile profile){

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
                        mView.UpdateImage();
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
        service.editProfile(token,profile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);
    }
    public interface View{
        void updateProfile(Profile profile);
        void UpdatePassword();
        void UpdateImage();
        void errorResult(int errorCode);
    }
}

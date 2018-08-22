package com.funnyApp.view.fragment;

import android.util.Log;


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

public class RegMobPresenter {

    private View mView;
    private int mErrorCode;

    public RegMobPresenter(View view){
        this.mView=view;

    }
    public void activateAccount(HashMap<String,String> activateUser){
        Observer<Response<HashMap<String,String>>> myObserver=new Observer<Response<HashMap<String, String>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Response<HashMap<String, String>> hashMapResponse) {

                mErrorCode =hashMapResponse.code();
                switch (mErrorCode){
                    case 400:
                        mView.activateError(400);
                        break;
                    case 401:
                        mView.activateError(401);
                        break;
                    case 403:
                        mView.activateError(403);
                        break;
                    case 404:
                        mView.activateError(404);
                        break;
                    case 500:
                        mView.activateError(500);
                        break;
                    case 405:
                        mView.activateError(405);
                        break;
                    case 200:
                        mView.activateError(200);
                        break;
                    case 201:

                        mView.successResult();

                        break;

                    default:
                        mView.activateError(-1);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("ErrorMessage",e.getMessage());
                mView.activateError(mErrorCode);

            }

            @Override
            public void onComplete() {



            }
        };
        appService service;
        service= ApiUtils.getAppService();
        service.activateAccount(activateUser)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);
    }
    public interface View {
        void activateError(int errorCode);
        void successResult();
    }
}

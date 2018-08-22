package com.funnyApp.view.fragment;

import android.util.Log;


import com.funnyApp.data.model.Register;
import com.funnyApp.data.remote.ApiUtils;
import com.funnyApp.data.remote.appService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by root on 6/9/17.
 */

public class RegisterPresenter {

    private int mErrorCode;


    private View mView;
   // Register register;

    public RegisterPresenter(View view){
        this.mView=view;

    }

        public void registerUser(final Register register){

            Observer<Response<Register>> myObserver= new Observer<Response<Register>>() {

                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Response<Register> registerResponse) {
                    mErrorCode =registerResponse.code();
                    switch (mErrorCode){
                        case 400:
                            mView.registerError(400);
                            break;
                        case 401:
                            mView.registerError(401);
                            break;
                        case 403:
                            mView.registerError(403);
                            break;
                        case 404:
                            mView.registerError(404);
                            break;
                        case 500:
                            mView.registerError(500);
                            break;
                        case 201:

                            mView.successResult(registerResponse.body().getSmsCode(),
                                    registerResponse.body().getUser().getUsername());

                            break;

                        default:
                            mView.registerError(-1);
                    }



                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.d("ErrorMessage",e.getMessage());
                    mView.registerError(mErrorCode);

                }

                @Override
                public void onComplete() {


                }
            };
            //maing network call
            appService service;
            service= ApiUtils.getAppService();
            service.createUser(register)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(myObserver);

        }

    public interface View {
        void registerError(int errorCode);
        void successResult(String smsCode,String userName);
    }
}
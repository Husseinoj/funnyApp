package com.funnyApp.view.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.funnyApp.R;
import com.funnyApp.data.model.Profile;
import com.funnyApp.data.model.User;
import com.funnyApp.helper.Tags;
import com.funnyApp.helper.UIHelper;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginPresenter.View{
    @BindView(R.id.inProccess)
    ProgressBar mProgressBar;

    @BindView(R.id.etUserName)
    EditText mUserName;

    @BindView(R.id.etPassword)
    EditText mPassword;

    LoginPresenter mLoginPresenter;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        UIHelper.unLockScreen(getActivity());
        mLoginPresenter=new LoginPresenter(this);
        return view;
    }


    @OnClick(R.id.btnRetrievePass)
    public void btnRetrievePass_OnClick(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_retrieve_pass);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.draw_all_round_corner);

        TextView dialogButton = (TextView) dialog.findViewById(R.id.btnClose);
        Button btnSendNewPass=(Button)dialog.findViewById(R.id.btnSendNewPass);
        btnSendNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edUsername = (EditText) dialog.findViewById(R.id.etPhone);
                UIHelper.lockScreen(getActivity());
                mProgressBar.setVisibility(View.VISIBLE);
                mLoginPresenter.sendNewPassword(edUsername.getText().toString());
                dialog.dismiss();
            }
        });
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
    @OnClick(R.id.btn_login)
    public void btnLogin_onClick(){
        mProgressBar.setVisibility(View.VISIBLE);
        UIHelper.lockScreen(getActivity());
        mLoginPresenter.login(mUserName.getText().toString(),mPassword.getText().toString());

    }

    @OnClick(R.id.btnRegister)
    public void btnRegister_OnClick(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.addToBackStack("back");
        transaction.replace(R.id.root_profile_fragment,new RegisterFragment());
        transaction.commit();
    }

    @Override
    public void errorResult(int errorCode) {
        mProgressBar.setVisibility(View.GONE);
        UIHelper.unLockScreen(getActivity());
        switch (errorCode){
            case 401:
                UIHelper.simpleDialogBox("شما مجاز به انجام این عمل نمی باشید.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 404:
                UIHelper.simpleDialogBox("سرور در دسترس نمی باشد",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 500:
                UIHelper.simpleDialogBox("سرور موقتا در دسترس نمی باشد.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            default:
                UIHelper.simpleDialogBox("خطایی ناشناخته، لطفا با پشتیبانی تماس بگیرید.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
        }
    }

    @Override
    public void successResult(String token) {

        //Save token to SharedPreferences to be accessed quickly whenever we want
        SharedPreferences preferences=getActivity().getSharedPreferences(Tags.PRERFERENCES_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Tags.PRERFERENCES_SYSTEM_TOKEN,token);
        editor.putString(Tags.PRERFERENCES_PROFILE,token);

        editor.commit();

        mLoginPresenter.getProfile(token);

    }

    @Override
    public void passwordSuccessResult(String password) {
        mProgressBar.setVisibility(View.GONE);
        UIHelper.unLockScreen(getActivity());
        //Set Vibration just for fun!!
        Vibrator vibrator=(Vibrator)this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        UIHelper.simpleDialogBox("پسورد شما :"+password,getActivity(),R.layout.dialog_ok_box,"نخند اس ام اس امد");
    }

    @Override
    public void getUserProfile(Profile profile) {
        mProgressBar.setVisibility(View.GONE);

        // Store the profile setting to Shared preferences as a json to quick access
        SharedPreferences preferences=getActivity().getSharedPreferences(Tags.PRERFERENCES_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(profile);
        editor.putString(Tags.PRERFERENCES_PROFILE,json);
        editor.commit();

        //save user
        User user=new User();
        user.setUsername(mUserName.getText().toString());
        user.setPassword(mPassword.getText().toString());
        user.setPasswordConfirmation(mPassword.getText().toString());
        json=gson.toJson(user);
        editor.putString(Tags.PRERFERENCES_USER,json);
        editor.commit();

        //unlock activity and move to profile
        UIHelper.unLockScreen(getActivity());
        //Move to Profile Fragment
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_profile_fragment,new ProfileFragment());
        transaction.commit();
    }


}

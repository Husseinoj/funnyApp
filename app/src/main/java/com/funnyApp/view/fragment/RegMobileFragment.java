package com.funnyApp.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.funnyApp.R;
import com.funnyApp.data.model.Message;
import com.funnyApp.helper.Helper;
import com.funnyApp.helper.UIHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegMobileFragment extends Fragment implements RegMobPresenter.View {

    RegMobPresenter mRegMobPresenter;
    String mSmsCode,mUserName;

    public static RegMobileFragment setArgument(Bundle b){

        RegMobileFragment regMobileFragment=new RegMobileFragment();
        regMobileFragment.setArguments(b);
        return regMobileFragment;
    }

    @BindView(R.id.ed1)
    EditText mFirstValue;

    @BindView(R.id.ed2)
    EditText mSecondValue;

    @BindView(R.id.ed3)
    EditText mThirdValue;

    @BindView(R.id.ed4)
    EditText mFourthValue;
    @BindView(R.id.inProccess)
    ProgressBar mProgressBar;

    public RegMobileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg_mobile, container, false);


        ButterKnife.bind(this, view);
        UIHelper.unLockScreen(getActivity());
        EventBus eventBus=EventBus.getDefault();
        eventBus.post(new Message("فعالسازی"));

        if (getArguments() != null) {
             mSmsCode = getArguments().getString("smsCode");
             mUserName = getArguments().getString("userName");

            String[] mSmsArray = new String[0];
            if (mSmsCode != null) {
                mSmsArray = mSmsCode.split("");
            }

            //set the Sms message in EditText
            mFirstValue.setText(mSmsArray[1]);
            mSecondValue.setText(mSmsArray[2]);
            mThirdValue.setText(mSmsArray[3]);
            mFourthValue.setText(mSmsArray[4]);

            //Set Vibration just for fun!!
            Vibrator vibrator=(Vibrator)this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
            UIHelper.simpleDialogBox("مثلا وضعمون خوبه براتون پیامک ارسال کردیم!!",getActivity(),R.layout.dialog_ok_box,"پیامک دریافت شد");
            mRegMobPresenter=new RegMobPresenter(this);

        }
        return view;
    }

    @Override
    public void activateError(int errorCode) {
        switch (errorCode){
            case 401:
                UIHelper.simpleDialogBox("شما مجاز به انجام این عمل نمی باشید.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 403:
                UIHelper.simpleDialogBox("کد پیامک معتبر نمی باشد.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 404:
                UIHelper.simpleDialogBox("سرور در دسترس نمی باشد",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 200:
                UIHelper.simpleDialogBox("حساب شما قبلا فعال شده است.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 500:
                UIHelper.simpleDialogBox("سرور موقتا در دسترس نمی باشد.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            default:
                UIHelper.simpleDialogBox("خطایی ناشناخته، لطفا با پشتیبانی تماس بگیرید.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
        }
    }

    @Override
    public void successResult() {

        mProgressBar.setVisibility(View.GONE);

        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        Helper.clearFragmentStack(getActivity());
        transaction.replace(R.id.root_profile_fragment,new LoginFragment());
        transaction.commit();
    }

    @OnClick(R.id.btnActivateAccount)
    public void btnActivateAccount_OnClick(){
        HashMap<String,String> userActivation=new HashMap<>();
        userActivation.put("username",mUserName);
        userActivation.put("sms_code",mSmsCode);
        UIHelper.lockScreen(getActivity());
        mProgressBar.setVisibility(View.VISIBLE);
        mRegMobPresenter.activateAccount(userActivation);
    }
}

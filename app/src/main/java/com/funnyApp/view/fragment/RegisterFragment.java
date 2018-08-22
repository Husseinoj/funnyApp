package com.funnyApp.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.funnyApp.R;
import com.funnyApp.data.model.Message;
import com.funnyApp.data.model.Profile;
import com.funnyApp.data.model.Register;
import com.funnyApp.data.model.User;
import com.funnyApp.helper.UIHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment  implements RegisterPresenter.View {

    @BindView(R.id.tvName) EditText mName;
    @BindView(R.id.etlastname) EditText mLastName;
    @BindView(R.id.etphone_number) EditText mPhoneNumber;
    @BindView(R.id.etmeliCode) EditText mNationalCode;
    @BindView(R.id.etpassword) EditText mPassword;
    @BindView(R.id.etrepeatpass) EditText mRepeatPassword;
    @BindView(R.id.radioMale) RadioButton mMale;
    @BindView(R.id.inProccess) ProgressBar mProgressBar;
    RegisterPresenter mRegisterPresenter;
    SegmentedGroup mGender;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this,view);
        EventBus eventBus=EventBus.getDefault();
        eventBus.post(new Message("عضویت در ایران مدیوم"));
        //Initial the male and Female draw_button Color
        genderInit(view);
        //Init draw_button

        mRegisterPresenter=new RegisterPresenter(this);

        return view;
    }
    private boolean checkingProccess() {
        //Checking all registration field
        if (!checkEmptyList())
            return false;
        if (!UIHelper.checktextSize(getActivity(), R.id.etrepeatpass, "تکرار کلمه عبور نمی تواند کمتر از ۶ رقم باشد.", R.layout.dialog_ok_box)
                && !UIHelper.checktextSize(getActivity(), R.id.etpassword, "کلمه عبور نمی تواند کمتر از ۶ رقم باشد.", R.layout.dialog_ok_box))
            return false;

        if(!UIHelper.compareTwoEd(getActivity(),R.id.etrepeatpass,
                R.id.etpassword,"کلمه عبور و تکرار آن برابر نمی باشد.",R.layout.dialog_ok_box,null))
            return false;

        return true;
    }
    //setting color and style to Gender Switch
    private void genderInit(View view) {
        mGender = (SegmentedGroup) view.findViewById(R.id.gender);
        mGender.setTintColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mGender.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
        mGender.check(R.id.radioMale);
    }
    //its better to do it in presenter!!!:)
    private boolean checkEmptyList(){
        int returnValue=0;
        ArrayList<Integer> controls=new ArrayList<>();
        controls.add(R.id.tvName);
        controls.add(R.id.etlastname);
        controls.add(R.id.etphone_number);
        controls.add(R.id.etmeliCode);
        controls.add(R.id.etpassword);
        controls.add(R.id.etrepeatpass);
        returnValue= UIHelper.checkEmptyEd(controls,getActivity());

        if( returnValue==0)
            return true;
        else {
            switch (returnValue){
                case R.id.tvName:
                    UIHelper.simpleDialogBox("لطفا نام را وارد کنید.",getActivity(),R.layout.dialog_ok_box,"");
                    return false;
                case R.id.etlastname:
                    UIHelper.simpleDialogBox("لطفا نام خانوادگی را وارد کنید.",getActivity(),R.layout.dialog_ok_box,"");
                    return false;
                case R.id.etphone_number:
                    UIHelper.simpleDialogBox("لطفا شماره همراه خود را وارد کنید. ",getActivity(),R.layout.dialog_ok_box,"");
                    return false;
                case R.id.etmeliCode:
                    UIHelper.simpleDialogBox("لطفا شماره ملی خود را وارد کنید",getActivity(),R.layout.dialog_ok_box,"");
                    return false;
                case R.id.etpassword:
                    UIHelper.simpleDialogBox("لطفا کلمه عبور خود را وارد کنید.",getActivity(),R.layout.dialog_ok_box,"");
                    return false;
                case R.id.etrepeatpass:
                    UIHelper.simpleDialogBox("لطفا تکرار کلمه عبور خود را وارد کنید.",getActivity(),R.layout.dialog_ok_box,"");
                    return false;

            }
            return true;
        }
    }

   @OnClick(R.id.btnRegister)
    public void btnRegister_OnClick(){

       if (!checkingProccess())
           return;

       Register register=new Register();
       User user=new User();
      //setting data to register in database
       Profile profile=new Profile();
       profile.setFirstName(mName.getText().toString());
       profile.setLastName(mLastName.getText().toString());
       profile.setNationalCode(mNationalCode.getText().toString());
       profile.setGender(mMale.isChecked()?true:false);

       user.setUsername(mPhoneNumber.getText().toString());
       user.setPassword(mPassword.getText().toString());
       user.setPasswordConfirmation(mRepeatPassword.getText().toString());
       user.setUserType("profile");

       register.setProfile(profile);
       register.setUser(user);


       mProgressBar.setVisibility(View.VISIBLE);
       UIHelper.lockScreen(getActivity());
        //in presenter call API and ...
       mRegisterPresenter.registerUser(register);
   }



    @Override
    public void registerError(int errorCode) {
        mProgressBar.setVisibility(View.GONE);
        UIHelper.unLockScreen(getActivity());
        switch (errorCode){
            case 401:
                UIHelper.simpleDialogBox("شما مجاز به انجام این عمل نمی باشید.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 404:
                UIHelper.simpleDialogBox("سرور در دسترس نمی باشد",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 405:
                UIHelper.simpleDialogBox("شما قبلا در سامانه ثبت نام نموده اید.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            case 500:
                UIHelper.simpleDialogBox("سرور موقتا در دسترس نمی باشد.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
                break;
            default:
                UIHelper.simpleDialogBox("خطایی ناشناخته، لطفا با پشتیبانی تماس بگیرید.",getActivity(),R.layout.dialog_ok_box,errorCode+"خطای ");
        }
    }

    @Override
    public void successResult(String smsCode, String userName) {
        //set Fragment Transition to automatically go to sms Fragment when it is successful
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        Bundle bdlSmsCode=new Bundle();
        bdlSmsCode.putString("smsCode",smsCode);
        bdlSmsCode.putString("userName",userName);
        RegMobileFragment mobileFragment=RegMobileFragment.setArgument(bdlSmsCode);
        transaction.addToBackStack("back");
        transaction.replace(R.id.root_profile_fragment,mobileFragment);
        transaction.commit();
    }


}

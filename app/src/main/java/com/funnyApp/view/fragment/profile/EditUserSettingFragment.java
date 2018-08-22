package com.funnyApp.view.fragment.profile;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funnyApp.R;
import com.funnyApp.data.model.Message;
import com.funnyApp.data.model.Profile;
import com.funnyApp.data.model.User;
import com.funnyApp.helper.ImagePicker;
import com.funnyApp.helper.Tags;
import com.funnyApp.helper.UIHelper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserSettingFragment extends Fragment implements EditUserSettingPresenter.View{

    @BindView(R.id.imgPic) ImageView mImagePic;
    @BindView(R.id.rlProfileImage) RelativeLayout mrlProfileImage;
    @BindView(R.id.tvName) TextView mName;
    @BindView(R.id.tvLastName) TextView mLastName;
    @BindView(R.id.tv_PhoneNumber) TextView mPhoneNumber;
    @BindView(R.id.edNationalCode) TextView mNationalCode;
    @BindView(R.id.tvGender) TextView mGender;
    @BindView(R.id.inProccess) ProgressBar mProgressBar;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_ID = 234;
    private static final int CAMERA_REQUEST = 1888;
    final int PIC_CROP = 2;
    private Uri mPicUri;
    Context mContext;

    EditUserSettingPresenter mEditUserPresenter;
    public EditUserSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_user_setting, container, false);

        ButterKnife.bind(this,view);
        EventBus eventBus=EventBus.getDefault();
        eventBus.post(new Message("تنظیمات کاربری"));
        ButterKnife.bind(this,view);
        mContext = getActivity().getApplicationContext();
        mEditUserPresenter=new EditUserSettingPresenter(this);
        settingInfo();



        /*UIHelper.setImageViewVisibility(getActivity(), R.id.editDialog, "gone",null);*/

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();

        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 100 * 28);
        params4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mrlProfileImage.setLayoutParams(params4);

        return view;
    }

    private void settingInfo() {
        Profile myProfile = getProfile();

        //fill the layout fields
        mName.setText(myProfile.getFirstName() );
        mLastName.setText(myProfile.getLastName());
        mGender.setText(myProfile.getGender()?"مرد":"زن");
        mPhoneNumber.setText(myProfile.getUserName());
        mNationalCode.setText(myProfile.getNationalCode());
        //load image from server
        Picasso.with(getActivity())
                .load(Tags.Url+myProfile.getAvatarUrl())
                .into(mImagePic);
    }

    private Profile getProfile() {
        //retrive data from Share Prefrences and sets
        SharedPreferences getInfoPreferences=getActivity().getSharedPreferences(Tags.PRERFERENCES_SETTINGS,
                Context.MODE_PRIVATE);
        String jsonProfile=getInfoPreferences.getString(Tags.PRERFERENCES_PROFILE,"");
        // convert from json to Profile object
        Gson gson=new Gson();
        return gson.fromJson(jsonProfile, Profile.class);
    }
    private User getUser() {
        //retrive data from Share Prefrences and sets
        SharedPreferences getInfoPreferences=getActivity().getSharedPreferences(Tags.PRERFERENCES_SETTINGS,
                Context.MODE_PRIVATE);
        String jsonUser=getInfoPreferences.getString(Tags.PRERFERENCES_USER,"");
        // convert from json to Profile object
        Gson gson=new Gson();
        return gson.fromJson(jsonUser, User.class);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Profile myProfile=getProfile();
        switch (requestCode) {

            case PICK_IMAGE_ID:

                mImagePic.setBackgroundColor(getResources().getColor(R.color.white));
                mPicUri = data.getData();
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                mImagePic.setImageBitmap(bitmap);
                UIHelper.lockScreen(getActivity());
                mProgressBar.setVisibility(View.VISIBLE);

                myProfile.setAvatarUrl(bitmap);
                mEditUserPresenter.UpdateImage(getToken(),myProfile);
                break;
            case PIC_CROP:
                try {
                    mImagePic.setBackgroundColor(getResources().getColor(R.color.white));
                    mPicUri = data.getData();
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");
                    mImagePic.setImageBitmap(thePic);
                    UIHelper.lockScreen(getActivity());
                    mProgressBar.setVisibility(View.VISIBLE);
                    myProfile.setAvatarUrl(thePic);
                    mEditUserPresenter.UpdateImage(getToken(),myProfile);

                } catch (RuntimeException e) {
                    Toast.makeText(getActivity(), e+",,You know me", Toast.LENGTH_LONG).show();
                }
                break;
            case CAMERA_REQUEST:
                if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mImagePic.setBackgroundColor(getResources().getColor(R.color.white));
                    mImagePic.setImageBitmap(photo);
                    UIHelper.lockScreen(getActivity());
                    mProgressBar.setVisibility(View.VISIBLE);
                    myProfile.setAvatarUrl(photo);
                    mEditUserPresenter.UpdateImage(getToken(),myProfile);

                }
                break;


            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
    private void selectImageOption() {
        final CharSequence[] items = {"گرفتن عکس", "انتخاب عکس از گالری", "لغو"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("انتخاب");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("گرفتن عکس")) {
                    checksCameraPermission();

                } else if (items[item].equals("انتخاب عکس از گالری")) {
                    try {
                        performCrop();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e + "", Toast.LENGTH_LONG).show();
                    }
                } else if (items[item].equals("لغو")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    //checking permition for android version 6
    public void checksCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("MyApp", "SDK >= 23");
            if (getActivity().checkSelfPermission(android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("MyApp", "Request permission");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_IMAGE_CAPTURE);

                if (! shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(),"شما باید اجازه دسترسی به دوربین را صادر کنید",Toast.LENGTH_LONG).show();
                }
            }
            else {
                Log.d("MyApp", "Permission granted: taking pic");
                takeImage();
            }
        }
        else {
            takeImage();
            Log.d("MyApp", "Android < 6.0");
        }
    }

    private void takeImage() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1888);
    }

    private void performCrop() {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(mPicUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);

        } catch (ActivityNotFoundException anfe) {
            //display an error message
            Toast.makeText(getActivity(), "Whoops - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();

            onPickImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onPickImage() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }


    @OnClick(R.id.btnChangePass)
    public void btnChangePass_OnClick(){
        changePassDialog();
    }
    @OnClick(R.id.btnEdite)
    public void btnEdite_OnClick(){
        editUserDialog();
    }
    @OnClick(R.id.btnChangePic)
    public void btnChangePic_OnClick(){
        selectImageOption();
    }
    @OnClick(R.id.llGetImage)
    public void llGetImage_OnClick(){
        selectImageOption();
    }


    private void editUserDialog() {

        final Dialog dlEditUser = new Dialog(getActivity());
        dlEditUser.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlEditUser.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlEditUser.setContentView(R.layout.dialog_edite_profilesetting);
        dlEditUser.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp4 = new WindowManager.LayoutParams();
        lp4.copyFrom(dlEditUser.getWindow().getAttributes());
        Display display = getActivity().getWindowManager().getDefaultDisplay();



        lp4.width = display.getWidth();
        lp4.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dlEditUser.getWindow().setAttributes(lp4);

        dlEditUser.show();
        final EditText etName= (EditText) dlEditUser.findViewById(R.id.etName);
        final EditText etLastName= (EditText) dlEditUser.findViewById(R.id.etFamily);
        final EditText etNationalCode= (EditText) dlEditUser.findViewById(R.id.edNationalCode);

        Button btnDelete=(Button)dlEditUser.findViewById(R.id.btnClose);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlEditUser.dismiss();
            }
        });

        Button btnRegister=(Button)dlEditUser.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile myProfile=new Profile();
                myProfile.setFirstName(etName.getText().toString());
                myProfile.setLastName(etLastName.getText().toString());
                myProfile.setNationalCode(etNationalCode.getText().toString());
                UIHelper.lockScreen(getActivity());
                mProgressBar.setVisibility(View.VISIBLE);
                mEditUserPresenter.updateProfile(getToken(),myProfile);

                dlEditUser.dismiss();
            }
        });
    }

    private void changePassDialog() {

        final Dialog dl4 = new Dialog(getActivity());
        dl4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dl4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dl4.setContentView(R.layout.dialog_edit_password);
        dl4.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp4 = new WindowManager.LayoutParams();
        lp4.copyFrom(dl4.getWindow().getAttributes());
        final Display display4 = getActivity().getWindowManager().getDefaultDisplay();
        int width4 = display4.getWidth();



        lp4.width = width4;
        lp4.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dl4.getWindow().setAttributes(lp4);

        dl4.show();
        Button btncancel=(Button)dl4.findViewById(R.id.btnClose);
        Button btnreg=(Button)dl4.findViewById(R.id.btnRegister);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl4.dismiss();
            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> controls=new ArrayList<Integer>();
                controls.add(R.id.etoldpass);
                controls.add(R.id.etNewPass);
                controls.add(R.id.etRepeatNewPass);

                if(!checkEmptyList(dl4)){

                    return;
                }
                if(!UIHelper.compareTwoEd(getActivity(),R.id.etNewPass,R.id.etRepeatNewPass,
                        R.id.tvErrorMessage,"رمز جدید و تکرار آن برابر نمی باشد.",dl4)) {

                    return;
                }
                if(!UIHelper.checktextSize(dl4,R.id.etNewPass,"رمز نمی تواند کمتر از ۶ رقم باشد.",R.id.tvErrorMessage
                )){

                    return;
                }
                User user=getUser();
                String edPassword=UIHelper.getEditText(dl4,R.id.etoldpass).toString();
                if (!edPassword.trim().equals(user.getPassword().trim())) {
                    UIHelper.setTvText(dl4, R.id.tvErrorMessage, "پسورد قدیمی شما اشتباه می باشد");
                    return;
                }


                mProgressBar.setVisibility(View.VISIBLE);



                user.setPassword(UIHelper.getEditText(dl4,R.id.etNewPass));
                user.setPasswordConfirmation(UIHelper.getEditText(dl4,R.id.etNewPass));

                mEditUserPresenter.UpdatePassword(getToken(),user);
                dl4.dismiss();
            }
        });
    }
    // get permition result in android version 6
    // a dialog shown to user and user decide what to do
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== 1 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getActivity(),"granted well",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(),"not granted",Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkEmptyList(Dialog dialog){
        int returnvalue=0;
        ArrayList<Integer> controls=new ArrayList<>();
        controls.add(R.id.etoldpass);
        controls.add(R.id.etNewPass);
        controls.add(R.id.etRepeatNewPass);

        returnvalue=UIHelper.checkEmptyEd(controls,dialog);
        if( returnvalue==0)
            return true;
        else {
            switch (returnvalue){
                case R.id.etoldpass:
                    UIHelper.setTvText(dialog,R.id.tvErrorMessage,"لطفا کلمه عبور قبلی را وارد کنید.");
                    return false;
                case R.id.etNewPass:
                    UIHelper.setTvText(dialog,R.id.tvErrorMessage,"لطفا کلمه ورود جدید را وارد کنید.");
                    return false;
                case R.id.etRepeatNewPass:
                    UIHelper.setTvText(dialog,R.id.tvErrorMessage,"لطفا تکرار کلمه ورود جدید را وارد کنید. ");
                    return false;

            }
            return true;
        }
    }

    @Override
    public void updateProfile(Profile profile) {
        UIHelper.simpleDialogBox("پروفایل شما با موفقیت بروز رسانی شد.",getActivity(),R.layout.dialog_ok_box,"پیام سیستم");

        Profile myCurrentProfile=getProfile();

        SharedPreferences profilePreferences=getActivity().getSharedPreferences(Tags.PRERFERENCES_SETTINGS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=profilePreferences.edit();
        Gson gson=new Gson();

        String myJsonProfile=gson.toJson(profile);
        editor.putString(Tags.PRERFERENCES_PROFILE,myJsonProfile);
        editor.apply();

        mName.setText(profile.getFirstName());
        mLastName.setText(profile.getLastName());
        mNationalCode.setText(profile.getNationalCode());
        mGender.setText(myCurrentProfile.getGender()?"مرد":"زن");
        mPhoneNumber.setText(myCurrentProfile.getUserName());

        UIHelper.unLockScreen(getActivity());
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void UpdatePassword() {
        mProgressBar.setVisibility(View.GONE);
        UIHelper.simpleDialogBox("پسورد شما با موفقیت عوض شد.",getActivity(),R.layout.dialog_ok_box,"پیام سیستم");
    }


    @Override
    public void UpdateImage() {
        UIHelper.unLockScreen(getActivity());
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void errorResult(int errorCode) {
        UIHelper.unLockScreen(getActivity());
        mProgressBar.setVisibility(View.GONE);
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
    public String getToken(){
        String token="";
        SharedPreferences getToken=getActivity().getSharedPreferences(Tags.PRERFERENCES_SETTINGS,Context.MODE_PRIVATE);
        token=getToken.getString(Tags.PRERFERENCES_SYSTEM_TOKEN,"");
        return token;
    }

}

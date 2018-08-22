package com.funnyApp.view.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funnyApp.R;
import com.funnyApp.data.model.Message;
import com.funnyApp.data.model.Profile;
import com.funnyApp.helper.Tags;
import com.funnyApp.view.fragment.profile.EditUserSettingFragment;
import com.funnyApp.view.fragment.profile.MyArticleFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.rlInfoBox)  RelativeLayout mInfoBox;
    @BindView(R.id.rlProfileImage) RelativeLayout mProfileImage;
    @BindView(R.id.rlRootProfileImage) RelativeLayout mRootProfileImage;
    @BindView(R.id.rlRootInfoBox) RelativeLayout mRootInfoBox;
    @BindView(R.id.rlInfoBoxTopBorder) RelativeLayout mInfoBoxTopBorder;
    @BindView(R.id.rlGenderTopBorder) RelativeLayout mGenderTopBorder;
    @BindView(R.id.rlNationalCodeBottomBorder) RelativeLayout mNationalCodeBottomBorder;
    @BindView(R.id.rlNameRow) RelativeLayout mNameRow;
    @BindView(R.id.rlGenderRow) RelativeLayout mGenderRow;
    @BindView(R.id.rlNationalCode) RelativeLayout mNationalCode;
    @BindView(R.id.rlCreateArticleImage) RelativeLayout mCreateArticleImage;
    @BindView(R.id.rlUserSettingsImage) RelativeLayout mUserSettingsImage;
    @BindView(R.id.rlValidArticleImage) RelativeLayout mValidArticleImage;
    @BindView(R.id.rlMyArticleImage) RelativeLayout mMyArticleImage;
    @BindView(R.id.rlAboutUsImage) RelativeLayout mAboutUsImage;
    @BindView(R.id.rlCategoryImage) RelativeLayout mCategoryImage;
    @BindView(R.id.rlExitImage) RelativeLayout mExitImage;
    @BindView(R.id.rlHelpImage) RelativeLayout mHelpImage;
    @BindView(R.id.tvName) TextView mTvName;
    @BindView(R.id.tvGender) TextView mTvGender;
    @BindView(R.id.edNationalCode) TextView mTvNationalCode;
    @BindView(R.id.imgPic) ImageView mImgProfileImage;




    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);

        EventBus eventBus=EventBus.getDefault();
        eventBus.post(new Message("حساب کاربری"));

        //retrive data from Share Prefrences and sets
        SharedPreferences getInfoPreferences=getActivity().getSharedPreferences(Tags.PRERFERENCES_SETTINGS,
                Context.MODE_PRIVATE);
        String jsonProfile=getInfoPreferences.getString(Tags.PRERFERENCES_PROFILE,"");

        // convert from json to Profile object
        Gson gson=new Gson();
        Profile myProfile=gson.fromJson(jsonProfile, Profile.class);

        //fill the layout fields
        mTvName.setText(myProfile.getFirstName() + " "+myProfile.getLastName());
        mTvGender.setText(myProfile.getGender()?"مرد":"زن");
        mTvNationalCode.setText(myProfile.getNationalCode());
        //load image from server
        Picasso.with(getActivity())
                .load(Tags.Url+myProfile.getAvatarUrl())
                .into(mImgProfileImage);
        //use to set ViewGroup(Mostly RelativeLayout) parameters to have appropraite view in different screen size
        setScreenLayoutParams();


        return view;
    }




    @OnClick(R.id.llUserSettings)
    public void userSettings_onClick(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.addToBackStack("back");
        transaction.replace(R.id.root_profile_fragment,new EditUserSettingFragment());
        transaction.commit();
    }
    @OnClick(R.id.btnMyArticle)
    public void btnMyArticle_OnClick(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.addToBackStack("back");
        transaction.replace(R.id.root_profile_fragment,new MyArticleFragment());
        transaction.commit();
    }
    @OnClick(R.id.btnVisibleArticle)
    public void btnVisibleArticle_OnClick(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.addToBackStack("back");
        transaction.replace(R.id.root_profile_fragment,new MyVisibleArticle());
        transaction.commit();
    }
    @OnClick(R.id.createArticle)
    public void createArticle_OnClick(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.addToBackStack("back");
        transaction.replace(R.id.root_profile_fragment,new CreateArticleFragment());
        transaction.commit();
    }
    @OnClick(R.id.llExit)
    public void logout(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_profile_fragment,new LoginFragment());
        transaction.commit();
    }
    private void setScreenLayoutParams() {
        //get screen width and height
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        //Set right side of InfoBox width base viewport screen size
        RelativeLayout.LayoutParams infoBoxParam = new RelativeLayout.LayoutParams(width / 40, RelativeLayout.LayoutParams.MATCH_PARENT);
        infoBoxParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mInfoBox.setLayoutParams(infoBoxParam);


        //set Image Profile PlaceHolder(RelativeLayout) width and height base on viewport with
        RelativeLayout.LayoutParams profileImageParams = new RelativeLayout.LayoutParams(width / 100 * 22, width / 100 * 29);
        profileImageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mProfileImage.setLayoutParams(profileImageParams);

        //Set Image Profile root parent width base on screen size.
        RelativeLayout.LayoutParams rootProfileImage = new RelativeLayout.LayoutParams(width / 11 * 4, RelativeLayout.LayoutParams.MATCH_PARENT);
        mRootProfileImage.setLayoutParams(rootProfileImage);

        //Set InfoBox(left side) root parent width and height(match parent)
        RelativeLayout.LayoutParams rootInfoBoxParams = new RelativeLayout.LayoutParams(width / 11 * 6, RelativeLayout.LayoutParams.MATCH_PARENT);
        rootInfoBoxParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRootInfoBox.setLayoutParams(rootInfoBoxParams);

        //set InfoBox top border height and align it
        RelativeLayout.LayoutParams InfoBoxTopBorderParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height / 90);
        InfoBoxTopBorderParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mInfoBoxTopBorder.setLayoutParams(InfoBoxTopBorderParams);

        //set Gender field top border to have appropriate height base on screen size
        RelativeLayout.LayoutParams genderTopBorderParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height / 500);
        genderTopBorderParams.addRule(RelativeLayout.BELOW, R.id.rlNameRow);
        mGenderTopBorder.setLayoutParams(genderTopBorderParams);

        //set national Code bottom border
        RelativeLayout.LayoutParams nationalCodeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height / 500);
        nationalCodeParams.addRule(RelativeLayout.BELOW, R.id.rlGenderRow);
        mNationalCodeBottomBorder.setLayoutParams(nationalCodeParams);


        //set Image Icon params
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(height / 129 * 10, height / 130 * 10);
        imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mCreateArticleImage.setLayoutParams(imageParams);
        mUserSettingsImage.setLayoutParams(imageParams);
        mValidArticleImage.setLayoutParams(imageParams);
        mMyArticleImage.setLayoutParams(imageParams);
        mAboutUsImage.setLayoutParams(imageParams);
        mCategoryImage.setLayoutParams(imageParams);
        mExitImage.setLayoutParams(imageParams);
        mHelpImage.setLayoutParams(imageParams);


        //set InfoBox field height and also align them to appropriate control base on screen size
        RelativeLayout.LayoutParams nameRowParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height / 100 * 6);
        nameRowParams.addRule(RelativeLayout.BELOW, R.id.rlInfoBoxTopBorder);
        nameRowParams.addRule(RelativeLayout.LEFT_OF, R.id.rlInfoBox);
        mNameRow.setLayoutParams(nameRowParams);

        RelativeLayout.LayoutParams genderRowParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height / 100 * 6);
        genderRowParams.addRule(RelativeLayout.BELOW, R.id.rlGenderTopBorder);
        genderRowParams.addRule(RelativeLayout.LEFT_OF, R.id.rlInfoBox);
        mGenderRow.setLayoutParams(genderRowParams);

        RelativeLayout.LayoutParams paramstxt2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height / 100 * 6);
        paramstxt2.addRule(RelativeLayout.BELOW, R.id.rlNationalCodeBottomBorder);
        paramstxt2.addRule(RelativeLayout.LEFT_OF, R.id.rlInfoBox);
        mNationalCode.setLayoutParams(paramstxt2);
    }
}

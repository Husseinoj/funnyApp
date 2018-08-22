package com.funnyApp.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.funnyApp.R;
import com.funnyApp.data.model.Message;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    @BindView(R.id.spCategoryHolder) LinearLayout spCategoryHolder;
    @BindView(R.id.btnSearch) Button btnSearch;
    @BindView(R.id.sp_category) SearchableSpinner spCategory;
    @BindView(R.id.category_switch) ToggleButton mCategorySwitch;
    @BindView(R.id.order_switch) ToggleButton mOrderSwitch;
    //hold the order and category state
    public static boolean mCategoryState =true, mOrderState =true;

    String[] mCategoryList = {"فیلتر بر اساس دسته بندی مطلب", "مولتی مدیا","سخت افزار","برنامه نویسی","شبکه","گرافیک","نرم افزار"};


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        EventBus setTitleEvent;
        setTitleEvent=EventBus.getDefault();
        setTitleEvent.post(new Message(" جست و جو"));

        spinnerConfig();


        return view;
    }

    private void spinnerConfig() {
        ArrayAdapter<String> adapteRregion = new ArrayAdapter<String>(getActivity(), R.layout.spineer_item, mCategoryList);
        adapteRregion.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);

        spCategory.setAdapter(adapteRregion);
        spCategory.setTitle("انتخاب");
        spCategory.setPositiveButton("خروج");
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }


    @OnClick(R.id.btnSearch)
    public void btnSearch_onClick(){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.addToBackStack("back");
        transaction.replace(R.id.root_search_fragment,new SearchResultFragment());
        transaction.commit();
    }


    @OnClick(R.id.llCategoryHolder)
    public void linearlayoutSwitch_OnClick(){

        categoryState();
    }
    @OnClick(R.id.category_switch)
    public void categorySwitch_OnClick(){
        categoryState();
    }
    @OnClick(R.id.order_switch)
    public void orderSwitch_OnClick(){
        orderState();
    }
    @OnClick(R.id.order_holder)
    public void llOrderSwitch_OnClick(){
        orderState();
    }

    //handle the order switch state
    private void orderState() {
        if (!spCategory.getSelectedItem().toString().equalsIgnoreCase("")) {
            if (mOrderState) {
                mOrderState =false;
                mOrderSwitch.setChecked(false);
                mOrderSwitch.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_search_toggled_off));
            } else {
                mOrderState =true;
                mOrderSwitch.setChecked(true);
                mOrderSwitch.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_search_toggled_on));
            }
        }else{
            Toast.makeText(getActivity(),"شما هیچ محله ای را انتخاب نکرده اید.", Toast.LENGTH_LONG).show();
        }
    }


    //this method handle the category switch
    private void categoryState() {
        if (!spCategory.getSelectedItem().toString().equalsIgnoreCase("")) {
            if (mCategoryState) {
                mCategoryState =false;
                mCategorySwitch.setChecked(false);
                mCategorySwitch.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_search_toggled_off));
                spCategoryHolder.setVisibility(View.VISIBLE);

            } else {

                spCategoryHolder.setVisibility(View.GONE);
                mCategoryState =true;
                mCategorySwitch.setChecked(true);
                mCategorySwitch.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_search_toggled_on));
            }
        }else{
            Toast.makeText(getActivity(),"شما هیچ محله ای را انتخاب نکرده اید.", Toast.LENGTH_LONG).show();
        }
    }
}

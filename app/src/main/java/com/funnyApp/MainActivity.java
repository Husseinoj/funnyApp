package com.funnyApp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.ViewGroup;

import android.support.design.widget.TabLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.funnyApp.adapter.ViewPagerAdapter;
import com.funnyApp.data.model.Message;
import com.funnyApp.helper.CustomViewPager;
import com.funnyApp.helper.Helper;
import com.funnyApp.view.fragment.root.KnowlageRoot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.headertitle) TextView mTitle;

    TabLayout mTabLayout;
    public LinearLayout linearLayout;

    ViewPager mViewPager;
    private int[] mTabIcons = {
            R.drawable.ic_tab_knowlage,
            R.drawable.ic_tab_favorit,
            R.drawable.ic_tab_search,
            R.drawable.ic_tab_user,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize View pager and set it up with tablayout
        tabCustomization();
        //initialize Tab layout and set it's textView and clear stack when press tab
        setupTabIcons();
        //bind Butter knife simple Dependency injection To make it easy to work with layout
        ButterKnife.bind(this);

        //Set Default Tab in Tablayout
        mTabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FF8035"), PorterDuff.Mode.SRC_IN);
        linearLayout = (LinearLayout) mTabLayout.getTabAt(0).getCustomView();
        ImageView firstTab = (ImageView) linearLayout.getChildAt(0);
        firstTab.setColorFilter(Color.parseColor("#FF8035"), PorterDuff.Mode.SRC_IN);
        mTitle.setText("دانستنی ها");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTitle(Message message){
        mTitle.setText(message.getTitle());
    }
    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // This method manage font in Caligraphy plugin which it need to set in All Activity that has fontPath attribute in xml layout
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    // clear fragment stack which it's filled
    /*private void clearFragmentStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }*/

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(mTabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(mTabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(mTabIcons[2]);
        mTabLayout.getTabAt(3).setIcon(mTabIcons[3]);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TabChanges(tab);

            }

            private void TabChanges(TabLayout.Tab tab) {

                linearLayout = (LinearLayout) tab.getCustomView();
                ImageView imgTab = (ImageView) linearLayout.getChildAt(0);
                TextView tabTitle = (TextView) linearLayout.getChildAt(1);
                tabTitle.setTextColor(Color.parseColor("#FF8035"));
                imgTab.setColorFilter(Color.parseColor("#FF8035"), PorterDuff.Mode.SRC_IN);

                switch (tab.getPosition()) {
                    case 0:
                        Helper.clearFragmentStack(MainActivity.this);
                        Log.d("Position", String.valueOf(tab.getPosition()));
                        mTitle.setText("دانستنی ها");
                        break;
                    case 1:
                        Helper.clearFragmentStack(MainActivity.this);
                        Log.d("Position", String.valueOf(tab.getPosition()));
                        mTitle.setText("مورد علاقه ها");
                        break;
                    case 2:
                        Helper.clearFragmentStack(MainActivity.this);
                        mTitle.setText("جست و جو");
                        Log.d("Position", String.valueOf(tab.getPosition()));
                        break;
                    case 3:
                        Helper.clearFragmentStack(MainActivity.this);
                        mTitle.setText("حساب کاربری");
                        Log.d("Position", String.valueOf(tab.getPosition()));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                linearLayout = (LinearLayout) tab.getCustomView();
                ImageView imgTab = (ImageView) linearLayout.getChildAt(0);
                TextView tabTitle = (TextView) linearLayout.getChildAt(1);
                tabTitle.setTextColor(Color.parseColor("#ffffff"));
                imgTab.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                linearLayout = (LinearLayout) tab.getCustomView();
                ImageView imgTab = (ImageView) linearLayout.getChildAt(0);
                TextView tabTitle = (TextView) linearLayout.getChildAt(1);
                tabTitle.setTextColor(Color.parseColor("#FF8035"));
                imgTab.setColorFilter(Color.parseColor("#FF8035"), PorterDuff.Mode.SRC_IN);

                switch (tab.getPosition()) {

                    case 0:
                        Helper.clearFragmentStack(MainActivity.this);
                        mTitle.setText("دانستنی ها");
                        Log.d("Position", String.valueOf(tab.getPosition()));

                        break;
                    case 1:
                        Helper.clearFragmentStack(MainActivity.this);
                        mTitle.setText("مورد علاقه ها");
                        Log.d("Position", String.valueOf(tab.getPosition()));
                        break;
                    case 2:
                        Helper.clearFragmentStack(MainActivity.this);
                        mTitle.setText("جست و جو");
                        Log.d("Position", String.valueOf(tab.getPosition()));

                        break;
                    case 3:
                        Helper.clearFragmentStack(MainActivity.this);
                        mTitle.setText("حساب کاربری");
                        Log.d("Position", String.valueOf(tab.getPosition()));
                        break;
                }
            }
        });


    }

    private void tabCustomization() {

        mViewPager = (CustomViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mTabLayout.setupWithViewPager(mViewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getApplicationContext(), mTabIcons);

        adapter.addFragment(new KnowlageRoot(), "دانستنی ها");
        adapter.addFragment(new FavoritFragment(), "مورد علاقه ها");
        adapter.addFragment(new SearchRoot(), "جست وجو");
        adapter.addFragment(new MainProfileFragment(), "پروفایل");

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(mTabLayout, i));
        }
        ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        Log.i("TabChild", String.valueOf(tabsCount));
    }

}



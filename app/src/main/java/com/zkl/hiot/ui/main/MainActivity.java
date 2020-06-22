package com.zkl.hiot.ui.main;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zkl.hiot.R;
import com.zkl.hiot.base.BaseActivity;
import com.zkl.hiot.http.HttpService;
import com.zkl.hiot.view.NoSlideViewPager;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    @Inject
    MainPresenter mainPresenter;
    private NoSlideViewPager viewPager;
    private RadioGroup main_radio_group;
    private RadioButton main_radio_message;
    private RadioButton main_radio_equipment;
    private RadioButton main_radio_scene;
    private RadioButton main_radio_mine;

    @Override
    protected MainPresenter createPresenter() {
//        mainPresenter = new MainPresenter();
        return mainPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.main_vp_select);
        main_radio_group = findViewById(R.id.main_radio_group);
        main_radio_message = findViewById(R.id.main_radio_message);
        main_radio_equipment = findViewById(R.id.main_radio_equipment);
        main_radio_scene = findViewById(R.id.main_radio_scene);
        main_radio_mine = findViewById(R.id.main_radio_mine);
        MainFragmentPagerAdapter pagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setSlidable(false);
        main_radio_message.setChecked(true);
        main_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_radio_message:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.main_radio_equipment:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.main_radio_scene:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.main_radio_mine:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void injectDependencies(){
        getActivityComponent().inject(this);
    }
}
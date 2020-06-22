package com.zkl.hiot.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zkl.hiot.App;
import com.zkl.hiot.injection.component.ActivityComponent;
import com.zkl.hiot.injection.component.ApplicationComponent;
import com.zkl.hiot.injection.component.DaggerActivityComponent;
import com.zkl.hiot.injection.module.ActivityModule;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private  P mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        mvpPresenter = createPresenter();
        if(mvpPresenter != null){
            mvpPresenter.setView((V)this);
        }
    }
    protected abstract void injectDependencies();

    protected  abstract  P createPresenter();

    private ActivityComponent mActivityComponent;
    public ActivityComponent getActivityComponent(){
        if(mActivityComponent == null){
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(getActivityModule())
                    .applicationComponent(getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    private ApplicationComponent getApplicationComponent(){
        return ((App)getApplication()).component();
    }
    private ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mvpPresenter != null){
            mvpPresenter.detachView();
        }
    }
}

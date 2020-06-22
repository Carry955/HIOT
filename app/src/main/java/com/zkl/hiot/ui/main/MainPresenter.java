package com.zkl.hiot.ui.main;

import android.app.Activity;

import com.zkl.hiot.base.BasePresenter;
import com.zkl.hiot.entity.LoginEntity;
import com.zkl.hiot.http.HttpResult;
import com.zkl.hiot.http.HttpResultFunc;
import com.zkl.hiot.http.HttpService;
import com.zkl.hiot.http.ProgressDialogSubscriber;
import com.zkl.hiot.http.UserPreferencesHelper;

import javax.inject.Inject;

import rx.Observable;


public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    HttpService service;
    @Inject
    Activity activity;
    @Inject
    UserPreferencesHelper preferencesHelper;

    @Inject
    public MainPresenter(){}
    public void login(final String username, final String password, String loginCode){
        Observable<LoginEntity> observable = service.login(username, password, loginCode)
                .map(new HttpResultFunc<LoginEntity>());
        toSubscribe(observable, new ProgressDialogSubscriber<LoginEntity>(activity){
            @Override
            public void onNext(LoginEntity loginEntity){
                getView().showToast(loginEntity.getUuid());
                preferencesHelper.putTokenValue(loginEntity.getTokenValue());
                preferencesHelper.putUuid(loginEntity.getUuid());
                preferencesHelper.putUserName(username);
                preferencesHelper.putPassword(password);
            }
        });
    }
}

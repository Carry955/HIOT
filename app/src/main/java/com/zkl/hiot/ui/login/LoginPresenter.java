package com.zkl.hiot.ui.login;

import android.app.Activity;
import android.text.TextUtils;

import com.zkl.hiot.base.BasePresenter;
import com.zkl.hiot.entity.LoginEntity;
import com.zkl.hiot.http.HttpResult;
import com.zkl.hiot.http.HttpService;
import com.zkl.hiot.http.ProgressDialogSubscriber;
import com.zkl.hiot.http.UserPreferencesHelper;

import javax.inject.Inject;

import rx.Observable;

public class LoginPresenter extends BasePresenter<LoginView> {
    @Inject
    HttpService service;
    @Inject
    Activity activity;
    @Inject
    UserPreferencesHelper preferencesHelper;
    @Inject
    public LoginPresenter(){
    }
    public void login(final String username, final String password){
        if(TextUtils.isEmpty(username)){
            getView().showToast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)){
            getView().showToast("请输入密码");
            return;
        }
        Observable<HttpResult<LoginEntity>> observable = service.login(username, password, "app");
        toSubscribe(observable, new ProgressDialogSubscriber<HttpResult<LoginEntity>>(activity) {
            @Override
            public void onNext(HttpResult<LoginEntity> result) {
                if (result != null){
                    if (result.getStatus() == 1 && result.getData() != null){
                        getView().loginSucceed();
                        getView().showToast("登陆成功");
                        preferencesHelper.putTokenValue(result.getData().getTokenValue());
                        preferencesHelper.putUuid(result.getData().getUuid());
                        preferencesHelper.putUserName(username);
                        preferencesHelper.putPassword(password);
                    }else{
                        getView().showToast(result.getMsg());
                    }
                }else{
                    getView().showToast("登录失败:result==null");
                }
            }
        });
    }
    public void autologin(){
        String userName = preferencesHelper.getUserName();
        String password = preferencesHelper.getPassword();
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)){
            login(userName, password);
        }
    }
}

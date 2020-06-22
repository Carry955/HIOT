package com.zkl.hiot.ui.login;

import com.zkl.hiot.base.BaseView;

public interface LoginView extends BaseView {
    void showToast(String msg);
    void loginSucceed();
}

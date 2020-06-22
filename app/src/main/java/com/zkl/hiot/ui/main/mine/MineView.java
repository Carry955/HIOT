package com.zkl.hiot.ui.main.mine;

import androidx.fragment.app.Fragment;

import com.zkl.hiot.base.BaseView;

public interface MineView  extends BaseView {
    void showUserHead(String imgUrl);
    void showUserName(String username);
    void showUserEmail(String email);
    void showToast(String msg);
    void logoutSucceed();
    Fragment getFragment();
}

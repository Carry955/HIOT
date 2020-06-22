package com.zkl.hiot.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zkl.hiot.R;
import com.zkl.hiot.base.BaseActivity;
import com.zkl.hiot.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisterActivity extends BaseActivity<RegisterView, RegisterPresenter> implements RegisterView {
    @Inject
    RegisterPresenter registerPresenter;
    private Unbinder unbinder;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.et_user_email)
    EditText etUserEmail;

    @OnClick(R.id.bt_register)
    void register(){
        String username = etUserName.getText().toString();
        String email = etUserEmail.getText().toString();
        String password = etUserPwd.getText().toString();
        registerPresenter.register(username, email, password);
    }
    @OnClick(R.id.link_login)
    void toLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return registerPresenter;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSucceed() {
        toLogin();
    }
}

package com.zkl.hiot.http;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import javax.inject.Inject;

public class UserPreferencesHelper {
    private final SharedPreferences mPreferences;
    private static final String PREF_FILE_NAME = "hiot_user_config";
    private static final String SP_USER_TOKENVALUE_KEY = "SP_USER_TOKENVALUE_KEY";
    private static final String SP_USER_UUID_KEY = "SP_USER_UUID_KEY";
    private static final String SP_USER_NAME_KEY = "SP_USER_NAME_KEY";
    private static final String SP_USER_PASSWORD_KEY = "SP_USER_PASSWORD_KEY";
    private static final String SP_USER_NICK_NAME_KEY = "SP_USER_NICK_NAME_KEY";
    private static final String SP_USER_EMAIL_KEY = "SP_USER_EMAIL_KEY";

    @Inject
    public UserPreferencesHelper(Context context){
        mPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
    }
    public void putTokenValue(String tokenValue){
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString(SP_USER_TOKENVALUE_KEY, tokenValue);
        edit.apply();
    }
    @Nullable
    public String getTokenValue(){
        return mPreferences.getString(SP_USER_TOKENVALUE_KEY, null);
    }
    public void putUuid(String uuid){
        mPreferences.edit().putString(SP_USER_UUID_KEY, uuid).apply();
    }
    @Nullable
    public String getUuid(){
        return mPreferences.getString(SP_USER_UUID_KEY, null);
    }
    public void putUserName(String username){
        mPreferences.edit().putString(SP_USER_NAME_KEY, username).apply();
    }
    @Nullable
    public String getUserName(){
        return mPreferences.getString(SP_USER_NAME_KEY, null);
    }
    public void putPassword(String password){
        mPreferences.edit().putString(SP_USER_PASSWORD_KEY, password).apply();
    }
    @Nullable
    public String getPassword(){
        return mPreferences.getString(SP_USER_PASSWORD_KEY, null);
    }
    public void putNickUserName(String nickname){
        mPreferences.edit().putString(SP_USER_NICK_NAME_KEY, nickname).apply();
    }
    @Nullable
    public String getNickUserName(){
        return mPreferences.getString(SP_USER_NICK_NAME_KEY, null);
    }
    public void putUserEmail(String email){
        mPreferences.edit().putString(SP_USER_EMAIL_KEY, email).apply();
    }
    @Nullable
    public String getUserEmail(){
        return mPreferences.getString(SP_USER_EMAIL_KEY, null);
    }
    public void clear(){
        mPreferences.edit().clear().apply();
    }
}
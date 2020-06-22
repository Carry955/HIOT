package com.zkl.hiot.injection.component;

import android.app.Application;
import android.content.Context;

import com.zkl.hiot.App;
import com.zkl.hiot.http.HttpService;
import com.zkl.hiot.http.UserPreferencesHelper;
import com.zkl.hiot.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(App application);
    Application application();
    Context context();
    HttpService httpService();
    UserPreferencesHelper userPreferencesHelper();
}

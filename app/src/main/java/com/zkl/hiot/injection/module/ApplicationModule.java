package com.zkl.hiot.injection.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.zkl.hiot.App;
import com.zkl.hiot.http.HttpService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    private final App application;
    public ApplicationModule(App application){
        this.application = application;
    }
    @Provides
    Application provideApplication(){
        return application;
    }
    @Provides
    Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(HttpService.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    @Provides
    @Singleton
    Gson provideGson(){
        return new Gson();
    }
    @Provides
    @Singleton
    HttpService provideHttpService(Retrofit retrofit){
        return retrofit.create(HttpService.class);
    }
}

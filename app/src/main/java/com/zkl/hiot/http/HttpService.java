package com.zkl.hiot.http;

import com.zkl.hiot.entity.HolderDeviceEntity;
import com.zkl.hiot.entity.LoginEntity;
import com.zkl.hiot.entity.RegisterEntity;
import com.zkl.hiot.entity.UserEntity;


import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpService {
    String BASE_URL = "http://114.115.179.78:8888/hiot/";
    String IMAGE_BASE_URL = "http://114.115.179.78:8888/hiot";

    @FormUrlEncoded
    @POST("auth/login")
    Observable<HttpResult<LoginEntity>> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("loginCode") String loginCode
    );

    @FormUrlEncoded
    @POST("user/register")
    Observable<HttpResult<RegisterEntity>> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("userType") String userType
    );
    @GET("user")
    Observable<HttpResult<UserEntity>> getUserInfo(
            @Header("Authorization") String Authorization
    );
    @POST("auth/logout")
    Observable<HttpResult> logout(
            @Header("Authorization") String Authorization
    );
    @POST("user/img")
    @Multipart
    Observable<HttpResult> uploadFile(
            @Part MultipartBody.Part file,
            @Header("Authorization") String authorization
            );
    @GET("holder/user")
    Observable<HttpResult<List<HolderDeviceEntity>>> getDeviceList(
            @Query("bonding") int bonding,
            @Header("Authorization") String authorization
    );
}

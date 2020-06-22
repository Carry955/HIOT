package com.zkl.hiot.http;

public interface DataModelCallback {
    void onSuccess(String data);
    void onError(String error);
    void onComplete();
}
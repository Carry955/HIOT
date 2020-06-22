package com.zkl.hiot.http;

public class DataModel {
    public static void getNetData(String param, DataModelCallback callback){
        switch (param){
            case "normal":
                callback.onSuccess("请求网络数据成功");
                break;
            case "error":
                callback.onError("请求异常");
                break;
            case "complete":
                callback.onComplete();
                break;
        }
    }
}

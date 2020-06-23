package com.zkl.hiot.ui.main.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalSelectionDialog;
import com.zkl.hiot.BuildConfig;
import com.zkl.hiot.base.BasePresenter;
import com.zkl.hiot.entity.UserEntity;
import com.zkl.hiot.http.HttpResult;
import com.zkl.hiot.http.HttpService;
import com.zkl.hiot.http.ProgressDialogSubscriber;
import com.zkl.hiot.http.UserPreferencesHelper;
import com.zkl.hiot.ui.main.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CROSS_PROFILE_APPS_SERVICE;

public class MinePresenter extends BasePresenter<MineView> {
    @Inject
    HttpService service;
    @Inject
    UserPreferencesHelper preferencesHelper;
    @Inject
    Activity activity;
    @Inject
    public MinePresenter(){ }
    public void getUserInfo(){
        Observable<HttpResult<UserEntity>> observable =
                service.getUserInfo(preferencesHelper.getTokenValue());
        toSubscribe(observable, new ProgressDialogSubscriber<HttpResult<UserEntity>>(activity) {
            @Override
            public void onNext(HttpResult<UserEntity> result) {
                if (result != null){
                    if(result.getStatus() == 1 && result.getData() != null){
                        getView().showUserHead(HttpService.IMAGE_BASE_URL+result.getData().getImg());
                        getView().showUserName(result.getData().getUsername());
                        getView().showUserEmail(result.getData().getEmail());
                    }else{
                        getView().showToast(result.getMsg());
                    }
                }else{
                    getView().showToast("获取用户信息失败：result==null");
                }
            }
        });
    }

    public void changeHead(){
        requestPermission();
    }

    private void requestPermission(){
        Acp.getInstance(activity).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .build(),
                new AcpListener(){
                    @Override
                    public void onGranted() {
                        choosePicDialog();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        getView().showToast(permissions.toString()+"权限拒绝");
                    }
                });
    }
    private void choosePicDialog(){
        NormalSelectionDialog chooseDialog = new NormalSelectionDialog.Builder(activity)
                .setlTitleVisible(false)
                .setTitleHeight(65)
                .setTitleText("请选择")
                .setTitleTextSize(16)
                .setTitleTextColor(android.R.color.black)
                .setItemHeight(60)
                .setItemWidth(0.9f)
                .setItemTextColor(android.R.color.black)
                .setItemTextSize(16)
                .setCancleButtonText("取消")
                .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                        switch (position){
                            case 0:
                                choseHeadImageFromGallery();
                                dialog.dismiss();
                                break;
                            case 1:
                                choseHeadImageFromCameraCapture();
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .setCanceledOnTouchOutside(true)
                .build();
        ArrayList<String> s = new ArrayList<>();
        s.add("从相机里选择照片");
        s.add("拍照");
        chooseDialog.setDatas(s);
        chooseDialog.show();
    }
    public static final int CHOOSE_PICTURE = 66;
    public static final int TAKE_PICTURE = 55;
    public static final int CROP_SMALL_PICTURE = 44;
    public void choseHeadImageFromGallery(){
        Intent openAlbumIntent = new Intent();
        openAlbumIntent.setAction(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        getView().getFragment().startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }
    private Uri tempUri;
    public void choseHeadImageFromCameraCapture(){
        Intent openCameraIntent = new Intent();
        openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoOutputFile;
        if(hasSdcard()){
            photoOutputFile = new File(Environment.getExternalStorageDirectory(), "head.jpg");
        }else{
            photoOutputFile = new File(activity.getFilesDir(), "head.jpg");
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            tempUri = FileProvider.getUriForFile(activity, "com.zkl.hiot.fileprovider", photoOutputFile);
        }else{
            tempUri = Uri.fromFile(photoOutputFile);
        }
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        getView().getFragment().startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

//    private String savePhoto(Bitmap photoBitmap, String path, String photoName) {
//        String localPath = null;
//        if (android.os.Environment.getExternalStorageState().equals(
//                android.os.Environment.MEDIA_MOUNTED)) {
//            File dir = new File(path);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File photoFile = new File(path, photoName + ".jpg");
//            FileOutputStream fileOutputStream = null;
//            try {
//                fileOutputStream = new FileOutputStream(photoFile);
//                if (photoBitmap != null) {
//                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
//                        localPath = photoFile.getPath();
//                        fileOutputStream.flush();
//                    }
//                }
//            } catch (FileNotFoundException e) {
//                photoFile.delete();
//                localPath = null;
//                e.printStackTrace();
//            } catch (IOException e) {
//                photoFile.delete();
//                localPath = null;
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (fileOutputStream != null) {
//                        fileOutputStream.close();
//                        fileOutputStream = null;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return localPath;
//    }
    public boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case TAKE_PICTURE:
                    cutImage(tempUri);
                    break;
                case CHOOSE_PICTURE:
                    if(data != null){
                        cutImage(data.getData());
                    }
                    break;
                case CROP_SMALL_PICTURE:
                    if(data != null){
//                        Bitmap bitmap = null;
//                        try {
//                            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uritempFile));
//                            uritempFile = null;
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
////                        bitmap = data.getExtras().getParcelable("data");
                        String fileName = "head.jpg";
                        String filePath;
                        if(hasSdcard()){
                            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        }else{
                            filePath = activity.getFilesDir().getAbsolutePath();
                        }
//                        String imagePath = savePhoto(bitmap, filePath, fileName);
//                        File file = new File(imagePath);
                        File file = new File(filePath, fileName);
                        uploadPic(file);
                    }
                    break;
            }
        }
    }
    Uri uritempFile;
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("dyp", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data",false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        String path;
        if(hasSdcard()){
            path = Environment.getExternalStorageDirectory().getPath();
        }else{
            path = activity.getFilesDir().getPath();
        }
        uritempFile = Uri.parse("file://" + "/" + path + "/" + "head.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        getView().getFragment().startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    private void uploadPic(final File file){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartFile =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Observable<HttpResult> observable = service.uploadFile(multipartFile, preferencesHelper.getTokenValue());
        toSubscribe(observable, new ProgressDialogSubscriber<HttpResult>(activity) {
            @Override
            public void onNext(HttpResult result) {
                if(result != null){
                    if(result.getStatus() == 1){
                        getView().showToast(result.getMsg());
                        getUserInfo();
                    }
                    if(result.getStatus() == -100){
                        getView().showToast(result.getMsg());
                    }else{
                        getView().showToast(result.getMsg());
                    }
                }else{
                    getView().showToast("上传失败result==null");
                }
                if(file.exists()&&file.isFile()){
                    file.delete();
                }
            }
        });
    }
    public void logout(){
        Observable<HttpResult> observable = service.logout(preferencesHelper.getTokenValue());
        toSubscribe(observable, new ProgressDialogSubscriber<HttpResult>(activity) {
            @Override
            public void onNext(HttpResult result) {
                if(result != null){
                    if (result.getStatus() == 1){
                        getView().showToast(result.getMsg());
                        getView().logoutSucceed();
                        preferencesHelper.clear();
                    }else{
                        getView().showToast(result.getMsg());
                    }
                }else{
                    getView().showToast("退出登录失败：result==null");
                }
            }
        });
    }
}

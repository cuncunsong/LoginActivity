package com.wt.loginactivity.fragmentViewPage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.wt.loginactivity.R;

public class ImageViewActivity extends AppCompatActivity {

//    private DownloadService.DownloadBinder downloadBinder;
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            downloadBinder = (DownloadService.DownloadBinder) service;
//        }
//    };


    private ImageView imageView;
    private String fileName;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);

        //从相册中加载下载到本地的图片
        imageView = findViewById(R.id.image_download);
        Button takePhone = findViewById(R.id.take_photo);
        takePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ImageViewActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ImageViewActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }

            }
        });

//        Button startDownload = (Button) findViewById(R.id.btn_download);
//        Button pauseDownload = (Button) findViewById(R.id.pause_download);
//        Button cancelDownload = (Button) findViewById(R.id.cancel_download);
//        startDownload.setOnClickListener(this);
//        pauseDownload.setOnClickListener(this);
//        cancelDownload.setOnClickListener(this);
//        Intent intent = new Intent(this, DownloadService.class);
//        startService(intent); // 启动服务,保证DownloadService一直在后台运行
//        bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务,让活动和服务通信
//        //申请权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
//        }
    }


    public void openAlbum() {
//        指定action
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
//        设置第二个参数为CHOOSE_PHOTO，从相册选择完图片回到onActivityResult() 方法时，
//        就会进入CHOOSE_PHOTO 的case来处理图片
        startActivityForResult(intent, CHOOSE_PHOTO);//打开相册
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                //判断手机系统版本号，兼容新老版本手机
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOn(data);
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        handleImageBefore(data);
                    }
                }
        }
    }

    private void handleImageBefore(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOn(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    //    @Override
//    public void onClick(View v) {
//        if (downloadBinder == null) {
//            return;
//        }
//        switch (v.getId()) {
//            case R.id.btn_download:
//                String url = "https://dldir1.qq.com/music/clntupate/QQMusicSetup.exe";
//                downloadBinder.startDownload(url);
//                break;
//            case R.id.pause_download:
//                downloadBinder.pauseDownload();
//                break;
//            case R.id.cancel_download:
//                downloadBinder.cancelDownload();
//                break;
//            default:
//                break;
//        }
//
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //解绑活动，否则可能导致内存泄漏
//        unbindService(connection);
//    }
}

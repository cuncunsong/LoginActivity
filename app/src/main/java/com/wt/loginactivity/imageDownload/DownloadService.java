package com.wt.loginactivity.imageDownload;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.wt.loginactivity.R;
import com.wt.loginactivity.fragmentViewPage.ImageViewActivity;

import java.io.File;

//保证DownloadTask一直在后台运行，创建一个下载的服务
public class DownloadService extends Service {

   private DownloadTask downloadTask;
   private DownloadListener listener=new DownloadListener() {
       @Override
       public void onProgress(int progress) {
           //调用NotificationManager的notify()方法去触发这个通知，在下拉状态栏中实时看到当前下载的进度
           getNotificationManager().notify(1,getNotification("Downloading...",progress));
       }

       @Override
       public void onSuccess() {
           downloadTask=null;
           // 下载成功时将前台服务通知关闭，并创建一个下载成功的通知
           stopForeground(true);
           getNotificationManager().notify(1, getNotification("Download Success", -1));
           Toast.makeText(DownloadService.this, "Download Success",Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onFailed() {
           downloadTask=null;
           // 下载失败时将前台服务通知关闭，并创建一个下载失败的通知
           stopForeground(true);
           getNotificationManager().notify(1,getNotification("Download Fail",-1));
           Toast.makeText(DownloadService.this, "Download Failed",
                   Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onPause() {
           downloadTask = null;
           Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).
                   show();
       }

       @Override
       public void onCanceled() {
           downloadTask = null;
           stopForeground(true);
           Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).
                   show();
       }
   };

    private DownloadBinder mBinder = new DownloadBinder();
    private String downloadUrl;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class DownloadBinder extends Binder{
        //创建了一个DownloadTask的实例，把DownloadListener作为参数传入
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                //调用execute()方法开启下载，并将下载文件的URL地址传入到execute()方法中。
                //手动调用，执行异步线程任务，必须运行在ui线程
                downloadTask.execute(downloadUrl);
                //前台服务
                //调用startForeground() 方法，在系统状态栏中创建一个持续运行的通知
                startForeground(1, getNotification("Downloading...", 0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                // 取消下载时需将文件删除，并将通知关闭
                    String last=".png";
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"))+last;
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Canceled",Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    //用于显示下载进度的通知
    private Notification getNotification(String title, int progress){
        Intent intent = new Intent(this,ImageViewActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(title);
        if(progress>=0){
            //当前progress大于或等于0时才显示下载进度
            builder.setContentText(progress+"%");
            /*
            100:传入通知的最大进度
            progress:传入通知的当前进度
            false:不使用模糊进度条
             */
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消一个正在执行的任务,onCancelled方法将会被调用
        downloadTask.cancel(true);
        Log.d("cancel","cancel");
    }
}
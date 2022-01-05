package com.wt.loginactivity.imageDownload;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//下载
/*
AsyncTask<String,Integer,Integer>
   String：在执行AsyncTask的时候需要传入一个字符串参数给后台任务
   第一个Integer：使用整型数据来作为进度显示单位
   第二个Integer：表示使用整型数据来反馈执行结果
 */
public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;
    private boolean isCancelled=false;
    private boolean isPaused=false;
    private int lastProgress;


    public DownloadTask(DownloadListener listener) {
        //通过listener参数回调下载状态
        this.listener=listener;
    }

    //后台执行的具体下载逻辑
    //该方法在子线程中运行，处理耗时操作
    @Override
    protected Integer doInBackground(String... p) {
        File file=null;
        InputStream is=null;
        RandomAccessFile saveFile=null;

        try {
            long downloadedLength=0;//记录已下载的文件长度
            //从参数中获取下载的url地址
            String downloadUrl=p[0];
            //解析出下载的文件名
            String last=".png";
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"))+last;
            //指定文件下载到sd卡的download目录
            String directory = Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            //判断文件是否存在
            if(file.exists()) {
                //存在，获取已下载的字节数
                downloadedLength = file.length();
            }
            long contentLength=getContentLength(downloadUrl);
            if(contentLength == 0){
                return TYPE_FAILED;
            }else if(contentLength==downloadedLength){
                //已下载字节个文件总长度相等，下载完成
                return TYPE_SUCCESS;
            }
            //发送网络请求
            //1.创建一个OkHttpClient实例
            OkHttpClient client = new OkHttpClient();
            //创建一个request对象发起HTTP请求
            Request request = new Request.Builder()
                    //断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE","bytes="+downloadedLength+"=")
                    .url(downloadUrl).build();
            //发送请求，并获取服务器返回的数据
            Response response = client.newCall(request).execute();
            //不断从网络上读取数据写入到本地，直到文件下载完成
            if(response!=null){
                 is = response.body().byteStream();
                 saveFile = new RandomAccessFile(file, "rw");
                 saveFile.seek(downloadedLength);//跳过已下载的字节
                 byte[] b=new byte[1024];
                 int total=0,len;
                 while((len=is.read(b)) !=-1){
                     //判断是否有暂停和取消操作
                     if(isCancelled){
                         return TYPE_CANCELED;
                     }else if(isPaused){
                         return TYPE_PAUSED;
                     }else{//没有则计算当前下载进度
                         total+=len;
                         saveFile.write(b,0,len);
                         //计算已下载的百分比
                         int progress= (int) ((total+downloadedLength)*100/contentLength);
                         //通知当前下载进度
                         publishProgress(progress);
                     }
                 }
                 response.body().close();
                 return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
           try{
               if(is!=null){
                   is.close();
               }
               if(saveFile!=null){
                   saveFile.close();
               }
               if(isCancelled && file!=null){
                   file.delete();
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        return TYPE_FAILED;
    }

    //在界面上更新当前下载进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress=values[0];
        if(progress >lastProgress){
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    //用于通知具体的下载结果
    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPause();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }
    //修改状态为暂停
    public void pauseDownload(){
        isPaused=true;
    }
    //修改状态为取消下载
    public void cancelDownload(){
        isCancelled=true;
    }

    //获取文件待下载总数
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }
}

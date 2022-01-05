package com.wt.loginactivity.imageDownload;

//定义一个回调接口，监听下载过程中的各种状态
public interface DownloadListener {
//通知当前下载进度
    void onProgress(int progress);
//    通知下载成功事件
    void onSuccess();
//    通知下载失败
    void onFailed();
//    通知下载暂停
    void onPause();
//    通知下载取消
    void onCanceled();
}

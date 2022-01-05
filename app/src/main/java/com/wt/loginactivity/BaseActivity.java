package com.wt.loginactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity{

    private ForceReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

    }

    //注册
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wt.loginactivity.FORCE_OFFLINE");
        receiver = new ForceReceiver();
        //注册广播
        registerReceiver(receiver,intentFilter);
    }

    //取消
    @Override
    protected void onPause() {
        super.onPause();
        if(receiver!=null){
            //取消广播
            unregisterReceiver(receiver);
            receiver=null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    class ForceReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent) {
            //构建一个对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("warning");
            builder.setMessage("You are forced to be offline. Please try to login again.");
            //设置对话框不可取消
            builder.setCancelable(false);
            //给对话框注册确定按钮，点击确定销毁所有活动，并重启LoginActivity
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();//销毁所有活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);//重新启动LoginActivity
                }
            });
            builder.show();
        }
    }
}

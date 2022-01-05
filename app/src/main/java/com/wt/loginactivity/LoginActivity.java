package com.wt.loginactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wt.loginactivity.manager.UserManager;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private Button btLogin;
    private CheckBox rememberPass;
    private EditText user,pwd;
    private SharedPreferences pref;
    private SharedPreferences.Editor edit;
    private TextView et_register;
    UserManager userManager=new UserManager();
    private String username;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btLogin = findViewById(R.id.bt_login);
        user = findViewById(R.id.et_user);
        pwd = findViewById(R.id.et_pwd);
        rememberPass = findViewById(R.id.remember_pass);

        //获取SharedPreferences对象
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if(isRemember){
            //将账号密码设置到文本框中
            user.setText(pref.getString("name", ""));
            pwd.setText(pref.getString("password", ""));
            rememberPass.setChecked(true);
        }

        btLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                username = user.getText().toString().trim();
                String password=pwd.getText().toString().trim();

                if(!userManager.findUserByName(username) && !userManager.findUserByPwd(password)){
                    //获取SharedPreferences.edit对象
                    edit = pref.edit();
                    //判断是否记住密码
                    if(rememberPass.isChecked()){
//                         选择记住密码，将用户名密码存入SharedPreferences文件
                        edit.putBoolean("remember_password",true);
                        edit.putString("name",username);
                        edit.putString("password",password);
                    }else{
//                         若没有选择记住密码，则清空文件
                        edit.clear();
                    }
//                     提交
                    edit.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });

         et_register = findViewById(R.id.et_register);
         et_register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                 startActivity(intent);
             }
         });
        Log.e(TAG,"onCreate");
    }


    public  String getTitles(){
        return username;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"onRestart");
    }
}


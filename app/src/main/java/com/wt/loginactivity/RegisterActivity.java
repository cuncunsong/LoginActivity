package com.wt.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wt.loginactivity.bean.User;
import com.wt.loginactivity.manager.UserManager;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "Register";
    private EditText etUser,etPwd;
    UserManager userManager=new UserManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btRegister = findViewById(R.id.bt_register);

        etUser = findViewById(R.id.et_user);
        etPwd = findViewById(R.id.et_pwd);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_register();
            }
        });
        Log.e(TAG,"create");
    }

    //注册
    public void check_register(){
        if(isPwdValid()){
            boolean flag;
            String name=etUser.getText().toString().trim();
            String pwd=etPwd.getText().toString().trim();
            flag=userManager.findUserByName(name);

            if(!flag){
                Toast.makeText(this, "用户名已存在，请重新输入！", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                flag=userManager.insertUser(name,pwd);
                if(!flag){
                    Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    List<User> userList = DataSupport.findAll(User.class);
                    for (User user : userList) {
                        Log.e(TAG,"name is "+user.getUserName());
                        Log.e(TAG,"pwd is "+user.getPassWord());
                    }
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
    //判断输入密码和账号是否为空
    public boolean isPwdValid(){
        String name=etUser.getText().toString().trim();
        String pwd=etPwd.getText().toString().trim();
        if(name.equals("")){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(pwd.equals("")){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

package com.wt.loginactivity.manager;

import com.wt.loginactivity.bean.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class UserManager {
    //插入数据
    public boolean insertUser(String name,String pwd){

        User user = new User();
        user.setUserName(name);
        user.setPassWord(pwd);
     //创建数据库
        LitePal.getDatabase();
        return user.save();
    }

    //判断用户名是否存在
    public boolean findUserByName(String name){
        List<User> users = DataSupport.findAll(User.class);
        for (User user : users) {
            if(name.trim().equals(user.getUserName())){
                return false;
            }
        }
        return true;
    }
    //判断密码是否存在
    public boolean findUserByPwd(String pwd){
        List<User> users = DataSupport.findAll(User.class);
        for (User user : users) {
            if(pwd.trim().equals(user.getPassWord())){
                return false;
            }
        }
        return true;
    }


    //返回用户id
    public int getUserIdByName(String name){
        List<User> users = DataSupport.findAll(User.class);
        for (User user : users) {
            if(name.equals(user.getUserName())){
                return user.getId();
            }
        }
        return -1;
    }
}

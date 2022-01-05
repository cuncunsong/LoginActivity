package com.wt.loginactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLite extends SQLiteOpenHelper {

    public static SQLiteOpenHelper sqLiteOpenHelper;
    public static synchronized SQLiteOpenHelper getInstance(Context context){
        if(sqLiteOpenHelper ==null){
            sqLiteOpenHelper=new MySQLite(context,"Collection.db",null,1);
        }
        return sqLiteOpenHelper;
    }
    public MySQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       String sql="create table arColl(id integer primary key AUTOINCREMENT,title text,author text,userid integer)";
      // String sql1="create table imageColl(id integer primary key AUTOINCREMENT,urlPath text,userid integer)";
       sqLiteDatabase.execSQL(sql);
       //sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

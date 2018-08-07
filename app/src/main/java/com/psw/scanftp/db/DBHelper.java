package com.psw.scanftp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pangshanwei on 2018/8/7.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static String dbName = "barcode.db" ;
    public static  int version = 1;
    public DBHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE barcode(" +
                "date TEXT," +
                "barcode TEXT," +
                "isup INTEGER" +
                ");";
        db.execSQL(sql) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

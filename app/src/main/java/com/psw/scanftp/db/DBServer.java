package com.psw.scanftp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.psw.scanftp.entity.Barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangshanwei on 2018/8/7.
 */

public class DBServer {

    private DBHelper helper ;

    private String tableName = "barcode" ;

    public DBServer(Context context){
        helper = new DBHelper(context) ;
    }

    /**
     * 插入数据
     * @param bar
     * @return
     */
    public int insertBarcode(Barcode bar){
        int ret = 0 ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put("date", bar.getDate()) ;
        values.put("barcode", bar.getBarcode()) ;
        if(bar.getIsUpload() ){
            values.put("isup", 1) ;
        }else{
            values.put("isup", 0) ;
        }


        ret = (int) db.insert(tableName, null, values) ;
        db.close();
        return ret ;

    }

    /**
     * 更新数据库状态
     * @param bar
     * @param isUp
     */
    public void updateBarcode(Barcode bar, boolean isUp){
        SQLiteDatabase db = helper.getWritableDatabase() ;
        int ret = 0 ;
        ContentValues values = new ContentValues() ;
        values.put("date", bar.getDate()) ;
        values.put("barcode", bar.getBarcode()) ;
        if(bar.getIsUpload() ){
            values.put("isup", 1) ;
        }else{
            values.put("isup", 0) ;
        }
        db.update(tableName, values, "barcode=?", new String[]{bar.getBarcode()}) ;
        db.close();
    }

    /**
     * 查询条码
     * @param barcode
     * @return
     */
    public Barcode queryBarcode(String barcode){
        Barcode bar = null ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        int ret = 0 ;
        Cursor cursor = db.query(tableName, null, "barcode=?",
                new String[]{barcode}, null, null, null) ;
        while(cursor.moveToNext()){
            bar = new Barcode() ;
            bar.setBarcode(barcode);
            bar.setDate(cursor.getString(cursor.getColumnIndex("date")));
            bar.setUpload(cursor.getInt(cursor.getColumnIndex("isup")) == 1);
        }
        db.close();
        return bar ;
    }

    /**
     * 根据文件名查条码
     * @param date
     * @return
     */
    public List<Barcode> queryByFileName(String date){
        List<Barcode> list = new ArrayList<>() ;
        SQLiteDatabase db = helper.getWritableDatabase() ;
        int ret = 0 ;
        Cursor cursor = db.query(tableName, null, "date=?",
                new String[]{date}, null, null, null) ;
        while(cursor.moveToNext()){
            Barcode bar = new Barcode() ;
            bar.setBarcode(cursor.getString(cursor.getColumnIndex("barcode")));
            bar.setDate(cursor.getString(cursor.getColumnIndex("date")));
            bar.setUpload(cursor.getInt(cursor.getColumnIndex("isup")) == 1);
            list.add(bar) ;
        }
        db.close();
        return list ;
    }

    /**
     * 删除表
     */
    public void deleteBarTable(){
        SQLiteDatabase db = helper.getWritableDatabase() ;
        int ret = 0 ;
        ret = db.delete(tableName, null, null) ;

        db.close() ;
    }
}

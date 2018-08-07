package com.psw.scanftp.entity;

/**
 * Created by pangshanwei on 2018/8/7.
 */

public class Barcode {

    private String date ;
    private String barcode ;

    private boolean isUpload ;

    public boolean getIsUpload(){
        return this.isUpload ;
    }
    public String getBarcode() {
        return barcode;
    }

    public String getDate() {
        return date;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }



    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

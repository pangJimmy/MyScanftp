package com.psw.scanftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psw.scanftp.base.MBaseActivity;
import com.psw.scanftp.util.FTPutil;
import com.psw.scanftp.util.FileUtil;
import com.psw.scanftp.util.SharedFile;
import com.psw.scanftp.util.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanDevice;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//主界面
public class MainActivity extends MBaseActivity implements OnClickListener{

    private TextView tv1 ;
    private TextView tv2 ;
    private TextView tv3 ;
    private TextView tv4 ;
    private TextView tv5 ;
    private TextView tv6 ;
    private TextView tv7 ;
    private TextView tv8 ;
    private TextView tv9 ;
    private TextView tv10 ;
    private TextView tv11 ;
    private TextView tv12 ;
    private TextView tv13 ;
    private TextView tv14 ;
    private TextView tv15 ;
    private TextView tv16 ;
    private TextView tv17 ;
    private TextView tv18 ;
    private TextView tv19 ;
    private TextView tv20 ;
    private TextView tv21 ;
    private TextView tv22 ;
    private TextView tv23 ;
    private TextView tv24 ;
    private TextView tv25 ;
    private TextView tv26 ;

    private EditText edit1 ;
    private EditText edit2 ;
    private EditText edit3 ;
    private EditText edit4 ;
    private EditText edit5 ;
    private EditText edit6 ;
    private EditText edit7 ;
    private EditText edit8 ;
    private EditText edit9 ;
    private EditText edit10 ;
    private EditText edit11 ;
    private EditText edit12 ;
    private EditText edit13 ;
    private EditText edit14 ;
    private EditText edit15 ;
    private EditText edit16 ;
    private EditText edit17 ;
    private EditText edit18 ;
    private EditText edit19 ;
    private EditText edit20 ;
    private EditText edit21 ;
    private EditText edit22 ;
    private EditText edit23 ;
    private EditText edit24 ;
    private EditText edit25 ;
    private EditText edit26 ;

    String info1 ;
    String info2 ;
    String info3 ;
    String info4 ;
    String info5 ;
    String info6 ;
    String info7 ;
    String info8 ;
    String info9 ;
    String info10 ;
    String info11 ;
    String info12 ;
    String info13 ;
    String info14 ;
    String info15 ;
    String info16 ;
    String info17 ;
    String info18 ;
    String info19 ;
    String info20 ;
    String info21 ;
    String info22 ;
    String info23 ;
    String info24 ;
    String info25 ;
    String info26 ;

    private Button btnUpload ;

    List<TextView> listTV = new ArrayList<TextView>() ;
    List<EditText> listEdit = new ArrayList<EditText>() ;
    List<String> listConfig ;
    private SharedFile shared ;
    private Spinner spinnerType ;
    String[] types = new String[]{"出库", "入库"} ;
    String workTypes = "出库";

    private final static String SCAN_ACTION = "scan.rcv.message";
    private ScanDevice scanDevice ;
    String barcodeStr ;
    //条码集合
    private Set<String> barcodeSet =  null;
    //文件名称
    private String fileName = null ;
    //扫描接收广播
    private BroadcastReceiver scanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] barocode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            byte temp = intent.getByteExtra("barcodeType", (byte) 0);
            android.util.Log.e("debug", "----codetype--" + temp);
            barcodeStr = new String(barocode, 0, barocodelen);
            if ( (barcodeSet == null|| barcodeSet.isEmpty()) && fileName == null) {
                //第一次添加
                barcodeSet.add(barcodeStr) ;
                edit2.append(barcodeStr+ ",\r\n") ;
                //创建文件
                String fileName = Utils.getDate() + ".txt" ;
                MainActivity.this.fileName = fileName ;
                String info = "[" + workTypes +"]"+ "\r\n" + "[条码信息]"+"\r\n";
                info =info + genSingleLine(barcodeStr);
                //写入文件
                writeToFile(fileName, info);
            }else{
                //集合中不包含，则添加
                if (!barcodeSet.contains(barcodeStr)) {
                    barcodeSet.add(barcodeStr) ;
                    edit2.append(barcodeStr+ ",\r\n") ;
                    //写入文件
                    writeToFile(fileName, genSingleLine(barcodeStr));
                }else{
                    //集合中包含则提示
                    ToastShow("该条码已扫");
                }
            }

            // tvResult.setText(barcodeStr);
            //写入TXT中
            scanDevice.stopScan();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main) ;
        setTitle("扫描作业") ;
        setBtnBackClickable(false) ;
        FileUtil.checkConfig(this) ;
        listConfig = FileUtil.getConfigs() ;
        for(String conf : listConfig){
            Log.e("", conf) ;
        }
        barcodeSet =  new HashSet<>() ;
        shared = new SharedFile(this) ;
		scanDevice = new ScanDevice() ;
        initView() ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(scanDevice != null){
            scanDevice.openScan() ;
            scanDevice.setOutScanMode(0);//接收广播
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(scanReceiver, filter);
    }

    @Override
    protected void onPause() {

        super.onPause();


        if(scanDevice != null) {
            scanDevice.stopScan();
            scanDevice.closeScan();
        }
        unregisterReceiver(scanReceiver);
    }

    private void initView(){
        //tv1 = (TextView) findViewById(R.id.textView_info_1) ;

        spinnerType = (Spinner) findViewById(R.id.spinner_type) ;
        spinnerType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types)) ;
        spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                workTypes = types[position] ;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        }) ;
        tv2 = (TextView) findViewById(R.id.textView_info_2) ;
        tv3 = (TextView) findViewById(R.id.textView_info_3) ;
        tv4 = (TextView) findViewById(R.id.textView_info_4) ;
        tv5 = (TextView) findViewById(R.id.textView_info_5) ;
        tv6 = (TextView) findViewById(R.id.textView_info_6) ;
        tv7 = (TextView) findViewById(R.id.textView_info_7) ;
        tv8 = (TextView) findViewById(R.id.textView_info_8) ;
        tv9 = (TextView) findViewById(R.id.textView_info_9) ;
        tv10 = (TextView) findViewById(R.id.textView_info_10) ;
        tv11= (TextView) findViewById(R.id.textView_info_11) ;
        tv12 = (TextView) findViewById(R.id.textView_info_12) ;
        tv13 = (TextView) findViewById(R.id.textView_info_13) ;
        tv14 = (TextView) findViewById(R.id.textView_info_14) ;
        tv15 = (TextView) findViewById(R.id.textView_info_15) ;
        tv16 = (TextView) findViewById(R.id.textView_info_16) ;
        tv17 = (TextView) findViewById(R.id.textView_info_17) ;
        tv18 = (TextView) findViewById(R.id.textView_info_18) ;
        tv19 = (TextView) findViewById(R.id.textView_info_19) ;
        tv20 = (TextView) findViewById(R.id.textView_info_20) ;
        tv21 = (TextView) findViewById(R.id.textView_info_21) ;
        tv22 = (TextView) findViewById(R.id.textView_info_22) ;
        tv23 = (TextView) findViewById(R.id.textView_info_23) ;
        tv24 = (TextView) findViewById(R.id.textView_info_24) ;
        tv25 = (TextView) findViewById(R.id.textView_info_25) ;
        tv26 = (TextView) findViewById(R.id.textView_info_26) ;

        //listTV.add(tv1) ;
        listTV.add(tv2) ;
        listTV.add(tv3) ;
        listTV.add(tv4) ;
        listTV.add(tv5) ;
        listTV.add(tv6) ;
        listTV.add(tv7) ;
        listTV.add(tv8) ;
        listTV.add(tv9) ;
        listTV.add(tv10) ;
        listTV.add(tv11) ;
        listTV.add(tv12) ;
        listTV.add(tv13) ;
        listTV.add(tv14) ;
        listTV.add(tv15) ;
        listTV.add(tv16) ;
        listTV.add(tv17) ;
        listTV.add(tv18) ;
        listTV.add(tv19) ;
        listTV.add(tv20) ;
        listTV.add(tv21) ;
        listTV.add(tv22) ;
        listTV.add(tv23) ;
        listTV.add(tv24) ;
        listTV.add(tv25) ;
        listTV.add(tv26) ;


        //edit1 = (EditText) findViewById(R.id.editText_info_1) ;
        edit2 = (EditText) findViewById(R.id.editText_info_2) ;
        edit3 = (EditText) findViewById(R.id.editText_info_3) ;
        edit4 = (EditText) findViewById(R.id.editText_info_4) ;
        edit5 = (EditText) findViewById(R.id.editText_info_5) ;
        edit6 = (EditText) findViewById(R.id.editText_info_6) ;
        edit7 = (EditText) findViewById(R.id.editText_info_7) ;
        edit8 = (EditText) findViewById(R.id.editText_info_8) ;
        edit9 = (EditText) findViewById(R.id.editText_info_9) ;
        edit10 = (EditText) findViewById(R.id.editText_info_10) ;
        edit11 = (EditText) findViewById(R.id.editText_info_11) ;
        edit12 = (EditText) findViewById(R.id.editText_info_12) ;
        edit13 = (EditText) findViewById(R.id.editText_info_13) ;
        edit14 = (EditText) findViewById(R.id.editText_info_14) ;
        edit15 = (EditText) findViewById(R.id.editText_info_15) ;
        edit16 = (EditText) findViewById(R.id.editText_info_16) ;
        edit17 = (EditText) findViewById(R.id.editText_info_17) ;
        edit18 = (EditText) findViewById(R.id.editText_info_18) ;
        edit19 = (EditText) findViewById(R.id.editText_info_19) ;
        edit20 = (EditText) findViewById(R.id.editText_info_20) ;
        edit21 = (EditText) findViewById(R.id.editText_info_21) ;
        edit22 = (EditText) findViewById(R.id.editText_info_22) ;
        edit23 = (EditText) findViewById(R.id.editText_info_23) ;
        edit24 = (EditText) findViewById(R.id.editText_info_24) ;
        edit25 = (EditText) findViewById(R.id.editText_info_25) ;
        edit26 = (EditText) findViewById(R.id.editText_info_26) ;

        //listEdit.add(edit1) ;
        listEdit.add(edit2) ;
        listEdit.add(edit3) ;
        listEdit.add(edit4) ;
        listEdit.add(edit5) ;
        listEdit.add(edit6) ;
        listEdit.add(edit7) ;
        listEdit.add(edit8) ;
        listEdit.add(edit9) ;
        listEdit.add(edit10) ;
        listEdit.add(edit11) ;
        listEdit.add(edit12) ;
        listEdit.add(edit13) ;
        listEdit.add(edit14) ;
        listEdit.add(edit15) ;
        listEdit.add(edit16) ;
        listEdit.add(edit17) ;
        listEdit.add(edit18) ;
        listEdit.add(edit19) ;
        listEdit.add(edit20) ;
        listEdit.add(edit21) ;
        listEdit.add(edit22) ;
        listEdit.add(edit23) ;
        listEdit.add(edit24) ;
        listEdit.add(edit25) ;
        listEdit.add(edit26) ;

        //tv1.setVisibility(View.GONE) ;
        tv2.setVisibility(View.GONE) ;
        tv3.setVisibility(View.GONE) ;
        tv4.setVisibility(View.GONE) ;
        tv5.setVisibility(View.GONE) ;
        tv6.setVisibility(View.GONE) ;
        tv7.setVisibility(View.GONE) ;
        tv8.setVisibility(View.GONE) ;
        tv9.setVisibility(View.GONE) ;
        tv10.setVisibility(View.GONE) ;
        tv11.setVisibility(View.GONE) ;
        tv12.setVisibility(View.GONE) ;
        tv13.setVisibility(View.GONE) ;
        tv14.setVisibility(View.GONE) ;
        tv15.setVisibility(View.GONE) ;
        tv16.setVisibility(View.GONE) ;
        tv17.setVisibility(View.GONE) ;
        tv18.setVisibility(View.GONE) ;
        tv19.setVisibility(View.GONE) ;
        tv20.setVisibility(View.GONE) ;
        tv21.setVisibility(View.GONE) ;
        tv22.setVisibility(View.GONE) ;
        tv23.setVisibility(View.GONE) ;
        tv24.setVisibility(View.GONE) ;
        tv25.setVisibility(View.GONE) ;
        tv26.setVisibility(View.GONE) ;

        //edit1.setVisibility(View.GONE) ;
        edit2.setVisibility(View.GONE) ;
        edit3.setVisibility(View.GONE) ;
        edit4.setVisibility(View.GONE) ;
        edit5.setVisibility(View.GONE) ;
        edit6.setVisibility(View.GONE) ;
        edit7.setVisibility(View.GONE) ;
        edit8.setVisibility(View.GONE) ;
        edit9.setVisibility(View.GONE) ;
        edit10.setVisibility(View.GONE) ;
        edit11.setVisibility(View.GONE) ;
        edit12.setVisibility(View.GONE) ;
        edit13.setVisibility(View.GONE) ;
        edit14.setVisibility(View.GONE) ;
        edit15.setVisibility(View.GONE) ;
        edit16.setVisibility(View.GONE) ;
        edit17.setVisibility(View.GONE) ;
        edit18.setVisibility(View.GONE) ;
        edit19.setVisibility(View.GONE) ;
        edit20.setVisibility(View.GONE) ;
        edit21.setVisibility(View.GONE) ;
        edit22.setVisibility(View.GONE) ;
        edit23.setVisibility(View.GONE) ;
        edit24.setVisibility(View.GONE) ;
        edit25.setVisibility(View.GONE) ;
        edit26.setVisibility(View.GONE) ;
        btnUpload = (Button ) findViewById(R.id.button_upload) ;
        btnUpload.setOnClickListener(this) ;
        int index = 0 ;
        while(index < (listConfig.size() )){
            listTV.get(index).setVisibility(View.VISIBLE) ;
            listTV.get(index).setText(listConfig.get(index) + ":") ;
            listEdit.get(index).setVisibility(View.VISIBLE) ;
            Log.e("index","" + index + "---" + listConfig.get(index)) ;
            index++ ;
        }

    }


    String url = "192.168.1.169" ;
    int port = 21 ;
    String username = "test" ;
    String password = "test" ;
    String path = "/" ;
    String unfinishPath = "/mnt/sdcard/Scanftp/unfinish" ;
    String finishPath = "/mnt/sdcard/Scanftp/finish" ;

    /**
     * 写成内部类
     */
    class UploadTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            createLoaddingDialog("正在上传文件") ;
        };

        @Override
        protected Boolean doInBackground(String... params) {
            FTPutil ftp = new FTPutil() ;
            FileInputStream fis;
            boolean flag  = false ;
            //未上传的文件
            List<String> listUpdateFiles ;
            try {
                url = shared.getFtp() ;
                port = Integer.valueOf(shared.getPort()) ;
                username = shared.getAccount() ;
                password = shared.getPassword() ;

                //要写入的文件
                fis = new FileInputStream(new File(upFilepath + params[0]));
//                url = "192.168.1.106" ;
//                port = 21 ;
                flag = ftp.upload(url, port, username, password, path, params[0], fis) ;
                //如果上传失败，把文件放到unfinish
                if (!flag) {
                    FileUtil.cutFile(upFilepath, params[0], unfinishPath, params[0]);
                }else{
                    FileUtil.cutFile(upFilepath, params[0], finishPath, params[0]);
                }
                //先上传跟目录的
                listUpdateFiles = FileUtil.getFiles(upFilepath) ;
                if(listUpdateFiles != null && !listUpdateFiles.isEmpty()){
                    for (String fileName : listUpdateFiles) {
                        fis = new FileInputStream(new File(upFilepath +fileName));
                        flag = ftp.upload(url, port, username, password, path, fileName, fis) ;
                        //如果上传失败，把文件放到unfinish
                        if (!flag) {
                            FileUtil.cutFile(upFilepath, fileName, unfinishPath, fileName);
                        }else{
                            FileUtil.cutFile(upFilepath, fileName, finishPath, fileName);
                        }
                    }
                }
                //再上传unfinish目录的
                listUpdateFiles = FileUtil.getFiles(unfinishPath) ;
                if(listUpdateFiles != null && !listUpdateFiles.isEmpty()){
                    for (String fileName : listUpdateFiles) {
                        fis = new FileInputStream(new File(unfinishPath+"/" +fileName));
                        flag = ftp.upload(url, port, username, password, path, fileName, fis) ;
                        //如果上传失败，把文件放到unfinish
                        if (!flag) {
//                            FileUtil.cutFile(unfinishPath, fileName, unfinishPath,fileName);
                        }else{
                            FileUtil.cutFile(unfinishPath, fileName, finishPath, fileName);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return flag;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialogLoading.cancel() ;
            if(result){
                ToastShow("上传成功") ;
                fileName = null ;
                barcodeSet.clear();
                listEdit.get(0).setText("");
//                initView();

            }else{
                ToastShow("上传失败,文件保存在unfinish目录下") ;
                fileName = null ;
                barcodeSet.clear();
                listEdit.get(0).setText("");
//                initView();
            }
        }
    } ;


    /**
     * 生成单行数据
     * @return
     */
    private String genSingleLine(String barcodeStr) {
        String temp =barcodeStr +  "," ;
        for(int i = 1 ; i < listConfig.size(); i++){
            if(i == listConfig.size() - 1){

                temp = temp + listEdit.get(i).getText().toString() + "\r\n";
            }else {
                temp = temp + listEdit.get(i).getText().toString() + ",";
            }
        }
        return temp ;
    }

    /**
     * 将数据写入文件
     * @param fileName
     * @param temp
     */
    private void writeToFile(String fileName, String temp) {
        try{

            //
            File pathFile = new File(upFilepath) ;
            if(!pathFile.exists()){
                pathFile.mkdirs() ;
            }
            File desFile = new File(upFilepath + fileName) ;
            FileWriter fw = new FileWriter(desFile,true) ;
            fw.write(temp) ;
            fw.flush() ;
            fw.close() ;
        }catch(Exception e){

        }
    }

    private String upFilepath = "mnt/sdcard/Scanftp/" ;
    /*生成文件信息如下
    [出库]
    [条码信息]
    123,11,22,33,44,55,66,77
     */
    private void saveInfo(String fileName){
        String info = "[" + workTypes +"]"+ "\r\n" + "[条码信息]"+"\r\n";
        String temp = "" ;
        for(int i = 1 ; i < listConfig.size(); i++){
            if(i == listConfig.size() - 1){

                temp = temp + listEdit.get(i).getText().toString() ;
            }else {
                temp = temp + listEdit.get(i).getText().toString() + ",";
            }
        }
        String barcode =listEdit.get(0).getText().toString() ;
        String tt = "";
        String[] bs = barcode.split("\r\n") ;
        if(bs != null){
            for(String b: bs){
                tt = tt + b + temp +"\r\n" ;
            }
        }
        info = info + tt ;
        try{

            //
            File pathFile = new File(path) ;
            if(!pathFile.exists()){
                pathFile.mkdirs() ;
            }
            File desFile = new File(upFilepath + fileName) ;
            FileWriter fw = new FileWriter(desFile) ;
            fw.write(info) ;
            fw.flush() ;
            fw.close() ;
        }catch(Exception e){

        }

    }

    @Override
    public void onClick(View v) {
        /*20180730 改变流程
        String fileName = Utils.getDate() + ".txt" ;
        saveInfo(fileName) ;
        */
        if (fileName != null) {

                UploadTask task = new UploadTask();
                task.execute(fileName);

        }else{
            ToastShow("请扫条码");
        }


    }
}

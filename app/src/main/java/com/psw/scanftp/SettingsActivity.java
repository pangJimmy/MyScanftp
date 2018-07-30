package com.psw.scanftp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.psw.scanftp.base.MBaseActivity;
import com.psw.scanftp.util.SharedFile;

//设置
public class SettingsActivity extends MBaseActivity implements OnClickListener{

    private EditText editFtp ;
    private EditText editPort ;
    private EditText editAccount ;
    private EditText editPassword ;
    private EditText editSearch ;
    private Button btnOk ;

    private String ftp ;
    private String port ;
    private String account ;
    private String password ;

    private String search ;

    private SharedFile shared ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings) ;
        setTitle("设置") ;
        super.tvCancel.setVisibility(View.GONE) ;
        shared = new SharedFile(this) ;
        initView() ;
    }

    private void initView(){
        editFtp = (EditText) findViewById(R.id.editText_ftp) ;
        editPort = (EditText) findViewById(R.id.editText_port) ;
        editAccount = (EditText) findViewById(R.id.editText_account) ;
        editPassword = (EditText) findViewById(R.id.editText_password) ;

        editSearch = (EditText) findViewById(R.id.editText_search) ;
        btnOk = (Button) findViewById(R.id.button_ok)  ;
        ftp = shared.getFtp() ;
        port = shared.getPort() ;
        account = shared.getAccount() ;
        password = shared.getPassword() ;
        search = shared.getSearchAddr() ;

        editAccount.setText(account) ;
        editFtp.setText(ftp) ;
        editPassword.setText(password) ;
        editPort.setText(port) ;
        editSearch.setText(search) ;

        btnOk.setOnClickListener(this) ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:
                if(saveConfig() ){
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show()  ;
                }
                break;

            default:
                break;
        }

    }

    //保存参数配置
    private boolean saveConfig(){
        ftp = editFtp.getText().toString() ;
        port = editPort.getText().toString() ;
        account = editAccount.getText().toString() ;
        password = editPassword.getText().toString() ;
        search = editSearch.getText().toString() ;
        if(ftp == null || ftp.length() == 0){
            Toast.makeText(this, "请输入FTP地址", Toast.LENGTH_SHORT).show() ;
            return false ;
        }

        if(port == null || port.length() == 0){
            Toast.makeText(this, "请输入端口号", Toast.LENGTH_SHORT).show() ;
            return false ;
        }

//		if(account == null || account.length() == 0){
//			Toast.makeText(this, "请输入FTP地址", 0).show() ;
//			return false ;
//		}
//
//		if(password == null || password.length() == 0){
//			Toast.makeText(this, "请输入FTP地址", 0).show() ;
//			return false ;
//		}
        shared.setFtp(ftp) ;
        shared.setAccount(account) ;
        shared.setPassword(password) ;
        shared.setPort(port) ;
        shared.setSearchAddr(search) ;
        return true ;
    }
}

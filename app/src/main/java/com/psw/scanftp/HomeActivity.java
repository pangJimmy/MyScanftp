package com.psw.scanftp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.psw.scanftp.base.MBaseActivity;
//主界面
public class HomeActivity extends MBaseActivity implements OnClickListener{

    private Button btnWork ;
    private Button btnSettings ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home) ;
        setTitle("主界面") ;
        setBtnBackClickable(false) ;
        setCancelUnvisible() ;
        initView() ;

    }

    private void initView(){
        btnWork = (Button) findViewById(R.id.button_work) ;
        btnSettings = (Button) findViewById(R.id.button_settings) ;
        btnWork.setOnClickListener(this) ;
        btnSettings.setOnClickListener(this) ;

    }

    @Override
    public void onClick(View v) {
        Intent intent = null ;
        switch (v.getId()) {
            case R.id.button_work:
                intent = new Intent(this, MainActivity.class) ;
                break;
            case R.id.button_settings:
                intent = new Intent(this, SettingsActivity.class) ;
                break;

            default:
                break;
        }
        startActivity(intent) ;
    }
}

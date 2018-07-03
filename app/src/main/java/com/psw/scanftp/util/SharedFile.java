package com.psw.scanftp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedFile {

	private Context context ;

	public SharedFile(Context context){
		this.context = context ;
	}

	/**
	 * 保存ftp
	 * @param ftp
	 */
	public void setFtp(String ftp){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("ftp", ftp) ;
		editor.commit() ;
	}

	/**
	 * 获取FTP
	 * @return
	 */
	public String getFtp(){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		return shared.getString("ftp", "192.168.1.169") ;
	}


	/**
	 * 保存端口
	 * @param ftp
	 */
	public void setPort(String port){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("port", port) ;
		editor.commit() ;
	}

	/**
	 * 获取端口
	 * @return
	 */
	public String getPort(){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		return shared.getString("port", "21") ;
	}

	/**
	 * 保存账号
	 * @param ftp
	 */
	public void setAccount(String account){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("account", account) ;
		editor.commit() ;
	}

	/**
	 * 获取账号
	 * @return
	 */
	public String getAccount(){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		return shared.getString("account", "test") ;
	}

	/**
	 * 保存密码
	 * @param ftp
	 */
	public void setPassword(String password){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("password", password) ;
		editor.commit() ;
	}

	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword(){
		SharedPreferences shared = context.getSharedPreferences("config", Context.MODE_PRIVATE) ;
		return shared.getString("password", "test") ;
	}
}

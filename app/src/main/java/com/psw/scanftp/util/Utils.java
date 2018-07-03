package com.psw.scanftp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	//获取时间日期
	public static String getDate(){
		String time = "" ;
		Date date = new Date() ;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss") ;
		time = format.format(date) ;
		return time ;
	}
}

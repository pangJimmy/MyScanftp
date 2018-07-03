package com.psw.scanftp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import com.psw.scanftp.R;

import android.content.Context;
import android.util.Log;

public class FileUtil {

	/**
	 * 剪切文件到另一目录
	 * @param srcPath
	 * @param srcName
	 * @param desPath
	 * @param desName
	 */
	public static void cutFile(String  srcPath, String srcName, String desPath, String desName){
		File fileSrc = new File(srcPath+ "/" + srcName);
		File fileDes = new File(desPath+ "/" + desName);
		File des = new File(desPath) ;
		if(!des.exists()){
			des.mkdirs() ;
		}
		byte[] temp = new byte[1024] ;
		int size = 0 ;
		try {
			FileInputStream fis = new FileInputStream(fileSrc) ;
			FileOutputStream fos = new FileOutputStream(fileDes) ;
			size = fis.read(temp) ;
			while(size > 0){
				fos.write(temp, 0, size) ;
				fos.flush();
				size = fis.read(temp) ;
			}
			fis.close() ;
			fos.close() ;
			fileSrc.delete() ;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/***
	 * 获取未完成图片
	 * @return
	 */
	public static List<String> getUnuploadPic(){
		List<String> listPic = new ArrayList<String>() ;
		File file = new File(MConstent.unuploadPicDir) ;
		if(!file.exists()){
			return null ;
		}
		File[] files = file.listFiles() ;
		if(files != null){
			for(File f:files){
				Log.e("file", f.getName() ) ;
				listPic.add(f.getName()) ;

			}
		}
		return listPic ;
	}

	/***
	 * 获取已完成图片
	 * @return
	 */
	public static List<String> getUploadPic(){
		List<String> listPic = new ArrayList<String>() ;
		File file = new File(MConstent.uploadPicDir) ;
		if(!file.exists()){
			return null ;
		}
		File[] files = file.listFiles() ;
		if(files != null){
			for(File f:files){
				Log.e("file", f.getName() ) ;
				listPic.add(f.getName()) ;
			}
		}
		return listPic ;
	}

	/**
	 * 保存文件
	 */
	public static boolean  saveFile(String name, String content){
		try {
			File root = new File("mnt/sdcard/Scanftp/finish/") ;
			File file = new File("mnt/sdcard/Scanftp/" + name + ".txt") ;
			if(!root.exists()){
				root.mkdirs() ;
			}
			//如果配置文件存在
			if(!file.exists()){
				file.createNewFile() ;
			}

			FileWriter fw = new FileWriter(file) ;
			fw.write(content) ;
			fw.flush();
			fw.close() ;
		} catch (Exception e) {
			return false ;
		}
		return true ;
	}

	/**
	 * 检测是否存在config配置文件
	 * @param context
	 */
	public static void checkConfig(Context context){

		try {
			File root = new File("mnt/sdcard/Scanftp/config/") ;
			File file = new File("mnt/sdcard/Scanftp/config/config.txt") ;
			if(!root.exists()){
				root.mkdirs() ;
			}
			//如果配置文件存在
			if(file.exists()){
				return ;
			}
			file.createNewFile() ;
			InputStream in = context.getResources().openRawResource(R.raw.config);
			//获取文件的字节数
			int lenght = in.available();

			//创建byte数组
			byte[]  buffer = new byte[lenght];

			//将文件中的数据读到byte数组中
			in.read(buffer);
			String result = new String(buffer, "utf-8");
			FileWriter fw = new FileWriter(file) ;
			fw.write(result) ;
			fw.flush();
			fw.close() ;
			in.close() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取配置文件
	 * @return
	 */
	public static List<String> getConfigs(){
		List<String> list = null ;
		try {
			File root = new File("mnt/sdcard/Scanftp/config/") ;
			File file = new File("mnt/sdcard/Scanftp/config/config.txt") ;
			//如果配置文件不存在
			if(!file.exists()){
				return list;
			}
			BufferedReader br = new BufferedReader(new FileReader(file)) ;

			String temp = br.readLine() ;
			list = new ArrayList<String>() ;
			while(temp != null){
				String[] arrStr = temp.split("=") ;
				if(arrStr.length > 1){
					if(arrStr != null && arrStr.length >= 1){
						list.add(arrStr[1]) ;
					}
				}
				temp = br.readLine() ;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list ;
	}
}

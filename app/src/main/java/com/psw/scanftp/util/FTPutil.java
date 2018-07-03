package com.psw.scanftp.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class FTPutil {

	/**
	 * 向FTP服务器上传文件
	 *
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            端口默认80
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param path
	 *            FTP服务器保存目录，是linux下的目录形式,如/photo/
	 * @param filename
	 *            文件名称 上传到FTP服务器上的文件名,是自己定义的名字
	 * @param input
	 *            输入流
	 * @return
	 */
	public static boolean upload(String url, int port, String username,
								 String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			boolean isExist = ftp.changeWorkingDirectory(path);
			if(!isExist){
				ftp.makeDirectory(path) ;
				ftp.changeWorkingDirectory(path);
			}

			ftp.setBufferSize(1024);
			ftp.setControlEncoding("utf-8");
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//处理中文名称的文件名，如果不加这一句的话，中文命名的文件是不能上传的
			filename = new String(filename.getBytes("GBK"), "iso-8859-1") ;
			ftp.storeFile(filename, input);


			input.close();
			ftp.logout();
			success = true;
			Log.e("FTPutil", "success") ;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

}

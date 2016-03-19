package com.xgzhang.wechat.framework.tools;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtility {
	private static Logger logger = LoggerFactory.getLogger(IOUtility.class); 
	
	public static byte[] downloadImage(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();
			byte[] btImg = readInputStream(inStream);
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void writeImageToDisk(byte[] img, String fileName) {
		try {
			File file = new File(fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public static byte[] readImageFromDisc(String path){
		File file = new File(path);
		if(file.exists()){
			try{
				return readInputStream(new BufferedInputStream(new FileInputStream(file)));
			}catch(Exception e){
				logger.error(e.getMessage());
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}

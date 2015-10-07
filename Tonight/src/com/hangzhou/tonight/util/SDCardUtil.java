package com.hangzhou.tonight.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.os.Environment;

public class SDCardUtil {
	public final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/com.tonight/cache/download/";
	//public static final String FILE_KEY = "123456789abcde";
	
	
	 /**  
     * Get image from newwork  
     * @param path The path of image  
     * @return InputStream
     * @throws Exception  
     */
    public static InputStream getImageStream(String path) throws Exception{   
        URL url = new URL(path);   
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
        conn.setConnectTimeout(5 * 1000);   
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){   
            return conn.getInputStream();      
        }   
        return null; 
    }
    /**  
     * Get data from stream 
     * @param inStream  
     * @return byte[]
     * @throws Exception  
     */  
    public static byte[] readStream(InputStream inStream) throws Exception{   
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();   
        byte[] buffer = new byte[1024];   
        int len = 0;   
        while( (len=inStream.read(buffer)) != -1){   
            outStream.write(buffer, 0, len);   
        }   
        outStream.close();   
        inStream.close();   
        return outStream.toByteArray();   
    } 

    public static boolean exists(String fileName){
    	File file = new File(ALBUM_PATH + fileName);
    	if(file.exists()){
    		if(file.length() ==0){file.delete();}
    	}
    	return file.exists();
    }
    
    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile,true));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
}

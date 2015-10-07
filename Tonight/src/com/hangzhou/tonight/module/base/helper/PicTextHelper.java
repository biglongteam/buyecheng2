package com.hangzhou.tonight.module.base.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.hangzhou.tonight.util.FileUtils;
import com.hangzhou.tonight.util.MD5Utils;
import com.hangzhou.tonight.util.SDCardUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Html;

/**
 * 图文结合帮助类
 * @author hank
 *
 */
public class PicTextHelper implements Html.ImageGetter{

	@Override
	public Drawable getDrawable(String source) {
		Drawable drawable = null;  
        URL url;  
        InputStream in = null;
        try {  
        	String filename = MD5Utils.md5(source) + "." + FileUtils.getExtName(source, "jpg");
        	if(FileUtils.isSdcardExist() && !SDCardUtil.exists(filename)){
        		url = new URL(source);
        		in  = url.openStream();
        		//drawable = Drawable.createFromStream(in, "");
        		Bitmap bitmap = BitmapFactory.decodeStream(in);
        		SDCardUtil.saveFile(bitmap, filename);
        	}else{
        		File file = new File(SDCardUtil.ALBUM_PATH + filename);
    			in = new BufferedInputStream(new FileInputStream(file));
    			
        	}
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{ 
        	drawable = Drawable.createFromStream(in, "");
        	if(drawable != null)drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());  
        	if(null != in){ try { in.close(); } catch (IOException e) { e.printStackTrace(); }}
        }  
        return drawable;  
	}

}

package com.hangzhou.tonight.module.base.alioss;

import java.io.FileNotFoundException;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.hangzhou.tonight.base.BaseApplication;

public class GetAndUploadFile {

	private String TAG = this.getClass().getName();
	
	private OSSService ossService;
	private OSSBucket bucket;
	private Context context;
	
	/**
	 * 
	 * @param context
	 */
	public GetAndUploadFile(Context context){
		ossService = BaseApplication.ossService;
		bucket = ossService.getOssBucket(BaseApplication.bucketName);
		this.context = context;
	}
	
	/**
	 * 断点上传
	 * @param uploadfile 上传文件
	 * @param filename 服务端保存路径名称
	 */
	public void resumableUpload(String uploadfile,String filename) {
	        OSSFile bigfFile = ossService.getOssFile(bucket, filename);
	        try {
	            bigfFile.setUploadFilePath(uploadfile, "application/octet-stream");
	            bigfFile.ResumableUploadInBackground(new SaveCallback() {

	                @Override
	                public void onSuccess(String objectKey) {
	                    Log.d(TAG, "[onSuccess] - " + objectKey + " upload success!");
	                }

	                @Override
	                public void onProgress(String objectKey, int byteCount, int totalSize) {
	                    Log.d(TAG, "[onProgress] - current upload " + objectKey + " bytes: " + byteCount + " in total: " + totalSize);
	                }

	                @Override
	                public void onFailure(String objectKey, OSSException ossException) {
	                    Log.e(TAG, "[onFailure] - upload " + objectKey + " failed!\n" + ossException.toString());
	                    ossException.printStackTrace();
	                    //ossException.getException().printStackTrace();
	                }
	            });
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
}

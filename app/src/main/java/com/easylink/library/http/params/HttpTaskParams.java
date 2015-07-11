package com.easylink.library.http.params;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 该类用来封装网络请求的参数：例如：url，参数等
 * @author yhb
 */
public class HttpTaskParams {

	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;
	public static final int METHOD_UPLOAD = 3;
	
	private String mUrl;
	private int mMethod = METHOD_GET;
	private String mCacheKey;
	
	//保存 普通的键值对
	private List<NameValuePair> mStringParams;
	
	//保存 上传使用的字节数据
	private List<NameByteValuePair> mByteParams;
	
	//保存 上传的文件数据
	private List<NameFileValuePair> mFileParams;

	//设置 测试的返回值
	private Object mTestResult;
	
	/**
	 * 私有构造函数
	 * @param url
	 * @param method
	 */
	private HttpTaskParams(String url, int method){
		
		mUrl = url;
		mMethod = method;
		mStringParams = new ArrayList<NameValuePair>();
	}
	
	/*
	 * static new instance -------------------------------------------------
	 */
	
	public static HttpTaskParams newGet(String url){
		
		return new HttpTaskParams(url, METHOD_GET);
	}
	
	public static HttpTaskParams newPost(String url){
		
		return new HttpTaskParams(url, METHOD_POST);
	}
	
	public static HttpTaskParams newUpload(String url){
		
		return new HttpTaskParams(url, METHOD_UPLOAD);
	}
	
	/*
	 * params setter part ---------------------------------------------------------
	 */
	
	public void setStringParams(List<NameValuePair> valueParams){
		
		mStringParams = valueParams;
	}
	
	public void setByteParams(List<NameByteValuePair> byteParams){
		
		mByteParams = byteParams;
	}
	
	public void setFileParams(List<NameFileValuePair> fileParams){
		
		mFileParams = fileParams;
	}
	
	/*
	 * param adder part -----------------------------------------------------------
	 */
	
	public void addParam(String key, String value){
		
		if(mStringParams == null)
			mStringParams = new ArrayList<NameValuePair>();
		
		mStringParams.add(new BasicNameValuePair(key, value));
	}
	
	public void addByteParam(String key, byte[] value){
		
		if(mByteParams == null)
			mByteParams = new ArrayList<NameByteValuePair>();
		
		mByteParams.add(new NameByteValuePair(key, value));
	}
	
	public void addFileParam(String key, String filePath){
		
		if(mFileParams == null)
			mFileParams = new ArrayList<NameFileValuePair>();
		
		mFileParams.add(new NameFileValuePair(key, filePath));
	}
	
	/*
	 * cache setter part -------------------------------------------------------
	 */
	
	public void setCacheKey(String cacheKey){
		
		mCacheKey = cacheKey;
	}
	
	/*
	 * getter -------------------------------------------------------------------
	 */
	
	public String getUrl(){
		
		return mUrl;
	}
	
	public int getMethod(){
		
		return mMethod;
	}
	
	public boolean isGetMethod(){
		
		return mMethod == METHOD_GET;
	}
	
	public String getCacheKey(){
		
		return mCacheKey;
	}
	
	public List<NameValuePair> getStringParams(){
		
		return mStringParams;
	}
	
	public List<NameByteValuePair> getByteParams(){
		
		return mByteParams;
	}
	
	public List<NameFileValuePair> getFileParams(){
		
		return mFileParams;
	}

	public void setTestResult(Object obj){

		mTestResult = obj;
	}

	public Object getTestResult(){

		return mTestResult;
	}
}

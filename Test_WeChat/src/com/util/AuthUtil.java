package com.util;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import sun.net.httpserver.DefaultHttpServerProvider;
import net.sf.json.JSONObject;

public class AuthUtil {

	public static final String APPID = "wx329889481707f237";
	public static final String appsecret = "4440417c8fc60aee0c0b3955cc3a7763";
	
	public static final String DOMAINN_NAME = "fdbvzx.natappfree.cc";//域名
	public static final String REDIRECT_URL = "http://"+DOMAINN_NAME+"/Test_WeChat/codeCallback";

	
	/**
	 * 请求一个网址
	 * @param url
	 * @return url返回的字符串（JSON格式）
	 */
	public static String doGetJson(String url){
		StringBuilder sb = new StringBuilder();
		try {
			URL urlObj  = new URL(url);
			URLConnection conn = urlObj.openConnection();
			InputStream is = conn.getInputStream();
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b))!=-1) {
				sb.append(new String(b,0,len));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	/**
	 * （重新）获取基础接口支持的access_token
	 * @param accessTokenJSON
	 * @return access_token
	 */
	private static String getAccessTokenBase(){
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
				+ "&appid="+AuthUtil.APPID
				+ "&secret="+AuthUtil.appsecret;
		String access_tokenString = AuthUtil.doGetJson(url);
		JSONObject jsonObj = JSONObject.fromObject(access_tokenString);
		String access_token = jsonObj.getString("access_token");
		String expires_in = jsonObj.getString("expires_in");
		AccessTokenSingleton accessToken = AccessTokenSingleton.getInstance();
		accessToken.setAccessToken(access_token);
		accessToken.setExpiresTime(expires_in);
		System.out.println("基础接口支持的access_token:::初始"+access_token);
		return access_token;
	}
	
	
	/**
	 * 通过判断access_token是否过期，来决定是否需要重新获取access_token
	 * @return access_token
	 */
	public static String getAccessToken(){
		AccessTokenSingleton accessToken = AccessTokenSingleton.getInstance();
		String access_token = accessToken.getAccessToken();
		Long expiresTime = accessToken.getExpiresTime();
		Long nowTime = new Date().getTime();
		// 判断是否过期
		if (access_token != null && expiresTime != null
				&& nowTime - expiresTime < 3000 * 1000) {
			System.out.println("基础接口支持的access_token未过期:::"+access_token);
			return access_token;
		}else{
			
			return getAccessTokenBase();
		}

	}
}

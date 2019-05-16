package com.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class AccessTokenSingleton {

	private String access_token;
	private Long expiresTime;//access_token的过期时间
	
	
	private static AccessTokenSingleton accessToken = null;
	//构造方法私有
	private AccessTokenSingleton(){
	}
	
	//提供一个全局都能能访问的静态方法来创造实例
	public static AccessTokenSingleton getInstance(){
		if(accessToken == null){
			return accessToken = new AccessTokenSingleton();
		}
		return accessToken;
	}
	
	public String getAccessToken() {
		return access_token;
	}
	public void setAccessToken(String accessToken) {
		this.access_token = accessToken;
	}

	public Long getExpiresTime() {
		return expiresTime;
	}

	/**
	 * @param expires_In为有效时间。
	 */
	public void setExpiresTime(String expires_in) {
		this.expiresTime = new Date().getTime()+Long.parseLong(expires_in);
	}
	
	
}

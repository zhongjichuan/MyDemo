package com.util;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import net.sf.json.JSONObject;

/**
 * @author weichengpeng
 *
 */
public class SignUtil {
    public static void main(String[] args) {
        String jsapi_ticket = "jsapi_ticket";

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    };

    
    /**
     * 通过基础接口access_token获得jsapi_ticket
     * @param access_token
     * @return jsapi_ticket
     */
    public static String getjsapi_ticket(String access_token){
    	String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token
    			+ "&type=jsapi";
    	String jsapi_ticketString = AuthUtil.doGetJson(url);
    	JSONObject jsonObj = JSONObject.fromObject(jsapi_ticketString);
    	String jsapi_ticket = jsonObj.getString("ticket");
    	return jsapi_ticket;
    }
    
    /**
     * 将四个参数组合进行加密获得签名
     * @param jsapi_ticket，通过access_token请求网址得的
     * @param url，当前网页的URL，不包含#及其后面部分
     * @return wx.config的四个参数及当前网页URL
     */
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");//sha1加密
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("domainName", AuthUtil.DOMAINN_NAME);
        ret.put("appId", AuthUtil.APPID);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}

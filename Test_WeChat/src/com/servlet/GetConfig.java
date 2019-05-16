package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.util.AuthUtil;
import com.util.SignUtil;

/**
 * Servlet implementation class GetConfig
 */
public class GetConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetConfig() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageUrl = request.getParameter("pageUrl");
		System.out.println("doGet>>pageUrl>>>>>>>>>>>>>>>>>>>"+pageUrl);
		
		//获取的是基础支持的access_token
		PrintWriter printWriter = response.getWriter();
		String access_token = AuthUtil.getAccessToken();
		String jsapi_ticket = SignUtil.getjsapi_ticket(access_token);
		Map<String,String> configMap = SignUtil.sign(jsapi_ticket, pageUrl);
		JSONObject jsonObj = JSONObject.fromObject(configMap);
		System.out.println("configMap->jsonObj---------------->"+jsonObj);
		printWriter.write(jsonObj.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}

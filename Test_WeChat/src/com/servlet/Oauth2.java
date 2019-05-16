package com.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.AuthUtil;

/**
 * Servlet implementation class Oauth2
 */
public class Oauth2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String APPID = "wx329889481707f237";  
    private static final String appsecret = "4440417c8fc60aee0c0b3955cc3a7763";  
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Oauth2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Oauth2 Servlet++++++++++++++++++++++++++++++++++++++");
		
		String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID
				+ "&redirect_uri="+URLEncoder.encode(AuthUtil.REDIRECT_URL)
				+ "&response_type=code"
				+ "&scope=snsapi_userinfo"
				+ "&state=123#wechat_redirect ";
		//request.getRequestDispatcher(getCodeUrl).forward(request, response);
		response.sendRedirect(getCodeUrl);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

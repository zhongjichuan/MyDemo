package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





import net.sf.json.JSONObject;

import com.util.AuthUtil;

/**
 * Servlet implementation class codeCallback
 */
public class codeCallback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public codeCallback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 调用微信API获取code，回调到此路径
		String code = request.getParameter("code").toString();
		//通过code获取网页授权接口调用凭证access_token,openid
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AuthUtil.APPID
				+ "&secret="+AuthUtil.appsecret
				+ "&code="+code
				+ "&grant_type=authorization_code";
		String accessToken = AuthUtil.doGetJson(url);
		JSONObject jsonObject = JSONObject.fromObject(accessToken);
		System.out.println("获取网页授权接口调用凭证access_token,openid：："+jsonObject);
		String access = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");
		
		//再通过openid获取用户信息
		String userInfoURL = " https://api.weixin.qq.com/sns/userinfo?access_token="+access
				+ "&openid="+openid
				+ "&lang=zh_CN";
		String userInfo = AuthUtil.doGetJson(userInfoURL);
		JSONObject jsonObject_User = JSONObject.fromObject(userInfo);
		System.out.println("jsonObject_User信息------->" + jsonObject_User);
		String nickname = jsonObject_User.getString("nickname");
		String sex = "男";
		int sexNO = Integer.parseInt(jsonObject_User.getString("sex"));
		if(sexNO != 1)	
			sex = "女";
		String city = jsonObject_User.getString("city");
		String headimgurl = jsonObject_User.getString("headimgurl");
		session.setAttribute("nickname", nickname);
		session.setAttribute("sex", sex);
		session.setAttribute("city", city);
		session.setAttribute("headimgurl", headimgurl);
		
		//浏览器重定向可以防止表单重复提交
		response.sendRedirect("/Test_WeChat/index.jsp");
		//request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}

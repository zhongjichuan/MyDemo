package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.util.SendMessageUtil;

/**
 * Servlet implementation class SendMessages
 */
public class SendMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = request.getParameter("phone");
		JSONObject resultJson = SendMessageUtil.getCode(phone);
		PrintWriter printWriter = response.getWriter();
		HttpSession session = request.getSession();
		String verificationCode = resultJson.getString("verificationCode");
		session.setAttribute("verificationCode", verificationCode);
		System.out.println("Servlet中输的结果"+resultJson);
		printWriter.write(resultJson.toString());
	}

}

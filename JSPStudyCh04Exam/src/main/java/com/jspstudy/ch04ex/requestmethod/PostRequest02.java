package com.jspstudy.ch04ex.requestmethod;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/postRequest")
// POST 방식과 GET 방식 요청 모두 처리하기
public class PostRequest02 extends HttpServlet {
	
	// POST 방식 요청을 처리하기 위해서 doPost() 메서드를 오버라이딩 한다.
	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String gender = request.getParameter("gedner");
		String address = request.getParameter("address");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();		
		
		out.println("<h2>회원정보</h2>");
		out.println("이름 : " + name + "<br>");
		out.println("나이 : " + age + "<br>");
		out.println("성별 : " + gender + "<br>");
		out.println("주소 : " + address + "<br>");
		
		out.close();
	}
}

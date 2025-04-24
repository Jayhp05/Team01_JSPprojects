package com.jspstudy.ch04.urlmapping;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* ContextRoot(JSPStudyCh04/) 아래의 *.led, *.do로
 * 들어오는 모든 요청을 이 서블릿이 처리하겠다고 선언하는 애노테이션 
 * 문자열 배열 형태로 여러 개의 url-pattern을 지정할 수 있다.
 * */
@WebServlet({ "*.led", "*.do" }) // 확장자만 선택하는 것이라 '/' 주면 안됨.
public class MappingTest4 extends HttpServlet {

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<head><title>MappingTest4</title></head>");
		out.println("	<body>");
		out.println("		MappingTest4");
		out.println("	</body");
		out.println("/html>");
		
		out.close();
	}
}

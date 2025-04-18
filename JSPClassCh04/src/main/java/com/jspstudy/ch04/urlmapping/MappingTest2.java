package com.jspstudy.ch04.urlmapping;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* JSPStudyCh04/mapping2/ 아래로 들어오는
 * 모든 요청을 이 서블릿이 처리하겠다고 선언하는 애노테이션
 * */
@WebServlet("/mapping2/*")
public class MappingTest2 extends HttpServlet {

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<head><title>MappingTest2</title></head>");
		out.println("	<body>");
		out.println("		MappingTest2");
		out.println("	</body");
		out.println("/html>");
		
		out.close();
	}
}

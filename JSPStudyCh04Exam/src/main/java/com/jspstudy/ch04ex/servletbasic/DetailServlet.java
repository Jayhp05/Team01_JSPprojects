package com.jspstudy.ch04ex.servletbasic;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/now1")
public class DetailServlet extends HttpServlet {

//	get - doGet()
//	post - doPost()
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		요청 정보를 처리해야 하지만, 이 파일에서는 진행하지 x
//		응답 작성 - 문서의 형식, 인코딩
		resp.setContentType("text/html; charset=UTF-8");
		
//		클라이언트로 전송 - 스트림 PrintWriter
		PrintWriter out = resp.getWriter();
		
		out.print("<h2>Test Ch04 Servlet</h2>");
		out.print("이름 : 아무개<br>");
		out.print("나이 : 25<br>");
		out.print("주소 : 서울특별시 강동구");
		
		out.close();
	}

}

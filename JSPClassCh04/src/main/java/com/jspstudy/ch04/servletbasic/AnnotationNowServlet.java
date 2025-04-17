package com.jspstudy.ch04.servletbasic;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//	HttpServlet
@WebServlet({"/now", "/test"})
public class AnnotationNowServlet extends HttpServlet {

//	get - doGet()
//	post - doPost()
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		요청 정보를 처리해야 하지만, 이 파일에서는 진행하지 x
//		응답 작성 - 문서의 형식, 인코딩
		resp.setContentType("text/html; charset=UTF-8");
		
//		클라이언트로 전송 - 스트림 PrintWriter
		PrintWriter out = resp.getWriter();
		
		out.print("<h2>서블릿 작성하기</h2>");
		out.print("Annotation Servlet");
		
		out.close();
	}

}

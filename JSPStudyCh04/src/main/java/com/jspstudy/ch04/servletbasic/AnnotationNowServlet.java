package com.jspstudy.ch04.servletbasic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* 서블릿 3.0부터는 web.xml 파일 외에 애노테이션(Annotation) URL 맵핑 기술을
 * 지원 한다. 애노테이션은 자바 5.0부터 지원하는 기술로 서블릿 3.0부터 web.xml에 
 * 설정해야 하는 내용을 web.xml에 설정하지 않고 서블릿 클래스 위에 @WebServlet
 * 애노테이션을 사용해 서블릿을 등록하고 그 서블릿이 처리할 URL을 맵핑할 수 있다.
 * 서블릿 클래스에 @WebServlet("/now") 또는 @WebServlet(urlPatterns="/now")과
 * 같이 애노테이션을 지정하면 "http://URL/웹애플리케이션이름/now"로 접속되는
 * 요청을 AnnotationNowServlet이 실행될 수 있도록 맵핑해 준다.
 **/
@WebServlet("/now")
public class AnnotationNowServlet extends HttpServlet {
	
	// GET 방식의 요청을 처리하기 위해서 doGet() 메서드를 오버라이딩 한다.
	@Override
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		/* 웹 브라우저에 출력될 응답문서의 형식과 문자 셋을 지정
		 * ContentType 설정은 Response 객체로부터 PrintWriter 
		 * 객체를 얻기 전에 지정해야 한글이 깨지지 않고 제대로 동작한다.
		 **/
		response.setContentType("text/html; charset=UTF-8");
				
		// 요청에 대한 처리 결과를 웹 브라우저에 출력할 스트림객체 얻기
		PrintWriter out = response.getWriter();		
		
		/* HTML 문서 형식에 맞게 웹 브라우저로 전송할 데이터를 작성한다.
		 * HTML 문서 형식에 맞게 html 태그부터 제대로 작성해야 하지만 out.println()으로
		 * 출력되는 내용은 브라우저에 출력되는 내용이 되므로 기본 태그들은 생략했다. 
		 **/
		out.println("<h2>서블릿 애노테이션 등록하기</h2>");
		out.println("Hello Servlet");
		
		// 작업이 끝나면 스트림을 닫는다.
		out.close();
	}
}
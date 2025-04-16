package com.jspstudy.ch04.requestmethod;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// GET 방식 요청 처리하기
public class GetRequestServlet extends HttpServlet {
	
	// GET 방식의 요청을 처리하기 위해서 doGet() 메서드를 오버라이딩 한다.
	@Override
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		
		int firstNum = 0;
		int secondNum = 0;	
		
		/* 웹 브라우저가 GET 방식으로 요청한 파라미터 읽기
		 * 
		 * GET 방식의 요청은 웹 브라우저가 서버로 요청을 보낼 때 HTML 문서의 폼에 
		 * 입력된 데이터를 요청 URL의 뒷부분에 추가하여 서버로 요청을 보낸다.
		 * 클라이언트가 서버로 요청할 때 서버로 보내는 데이터를 요청 파라미터라고 한다.
		 * 
		 * 요청에 대한 정보를 저장하고 있는 HttpServletRequest 객체의 
		 * getParameter() 메서드를 이용해 아래와 같이 읽어올 요청 파라미터의
		 * 이름을 지정하면 클라이언트로부터 전송된 요청 파라미터를 읽을 수 있다.
		 **/
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		
		// 웹 브라우저에 출력될 응답문서의 형식과 문자 셋을 지정
		response.setContentType("text/html; charset=utf-8");		
		PrintWriter out = response.getWriter();		
		
		try {
			// 숫자 형태의 문자열이 아니면 NumberFormatException 발생
			firstNum =  Integer.parseInt(num1);
			secondNum = Integer.parseInt(num2);
			
			// 정상적인 처리시 웹 브라우저에 출력할 내용 작성
			out.println("<h2>GET 방식 요청 처리</h2>");
			out.println("첫 번째 입력 값 : " + firstNum + "<br/>");
			out.println("두 번째 입력 값 : " + secondNum + "<br/>");
			out.println("두 수를 곱한 값 : " + firstNum * secondNum + "<br/>");
			
		} catch(NumberFormatException e) {			
			// NumberFormatException이 발생할 시 웹 브라우저에 출력할 내용 작성			
			out.println("숫자가 아닌 데이터가 입력되었습니다.<br/>");
			out.println("첫 번째 입력 값 : " + num1 + ", 두 번째 입력 값 : " + num2);
			
		} finally {			
			// 정상 처리와 Exception 발생시 모두 연결된 스트림을 닫는다.
			out.close();
		}	
	}
}

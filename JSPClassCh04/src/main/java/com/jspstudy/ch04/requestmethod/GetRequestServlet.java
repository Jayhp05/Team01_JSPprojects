package com.jspstudy.ch04.requestmethod;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//	JSPClassCh04/getRequest
/*@WebServlet("/getRequest")*/
public class GetRequestServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		입력되는 값 => 파라미터
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		
//		db 저장, 어떤 처리 -> 두 수를 더한 결과 출력
		int firstNum = Integer.parseInt(num1);
		int secondNum = Integer.parseInt(num2);
		
//		웹 브라우저에 출력될 응답 형식을 지정
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<h2>GET 방식 요청 처리</h2>");
		out.print("첫 번째 입력 : " + firstNum + "<br>");
		out.print("두 번째 입력 : " + secondNum + "<br>");
		out.print("두 수를 곱한 값 : " + firstNum * secondNum + "<br>");
		
		out.close();
		
	}

}

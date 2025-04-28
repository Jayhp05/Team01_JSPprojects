package com.jspstudy.bbs.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/writeForm")
public class BoardWriteFormController extends HttpServlet {

	// get 방식의 요청을 처리하는 메소드
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		// 게시글 쓰기 폼 요청은 게시글 쓰기 폼만 화면에 보여주면 되므로 필요한
		// 모델 데이터가 없기 때문에 아무런 처리 없이 뷰 페이지로 포워딩 하면 된다.
		
		/* view 페이지로 제어를 이동해 요청에 대한 결과를 출력하기 위해
		 * HttpServletRequest 객체로부터 RequestDispatcher 객체를 구하고
		 * /WEB-INF/board/writeForm.jsp 페이지로 포워딩 한다. 
		 **/
		RequestDispatcher rd = 
				request.getRequestDispatcher("/WEB-INF/board/writeForm.jsp");
		rd.forward(request, response);
	}
}

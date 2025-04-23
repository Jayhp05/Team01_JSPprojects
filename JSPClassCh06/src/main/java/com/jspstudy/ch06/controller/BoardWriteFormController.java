package com.jspstudy.ch06.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/writeForm")
public class BoardWriteFormController extends HttpServlet {
	//	1. "글쓰기" 버튼이 클릭
	//	
	//	2. 게시글 쓰기 폼을 화면에 보인다
	//		- 게시글 쓰기 폼 요청을 처리하는 servlet
	//		- 게시글 쓰기 폼을 응답으로 보냄
	//	3.  사용자 폼을 보고 게시글을 쓴다
	//	4. 서브밋 버튼을 클릭하여 서버로 데이터를 보냄.
	//		- 폼에 데이터가 잘 들어가 있는지 유효성 검사
	//		- 유효성 검사를 통과 못하면 경고 창을 띄우고, 서브밋을 멈춤.
	//		- 유효성 검사를 통과하면 폼을 전송
	//	5. 폼에서 들어온 데이터를 받아서 DB에 저장한다.
	//		- writeProcess
	//		- DB에 저장한 후에 게시글 리스트로 리다이렉트 시킴.

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 화면에 출력할 데이터(모델)가 필요없음
		// 뷰로 제어를 이동함.
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/board/writeForm.jsp");
		rd.forward(req, resp);
	}
	
}

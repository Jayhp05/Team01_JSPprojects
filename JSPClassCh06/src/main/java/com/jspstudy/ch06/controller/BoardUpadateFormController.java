package com.jspstudy.ch06.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateForm")
public class BoardUpadateFormController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		post 요청방식의 문자셋 처리 - 그러나 최신 버전은 굳이 작성하지 않아도 됨.
		req.setCharacterEncoding("utf-8");
		
//		1. 수정 권한 체크 -> 비번 - no, pass 받기
		String sNo = req.getParameter("no");
		String pass = req.getParameter("pass");
		
//		2. 사용자가 입력한 비번과 DB 게시글의 비번이 맞는지 체크
		BoardDao dao = new BoardDao();
		
		int no = Integer.parseInt(sNo);
		boolean isPassCheck = dao.isPassCheck(no, pass);
		
//		틀리면 - 경고 창 띄우고 종료
		if(! isPassCheck) {

			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>");
			out.println(" alert('비밀번호가 다릅니다.');");
			out.println(" history.back();");
			out.println("</script>");
			out.close();
			
			return ;
		}
		
//		맞으면 - no에 해당하는 게시글 정보를 DB에서 읽어와 폼에 출력
		Board board = dao.getBoard(no);
		
		req.setAttribute("board", board);
		
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/board/updateForm.jsp");
		
		rd.forward(req, resp);
		
	}

}
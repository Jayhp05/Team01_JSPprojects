package com.jspstudy.ch06.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteProcess")
public class BoardDeleteController extends HttpServlet {

	//	5.  상세보기 에서 삭제하기 버튼이 클릭되어 들어온 데이터를 받아서 비밀번호 체크
	//		- DB에서 삭제한 후 게시글 리스트로 리다이렉트 시킴.
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
//		1. 수정 권한 체크 -> 비번 - no, pass 받기
		String sNo = req.getParameter("no");
		String pass = req.getParameter("pass");
		
		int no = Integer.parseInt(sNo);
		
//		2. 사용자가 입력한 비번과 DB 게시글의 비번이 맞는지 체크
		BoardDao dao = new BoardDao();
		
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
		
		// DB에서 삭제하고 리다이렉트 시킴
		dao.deleteBoard(no);
		 
		resp.sendRedirect("boardList");
	}

}

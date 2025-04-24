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

@WebServlet("/updateProcess")
public class BoardUpdateController extends HttpServlet {

	//	5. 폼에서 들어온 데이터를 받아서 DB에 저장한다.
	//		- writeProcess
	//		- 사용자 폼에 입력한 데이터를 받아서
	//		- DB에 저장한 후에 게시글 리스트로 리다이렉트 시킴.
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		String pass= null, title = null, writer = null, content = null;
		int no = 0;
		
//		1. 수정 권한 체크 -> 비번 - no, pass 받기
		no = Integer.parseInt(req.getParameter("no"));
		pass = req.getParameter("pass");
		
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
		
		title = req.getParameter("title");
		writer = req.getParameter("writer");
		content = req.getParameter("content");
		
		Board board = new Board();
		board.setNo(no);
		board.setTitle(title);
		board.setWriter(writer);
		board.setPass(pass);
		board.setContent(content);
		
		// BoardDao의 updateBoard() 메서드를 이용해 DB에서 게시 글을 수정한다.
		dao.updateBoard(board);
		 
		resp.sendRedirect("boardList");
	}

}

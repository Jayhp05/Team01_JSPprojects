package com.jspstudy.ch06.controller;

import java.io.IOException;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/writeProcess")
public class BoardWriteController extends HttpServlet {

	//	5. 폼에서 들어온 데이터를 받아서 DB에 저장한다.
	//		- writeProcess
	//		- 사용자 폼에 입력한 데이터를 받아서
	//		- DB에 저장한 후에 게시글 리스트로 리다이렉트 시킴.
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		String title = req.getParameter("title");
		String writer = req.getParameter("writer");
		String pass = req.getParameter("pass");
		String content = req.getParameter("content");
		
//		하나의 게시글 정보를 Board 객체로 담아서 전달
		 Board board = new Board();
		 board.setTitle(title);
		 board.setWriter(writer);
		 board.setPass(pass);
		 board.setContent(content);
		 
		 // BoardDao 객체 생성하고 게시 글을 DB에 추가한다.
		 BoardDao dao = new BoardDao();
		 dao.insertBoard(board);
		 
		 resp.sendRedirect("boardList");
	}

}

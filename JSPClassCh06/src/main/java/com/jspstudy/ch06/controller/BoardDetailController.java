package com.jspstudy.ch06.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/boardDetail")
public class BoardDetailController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
//		사용자가 몇 번 게시글을 보고 싶은지 파악
		String no = req.getParameter("no");
		
//		그 게시글을 DB에서 가져와서 뷰에 출력
//		DB 코드 = DAO를 이용해서 게시글 리스트 가져오면 됨.
		BoardDao dao = new BoardDao();
		
		// DB에 가서 no에 해당하는 게시글 하나를 읽어옴
		Board board = dao.getBoard(Integer.parseInt(no));
		
		// 뷰에 가서 출력할 결과 데이터 => 모델
		req.setAttribute("board", board);
		
		// 뷰로 제어를 이동함.
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/board/boardDetail.jsp");
		rd.forward(req, resp);
	}
	
}

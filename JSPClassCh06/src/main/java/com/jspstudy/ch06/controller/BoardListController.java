package com.jspstudy.ch06.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/boardList")
public class BoardListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		DB 코드 = DAO를 이용해서 게시글 리스트 가져오면 됨.
		BoardDao dao = new BoardDao();
		ArrayList<Board> bList = dao.boardList();
		
		// DB에 가서 게시글 리스트(모델)를 읽어왔음
		// 뷰에 가서 모델을 출력
		req.setAttribute("bList", bList);
		
		// 뷰로 제어를 이동함.
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/board/boardList.jsp");
		rd.forward(req, resp);
		
	} // end doGet()
	
} // end 

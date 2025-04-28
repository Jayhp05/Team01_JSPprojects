package com.jspstudy.bbs.controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

@WebServlet("/boardList")
public class BoardListController extends HttpServlet {

	// get 방식의 요청을 처리하는 메소드
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		// BoardDao 객체를 생성하고 데이터베이스에서 게시글 리스트를 읽어온다.
		BoardDao dao = new BoardDao();
		ArrayList<Board> bList = dao.boardList();	

		// 요청을 처리한 결과 데이터를 HttpServletRequest의 속성에 저장한다.
		request.setAttribute("bList", bList);
		
		/* view 페이지로 제어를 이동해 요청에 대한 결과를 출력하기 위해
		 * HttpServletRequest 객체로부터 RequestDispatcher 객체를 구하고
		 * /WEB-INF/board/boardList.jsp 페이지로 포워딩 한다. 
		 **/
		RequestDispatcher rd = 
				request.getRequestDispatcher("/WEB-INF/board/boardList.jsp");
		rd.forward(request, response);
	}
}

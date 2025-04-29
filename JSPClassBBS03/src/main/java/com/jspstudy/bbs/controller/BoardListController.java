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
	
//	한 페이지에 보여줄 게시글 수
	private static final int PAGE_SIZE = 10;

//	한 페이지에 보여줄 페이지 그룹의 수
//	[이전] 1 2 3 4 ... 10 [다음]
	private static final int PAGE_GROUP = 10;

	 // post 방식의 요청을 처리하는 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
		 
		 doGet(request, response);
	}
	
	// get 방식의 요청을 처리하는 메소드
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
//		몇 페이지를 원하는지 알아야
		String pageNum = request.getParameter("pageNum");
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		
//		1 - 1	2 - 11	3 - 21	7 - 61
//		1 - 10	2 - 20	3 - 30	7 - 70
//		숫자 1에 한페이지를 반환하게 해야됨. 1 => 1 ~ 10, 2 => 11 ~ 20 ...
		int startRow = currentPage * PAGE_SIZE - (PAGE_SIZE - 1);
		int endRow = startRow + PAGE_SIZE - 1;
		
		// BoardDao 객체를 생성하고 데이터베이스에서 게시글 리스트를 읽어온다.
		BoardDao dao = new BoardDao();
		ArrayList<Board> bList = null;
		int listCount = 0;
		
//		검색 게시글 리스트 - type. keyword 동시에 있으면
		boolean searchOption = (type == null || type.equals("") || keyword == null || keyword.equals("")) ? false : true;
		
		// 검색 요청이 아니면
		if(! searchOption) {			
//			일반 게시글 리스트
			bList = dao.boardList(startRow, endRow);
			listCount = dao.getBoardCount();
		} 
		else { // 검색 요청이면
			// 검색어에 해당하는 게시글 수를 구한다.
			bList = dao.searchList(type, keyword, startRow, endRow);
			listCount = dao.getBoardCount(type, keyword);
		}
		
		System.out.println("listCount : " + listCount);
		
//		전체 페이지 수 - 계산을 위해서 필요한 데이터
//		153 / 10 = 15 + 1 (어느 때 해야하는지)
		int pageCount = listCount / PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1);
		
//		페이지 네이션 시작 페이지
//		1 ~ 10 -> 1 | 11 ~ 20 -> 11 | 21 ~ 30 -> 21
//		1 / 10 * 10 = 0, 5 / 10 * 10 = 0		9 / 10 * 10 = 0 + 1
//		21 / 10 * 10 = 20, 25 / 10 * 10 = 20		29 / 10 * 10 = 20 + 1
//		10 / 10 * 10 + 1 = 11(1) 		30 / 10 * 10 + 1 = 31(21)
		int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1 - (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
		
//		페이지 네이션 마지막 페이지
		int endPage = startPage + PAGE_GROUP - 1;
		
//		전체 페이지가 17페이지 = endPage ?
		if(endPage > pageCount) {
			endPage = pageCount;
		}

		// 요청을 처리한 결과 데이터를 HttpServletRequest의 속성에 저장한다.
		request.setAttribute("bList", bList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageGroup", PAGE_GROUP);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("searchOption", searchOption);
		
		// 검색 요청이면 type과 keyword를 request 영역에 저장한다.
		if(searchOption) {
			request.setAttribute("keyword", keyword);
			request.setAttribute("type", type);
		}
		
		/* view 페이지로 제어를 이동해 요청에 대한 결과를 출력하기 위해
		 * HttpServletRequest 객체로부터 RequestDispatcher 객체를 구하고
		 * /WEB-INF/board/boardList.jsp 페이지로 포워딩 한다. 
		 **/
		RequestDispatcher rd = 
				request.getRequestDispatcher("/WEB-INF/board/boardList.jsp");
		rd.forward(request, response);
	}
}

package com.jspstudy.bbs.service;

import java.io.IOException;
import java.util.ArrayList;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//	게시글 리스트 요청을 처리하는 서비스 클래스
public class BoardListService {
	
	// 한 페이지에 보여 줄 게시글의 수를 상수로 선언하고 있다.
	private static final int PAGE_SIZE = 10;
	 
	/* 한 페이지에 보여 질 페이지 그룹의 수를 상수로 선언하고 있다.
	 * [이전] 1 2 3 4 5 6 7 8 9 10 [다음]	
	 **/
	private static final int PAGE_GROUP = 10;

//	게시글 리스트 요청을 처리하는 메서드
	public String requestProcess (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String pageNum = request.getParameter("pageNum");
		String type = request.getParameter("type");	
		String keyword = request.getParameter("keyword");
		
		/* pageNum이 null 이면 처음 게시글 리스트를 요청하거나 게시글 쓰기에서 
		 * Redirect 되어 넘어온 요청으로 pageNum을 1페이지로 설정한다.
		 * 즉 첫 번째 페이지에 해당하는 게시글 리스트를 화면에 출력한다. 
		 **/
		if(pageNum == null) {
			pageNum = "1";
		}

		// 요청 파라미터의 pageNum을 int 형으로 변환하여 현재 페이지로 설정
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = currentPage * PAGE_SIZE - (PAGE_SIZE - 1);
		//int startRow = (currentPage - 1) * PAGE_SIZE + 1;
		
		int endRow = startRow + PAGE_SIZE - 1;	
		int listCount = 0;	
		ArrayList<Board> bList = null;	
		
		// BoardDao 객체 생성 
		BoardDao dao = new BoardDao();
		
		
		/* 요청 파라미터에서 type이나 keyword가 비어 있으면 일반 
		 * 게시글 리스트를 요청하는 것으로 간주하여 false 값을 갖게 한다.
		 **/
		boolean searchOption = (type == null || type.equals("") 
				|| keyword == null || keyword.equals("")) ? false : true; 
		
		// 검색 요청이 아니면
		if(! searchOption) {
			// 전체 게시글 수를 구한다.
			listCount = dao.getBoardCount();
			bList = dao.boardList(startRow, endRow);
			
		} else { // 검색 요청이면
			// 검색어에 해당하는 게시글 수를 구한다.
			listCount = dao.getBoardCount(type, keyword);
			bList = dao.searchList(type, keyword, startRow, endRow);
		}
		System.out.println("listCount : " + listCount);
		
		int pageCount = listCount / PAGE_SIZE 
					+ (listCount % PAGE_SIZE == 0 ? 0 : 1);

		int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1 - (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
			
		int endPage = startPage + PAGE_GROUP - 1;
		
		if(endPage > pageCount) {
			endPage = pageCount;
		}
		
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
		
		return "board/boardList";
	}
}

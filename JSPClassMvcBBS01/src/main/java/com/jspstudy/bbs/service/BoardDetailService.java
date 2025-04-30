package com.jspstudy.bbs.service;

import java.io.IOException;
import java.io.PrintWriter;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardDetailService {
	
	public String requestProcess(
			
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//요청 파라미터로 넘어 온 게시글 번호와 페이지 번호를 읽어온다.
			String no = request.getParameter("no");
			String pageNum = request.getParameter("pageNum");
			String type = request.getParameter("type");
			String keyword = request.getParameter("keyword");
			 
			if(no == null || no.equals("") || pageNum == null || pageNum.equals("")) {
		 
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println(" alert('정상적인 접근이 아닙니다.');");
			out.println(" history.back();");
			out.println("</script>");
			 
			return null;
		}
	 
		boolean searchOption = (type == null || type.equals("") || keyword == null || keyword.equals("")) ? false : true;
		
		BoardDao dao = new BoardDao();
		Board board = dao.getBoard(Integer.valueOf(no), true);
		
		request.setAttribute("board", board);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchOption", searchOption);
		// 검색 요청이면 type과 keyword를 request 영역에 저장한다.
		if(searchOption) {
			request.setAttribute("type", type);
			request.setAttribute("keyword", keyword);
		}
	
		return "board/boardDetail";
	}
}

package com.jspstudy.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.BoardDao;

@WebServlet("/deleteProcess")
public class BoardDeleteController extends HttpServlet {

	// post 방식의 요청을 처리하는 메소드
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		//POST 요청방식의 문자 셋 처리
		request.setCharacterEncoding("utf-8");

	   /* 게시글을 삭제하기 위해서 boardDetail.jsp에서 게시글 삭제 요청을
		* 하면서 테이블에서 게시글 하나를 유일하게 구분할 수 있는 게시글 번호를
		* 요청 파라미터로 보냈기 때문에 이 게시글 번호를 요청 파라미터로부터 읽어
		* BoardDao를 통해서 게시글 번호에 해당하는 게시글을 DB에서 삭제할 수 있다.
		*
		* 아래에서 no라는 요청 파라미터가 없다면 NumberFormatException 발생
		**/	
		String sNo = request.getParameter("no");
		String pass = request.getParameter("pass");
		String pageNum = request.getParameter("pageNum");
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
			
		/* BoardDao 객체 생성하고 다시 한 번 게시글의
		 * 비밀번호를 체크해 맞지 않으면 이전으로 돌려보낸다.
		 **/
		BoardDao dao = new BoardDao();
		int no = Integer.parseInt(sNo);
		
		boolean isPassCheck = dao.isPassCheck(no, pass);	
		if(! isPassCheck) {

			/* 문자열을 보다 효율적으로 다루기 위해서 StringBuilder 객체를 이용해
			 * 응답 데이터를 작성하고 있다. 아래에서는 비밀번호가 틀리면 사용자에게
			 * 경고 창을 띄우고 브라우저의 history 객체를 이용해 바로 이전으로 
			 * 돌려보내기 위해서 자바스크립트로 응답을 작성하고 있다.
			 **/
			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("	alert('비밀번호가 맞지 않습니다.');");
			sb.append("	history.back();");
			sb.append("</script>");

			/* 응답 객체에 연결된 JspWriter 객체를 이용해 응답 데이터를 전송하고
			 * 더 이상 실행할 필요가 없으므로 return 문을 이용해 현재 메서드를 종료한다.
			 **/
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();			
			out.println(sb.toString());
			System.out.println("비밀번호 맞지 않음");
			return;
		}
		
		// BoardDao의 deleteBoard() 메서드를 이용해 DB에서 게시글을 삭제한다.
		dao.deleteBoard(no);
		
		boolean searchOption = (type == null || type.equals("") || keyword == null || keyword.equals("")) ? false : true; 
		
		String url = "boardList?pageNum=" + pageNum;

		if(searchOption) {
			keyword = URLEncoder.encode(keyword, "utf-8");
			url += "&type=" + type + "&keyword=" + keyword; 
		}
			
		response.sendRedirect("boardList");
	}
}

package com.jspstudy.bbs.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

@WebServlet("/updateForm")
public class BoardUpdateFormController extends HttpServlet {

	// post 방식의 요청을 처리하는 메소드
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		//POST 요청방식의 문자 셋 처리
		request.setCharacterEncoding("utf-8");

		/* 게시글 수정 폼에는 게시글 등록 폼과는 다르게 기존의 게시글 정보를 출력해야 한다.
		 * 테이블에서 하나의 게시글 정보를 읽어오기 위해서 boardDetail.jsp에서 
		 * 게시글 수정 폼 요청을 하면서 테이블에서 하나의 게시글을 유일하게 구분할 수 있는
		 * 게시글 번호를 요청 파라미터로 보냈기 때문에 이 게시글 번호를 요청 파라미터로 
		 * 부터 읽어 BoardDao를 통해서 게시글 번호에 해당하는 게시글을 읽을 수 있다.
		 *
		 * 아래에서 no라는 요청 파라미터가 없다면 NumberFormatException 발생
		 **/	
		String sNo = request.getParameter("no");
		String pass = request.getParameter("pass");
		String pageNum = request.getParameter("pagaNum");
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		
//		no, pageNum이 없으면 우리가 의도한 것이 아님
//		직접 응답을 만들어서 (js로 응답) 알림하고 게시글 리스트로 보냄
		if(sNo == null || sNo.equals("") || pass == null || pass.equals("") || pageNum == null || pageNum.equals("")) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("	alert('잘못된 접근입니다.');");
//			out.println("	history.back();");
			out.println("	location.href='boardList'");
			out.println("</script>");
		}
		
		boolean searchOption = (type == null || type.equals("") || keyword == null || keyword.equals("")) ? false : true;
		
		/* BoardDao 객체를 생성하고 DB에서 게시글 번호와 사용자가 입력한 게시글
		 * 비밀번호가 맞는지를 체크하여 맞으면 게시글 번호에 해당하는 게시글을 읽어온다.
		 **/	
		BoardDao dao = new BoardDao();
		int no = Integer.parseInt(sNo);
		boolean isPassCheck = dao.isPassCheck(no, pass);
		
		/* 게시글 번호에 해당하는 비밀번호가 틀리면 비밀번호가 틀리다고 경고 창을
		 * 띄우고 브라우저의 history 객체를 이용해 바로 이전으로 돌려보내기 위해서
		 * 자바스크립트로 응답을 작성해 클라이언트로 보낸다. 
		 **/
		if(!isPassCheck) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("	alert('비밀번호가 다릅니다.');");
			out.println("	history.back();");
			out.println("</script>");
			out.close();
			return;
		}
		
		/* 비밀번호가 맞으면 게시글 내용을 수정 폼에 출력하기 위해서
		 * BoardDao 객체를 이용해 no에 해당하는 게시글 정보를 읽어온다.
		 **/
		Board board = dao.getBoard(no);		
		
		// 요청을 처리한 결과 데이터를 HttpServletRequest의 속성에 저장한다.
//		뷰에 가서 출력할 결과 데이터 => 모델
		request.setAttribute("board", board);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchOption", searchOption);
		
		// 검색 요청이면 type과 keyword를 request 영역에 저장한다.
		if(searchOption) {
			request.setAttribute("type", type);
			request.setAttribute("keyword", keyword);
		}
		
		/* view 페이지로 제어를 이동해 요청에 대한 결과를 출력하기 위해
		 * HttpServletRequest 객체로부터 RequestDispatcher 객체를 구하고
		 * /WEB-INF/board/updateForm.jsp 페이지로 포워딩 한다. 
		 **/
		RequestDispatcher rd = 
				request.getRequestDispatcher("/WEB-INF/board/updateForm.jsp");
		rd.forward(request, response);
	}
}

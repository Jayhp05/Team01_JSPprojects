package com.jspstudy.ch06.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

@WebServlet("/updateProcess")
public class BoardUpdateController extends HttpServlet {

	// post 방식의 요청을 처리하는 메소드
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		// POST 방식의 요청에 대한 문자셋 처리
		request.setCharacterEncoding("utf-8");
		
		// 요청 파라미터를 저장할 변수 선언 
		String pass= null, title = null, writer = null, content = null;
		int no = 0;	

		/* 사용자가 폼에서 수정한 데이터를 요청 파라미터로부터 읽어온다.
		 *
		 * 게시 글을 수정하기 위해서 updateForm.jsp에서 게시 글 수정 요청을
		 * 하면서 테이블에서 게시 글 하나를 유일하게 구분할 수 있는 게시 글 번호를
		 * 요청 파라미터로 보냈기 때문에 이 게시 글 번호를 요청 파라미터로부터 읽어
		 * BoardDao를 통해서 게시 글 번호에 해당하는 게시 글의 내용을 수정 할 수 있다.
		 *
		 * 아래에서 no라는 요청 파라미터가 없다면 NumberFormatException 발생
		 **/	 
		no = Integer.parseInt(request.getParameter("no"));		
		pass = request.getParameter("pass");
		
		// BoardDao 객체를 생성하고 게시 글 비밀번호를 체크해 맞지 않으면 이전으로 돌려보낸다.
		BoardDao dao = new BoardDao();	
		boolean isPassCheck = dao.isPassCheck(no, pass);
		
		// 게시 글 번호에 해당하는 게시글 비밀번호가 틀리다면 
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

			/* 응답 객체에 연결된 PrintWriter 객체를 이용해 응답 데이터를 전송하고
			 * 더 이상 실행할 필요가 없으므로 return 문을 이용해 현재 메서드를 종료한다.
			 **/
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(sb.toString());
			System.out.println("비밀번호 맞지 않음");
			return;
		} 
		
		// 비밀번호가 맞으면 사용자가 게시 글 수정 폼에 입력한 데이터를 읽어온다.
		title = request.getParameter("title");
		writer = request.getParameter("writer");		
		content = request.getParameter("content");
		
		/* 하나의 게시 글 정보를 저장하는 자바빈 객체를 생성하고 파라미터로
		 * 넘겨받은 요청 데이터를 Board 객체에 저장한다.
		 **/	 
		Board board = new Board();
		board.setNo(no);
		board.setTitle(title);
		board.setWriter(writer);
		board.setPass(pass);
		board.setContent(content);
		
		// BoardDao의 updateBoard() 메서드를 이용해 DB에서 게시 글을 수정한다.	
		dao.updateBoard(board);	
		
		/* 게시 글 수정이 완료된 후 response 내장객체의 sendRedirect() 메서드를
		 * 이용해 게시 글 리스트로 Redirect 시킨다. response 내장객체의 sendRedirect()
		 * 메서드는 요청한 자원이 다른 곳으로 이동되었다고 웹브라우저에게 응답하면서
		 * 이동할 URL을 알려주고 그 쪽으로 다시 요청하라고 응답하는 메소드이다. 
		 * 웹브라우저가 요청한 컨텐츠가 다른 곳으로 이동되었다고 응답하면서 그 쪽으로
		 * 다시 요청하라고 이동할 주소를 웹브라우저에게 알려주면 웹브라우저는 그 주소로
		 * 다시 요청하게 되는데 이를 리다이렉션이라고 한다.
		 *	 
		 * Redirect 기법은 웹 브라우저를 새로 고침(F5) 했을 때 동일한 코드가 다시
		 * 실행되면 문제가 될 수 있는 경우 클라이언트의 요청을 처리한 후 특정 URL로
		 * 이동시키기 위해 사용하는 기법이다. 예를 들어 게시 글 수정하기 요청을 처리한
		 * 후 Redirect 시키지 않으면 게시 글 수정 후에 사용자가 새로 고침(F5) 동작을
		 * 하면 바로 이전에 수정한 게시 글 내용과 동일한 내용을 다시 DB에 수정하는 작업을 
		 * 하게 되는데 이렇게 되면 계속해서 같은 데이터를 수정하려고 하는 문제가 발생한다.
		 * 이를 방지하기 위해서 게시 글 수정이 완료되면 게시 글 리스트(select 문은 반복
		 * 사용해도 중복된 데이터가 발생하지 않음)로 이동시키기 위해서 response 
		 * 내장객체의 sendRedirect() 메소드를 사용해 게시 글 리스트의 URL을
		 * 웹 브라우저에게 응답하고 웹 브라우저는 응답 받은 URL로 다시 요청하도록 하는
		 * 것이다. 이렇게 게시 글 수정과 같이 DB 입력 작업이 연동되는 경우 사용자의
		 * 새로 고침(F5) 동작에 의해 동일한 요청이 다시 발생하여 DB에서 이미 수정된 
		 * 게시 글을 수정하거나 SQLException을 발생 시킬 수 있어 Redirect 기법을
		 * 사용한다. 이외에 다른 사이트로 이동시킬 때 Redirect 기법을 사용 한다.
		 **/	
		response.sendRedirect("boardList");
	}
}

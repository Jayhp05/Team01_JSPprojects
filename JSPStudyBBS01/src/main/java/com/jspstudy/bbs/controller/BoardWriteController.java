package com.jspstudy.bbs.controller;

import java.io.IOException;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/writeProcess")
public class BoardWriteController extends HttpServlet {

	// post 방식의 요청을 처리하는 메소드
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

		//POST 요청방식의 문자 셋 처리
		request.setCharacterEncoding("utf-8");
		
		/* 사용자가 폼에 입력한 데이터 읽어오기
		 * HttpServletRequest 객체를 통해 파라미터를 읽어 변수에 저장한다. 
		 **/	
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String pass = request.getParameter("pass");
		String content = request.getParameter("content");		
		
		/* 하나의 게시글 정보를 저장하는 자바빈 객체를 생성하고 요청 파라미터로 받은
		 * 데이터를 Board 객체에 저장한다.
		 **/
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setPass(pass);	
		board.setContent(content);	
		
		// BoardDao 객체 생성하고 게시글을 DB에 추가한다.
		BoardDao dao = new BoardDao();
		dao.insertBoard(board);
		
		/* 게시글 쓰기가 완료된 후 response 내장객체의 sendRedirect() 메서드를
		 * 이용해 게시글 리스트로 Redirect 시킨다. response 내장객체의 sendRedirect()
		 * 메서드는 요청한 자원이 다른 곳으로 이동되었다고 웹브라우저에게 응답하면서
		 * 이동할 URL을 알려주고 그 쪽으로 다시 요청하라고 응답하는 메소드이다.
		 * 웹 브라우저가 요청한 컨텐츠가 다른 곳으로 이동되었다고 응답하면서 그 쪽으로
		 * 다시 요청하라고 이동할 주소를 웹브라우저에게 알려주면 웹브라우저는 그 주소로
		 * 다시 요청하게 되는데 이를 리다이렉션이라고 한다.
		 *	 
		 * Redirect 기법은 웹 브라우저를 새로 고침(F5) 했을 때 동일한 코드가 다시
		 * 실행되면 문제가 될 수 있는 경우 클라이언트의 요청을 처리한 후 특정 URL로
		 * 이동시키기 위해 사용하는 기법이다. 예를 들어 게시글 쓰기에 대한 요청을 처리한
		 * 후 Redirect 시키지 않으면 게시글 쓰기 후에 사용자가 새로 고침(F5) 동작을
		 * 하면 바로 이전에 작성한 게시글 내용과 동일한 내용을 다시 DB에 등록하는 작업을 
		 * 하게 되는데 이렇게 되면 중복된 데이터를 계속해서 저장하는 문제가 발생한다.
		 * 이를 방지하기 위해서 게시글 쓰기가 완료되면 게시글 리스트(select 문은 반복
		 * 사용해도 중복된 데이터가 발생하지 않음)로 이동시키기 위해서 response 
		 * 내장객체의 sendRedirect() 메소드를 사용해 게시글 리스트의 URL을
		 * 웹 브라우저에게 응답하고 웹 브라우저는 응답 받은 URL로 다시 요청하도록 하는
		 * 것이다. 이렇게 게시글 쓰기와 같이 DB 입력 작업이 연동되는 경우 사용자의
		 * 새로 고침(F5) 동작에 의해 동일한 요청이 다시 발생하여 DB에 입력되는 데이터의 
		 * 중복이 발생하거나 SQLException을 발생 시킬 수 있어 Redirect 기법을
		 * 사용한다. 이외에 다른 사이트로 이동시킬 때 Redirect 기법을 사용 한다.
		 **/
		response.sendRedirect("boardList");
	}
}

package com.jspstudy.bbs.ajax;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// ajax 요청을 처리하는 Controller 클래스 
@WebServlet(name="ajaxController", urlPatterns="*.ajax")
public class AjaxController extends HttpServlet {
	
	 
	/* doGet(), doPost()에서 호출하는 메소드
	 * 즉 Ajax로 들어오는 GET 방식과 POST방식 모두를 처리할 메서드 이다. 
	 * 컨트롤러는 이 메소드 안에서 브라우저의 요청에 대한 처리를 요청 URL을 분석해
	 * 요청을 처리할 모델 클래스를 결정하고 해당 모델 클래스의 객체를 사용해(위임)
	 * 클라이언트의 요청을 처리한 후 그 결과를 뷰로 전달해 결과 화면을 만들게 된다.
	 * 뷰로 전달된 데이터는 html 형식의 문서에 출력하여 브라우저에게 응답한다.
	 **/
	public void doAjax(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		
		/* 모든 모델 클래스의 슈퍼 인터페이스인 CommandProcess 타입의
		 * 변수를 선언 하고 null로 초기화 한다.
		 **/
		AjaxProcess ajaxAction = null;
		
		/* 요청 정보를 담고 있는 Request 객체로부터 요청 URI를 구한다.
		 * /JSPStudyMvcBBS03/passCheck.ajax
		 **/ 
		String command = request.getRequestURI();
		
		/* 요청 정보를 담고 있는 Request 객체로부터 ContextPath를 구하고 
		 * /JSPStudyMvcBBS03
		 *
		 * 요청 URI에서 ContextPath를 제외한 요청 명령을 추출 한다.
		 * /passCheck.ajax
		 **/
		command = command.substring(request.getContextPath().length());	
		System.out.println("command : " + command);
		
		/* 요청 URI에서 추출한 명령을 비교해 요청을 처리할 모델 클래스를
		 * 결정하여 객체를 생성하고 메서드를 호출해 요청을 처리한다.
		 **/
		if(command.equals("/passCheck.ajax")) {
				
			/* 회원정보 수정시 비밀번호 체크 요청이 들어오는 경우 처리
			 * 회원 id에 해당하는 pass가 맞는지를 체크해 주는 
			 * PassCheckAction 클래스의 인스턴스를 생성한 후 Request와
			 * Response 객체를 매개변수로 ajaxProcess()를
			 * 호출하여 회원 비밀번호가 맞는지에 대한 요청을 처리 한다.
			 **/
			ajaxAction = new PassCheckAction();
			ajaxAction.ajaxProcess(request, response);
			
		} else if(command.equals("/recommend.ajax")) {
			
			/* 추천과 땡큐 요청이 들어오는 경우 처리
			 * 게시글 번호에 해당하는 추천과 땡큐 요청을 처리하는  
			 * RecommendAction 클래스의 인스턴스를 생성한 후 Request와
			 * Response 객체를 매개변수로 ajaxProcess()를
			 * 호출하여 추천과 땡큐에 대한 요청을 처리 한다.
			 **/
			ajaxAction = new RecommendAction();
			ajaxAction.ajaxProcess(request, response);
			
		} else if(command.equals("/replyWrite.ajax")) {
			
			/* 댓글 쓰기 요청이 들어오는 경우 처리
			 * 댓글 쓰기 요청을 처리하는 ReplyWriteAction 클래스의 
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * ajaxProcess()를 호출하여 댓글 쓰기에 대한 요청을 처리 한다.
			 **/
			ajaxAction = new ReplyWriteAction();
			ajaxAction.ajaxProcess(request, response);
			
		}  else if(command.equals("/replyUpdate.ajax")) {
			
			/* 댓글 수정하기 요청이 들어오는 경우 처리
			 * 댓글 수정하기 요청을 처리하는 ReplyUpdateAction 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * ajaxProcess()를 호출하여 댓글 쓰기에 대한 요청을 처리 한다.
			 **/
			ajaxAction = new ReplyUpdateAction();
			ajaxAction.ajaxProcess(request, response);
			
		}  else if(command.equals("/replyDelete.ajax")) {
			
			/* 댓글 수정하기 요청이 들어오는 경우 처리
			 * 댓글 수정하기 요청을 처리하는 ReplyDeleteAction 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * ajaxProcess()를 호출하여 댓글 쓰기에 대한 요청을 처리 한다.
			 **/
			ajaxAction = new ReplyDeleteAction();
			ajaxAction.ajaxProcess(request, response);
		}
	}
		
	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		
		doAjax(request, response);
	}
	
	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		
		doAjax(request, response);
	}
}

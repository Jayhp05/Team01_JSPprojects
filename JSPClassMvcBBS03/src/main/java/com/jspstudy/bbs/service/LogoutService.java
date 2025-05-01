package com.jspstudy.bbs.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 회원 로그아웃 요청을 처리하는 모델 클래스
public class LogoutService implements CommandProcess {

	@Override
	public String requestProcess(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		/* request 객체로부터 HttpSession 객체를 구해 현재 세션을 삭제한다.
		 * 현재 세션이 삭제되기 때문에 세션 영역에 저장된 모든 데이터가 삭제된다.
		 **/
		HttpSession session = request.getSession();	
		session.invalidate();
		
		/*
		// 아래와 같이 세션에 저장된 개별 속성만 삭제 할 수 있다.
		session.removeAttribute("isLogin");
		session.removeAttribute("id");
		*/	
		
		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 *	
		 * 현재 요청을 처리한 후에 Redirect 하려면 뷰 페이지를 지정하는 문자열 맨 앞에
		 * "r:" 또는 "redirect:"를 접두어로 붙여서 반환하고 Redirect가 아니라 Forward
		 * 하려면 뷰 페이지의 경로만 지정하여 문자열로 반환하면 Controller에서 판단하여
		 * Redirect 또는 Forward로 연결된다. 
		 * 
		 * 로그아웃 요청을 처리하고 Redirect 기법을 사용해 게시글 리스트 페이지로
		 * 이동시키기 위해서 View 페이지 정보를 반환할 때 맨 앞에 "r:" 접두어를 붙여서
		 * 게시글 리스트 보기 요청을 처리하는 URL를 지정하여 Controller로 넘기면
		 * Controller는 넘겨받은 View 페이지 정보를 분석하여 Redirect 시키게 된다.
		 * 
		 * Redirect는 클라이언트 요청에 대한 결과 페이지가 다른 곳으로 이동되었다고 
		 * 브라우저에게 알려주고 그 이동된 주소로 다시 요청하라고 브라우저에게 URL을
		 * 보내서 브라우저가 그 URL로 다시 응답하도록 처리하는 것으로 아래와 같이
		 * View 페이지 정보의 맨 앞에 "r:" 또는 "redirect:"를 접두어로 붙여서 반환하면
		 * Controller에서 View 페이지 정보를 분석해 Redirect 시키고 이 응답을 받은
		 * 브라우저는 게시글 리스트를 보여주는 페이지를 다시 요청하게 된다. 
		 *
		 * 지금과 같이 리다이렉트를 해야 할 경우 웹브라우저가 다시 요청할 주소만 응답하고
		 * 웹브라우저에서는 이 주소로 재요청하는 동작을 하므로 웹 템플릿 페이지인
		 * index.jsp를 기준으로 뷰 페이지를 지정하면 안 된다. 왜냐하면 리다이렉트는
		 * 뷰 페이지를 거쳐서 클라이언트로 응답되는 것이 아니라 현재 클라이언트가 요청한
		 * 주소가 다른 곳으로 이동되었다고 알려주기 위해 웹브라우저가 이동할 주소만
		 * 응답하고 웹 브라우저는 서버로부터 응답 받은 주소로 다시 요청하는 동작을 하기
		 * 때문에 뷰 페이지의 정보가 아닌 웹 브라우저가 이동할 주소를 지정해야 한다.
		 **/	
		return "r:boardList.mvc";
	}
}

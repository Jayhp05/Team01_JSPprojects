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
			
		return "r:boardList.mvc";
	}
}

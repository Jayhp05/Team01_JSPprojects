package com.jspstudy.bbs.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.jspstudy.bbs.dao.MemberDao;
import com.jspstudy.bbs.vo.Member;

// 회원정보 수정 폼 요청을 처리하는 모델 클래스
public class MemberUpdateFormService implements CommandProcess {
	
	public String requestProcess(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		
//		필요한 데이터는 로그인할 때 저장한 세션에서 가져온다.
		
		/* Request 객체로부터 세션 객체를 얻어 회원 로그인 또는
		 * 회원 가입시 세션 영역의 속성에 저장된 회원의 id를 읽어온다.
		 **/
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		
		/* 회원 정보를 테이블로부터 읽어오기 위해 MemberDAO 객체를 얻어
		 * 회원 테이블에서 id에 해당하는 회원 정보를 읽어온다.
		 **/ 
		MemberDao dao = new MemberDao();
		Member member = dao.getMember(id);
		
		// Request 영역의 속성에 테이블로부터 읽어온 회원 정보를 저장 한다.
		session.setAttribute("member", member);
	
		return "member/memberUpdateForm";
	}
}

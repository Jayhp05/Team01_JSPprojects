package com.jspstudy.bbs.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.MemberDao;

// 회원정보 수정 폼에서 회원의 비밀번호 확인을 Ajax로 처리하는 모델 클래스
public class PassCheckAction implements AjaxProcess {

	@Override
	public void ajaxProcess(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		response.setContentType("text/html; charset=utf-8"); 
		//response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(id == null || pass == null) {
			
			/* 클라이언트에서 자바스크립트 문장으로 해석해 주는 
			 * eval() 함수를 사용하기 때문에 script 구문은 기술하지 않았다.
			 **/			
			out.println("	alert('정상적인 접근이 아닙니다.');");
			//out.println("	window.history.back();");			
			return;
		}
		
		/* 회원 로그인을 처리하기 위해 MemberDAO 객체를 얻어
		 * 회원 테이블의 회원 정보와 비교하여 가입된 회원이 아니면 -1을
		 * 로그인 성공 이면 1을 비밀번호가 맞지 않으면 0을 리턴 받는다.
		 **/ 
		MemberDao dao = new MemberDao();
		int result = dao.checkMember(id, pass);
		
		/* 응답 데이터를 json 타입으로 작성한다.
		 * 클라이언트 쪽에서 json 형식의 데이터를 받아 eval() 함수를 사용해
		 * 자바스크립트 객체로 다루기 위해 json 형식의 데이터를 ()로 감싸야 한다. 
		 * 그렇지 않으면 Syntax 에러가 발생하기 쉽다. eval() 함수는 아래 코드와 같이 
		 * json 형식으로 보낸 데이터를 자바스크립트 블록 구문으로 해석해서 
		 * 라벨 recommend와 라벨 thank가 존재하는 문장으로 해석하기 때문에
		 * json 형식의 데이터를 객체 리터럴로 인식시키기 위해서는 ()로 감싸야 한다. 
		 **/
		String message = "({ 'result' : '" + result + "' })";
		out.println(message);
	}
}

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
		
		//response.setContentType("text/html; charset=utf-8"); 
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 응답 데이터에 사용할 StringBuilder 객체 생성
		StringBuilder sb = new StringBuilder();
		
		if(id == null || pass == null) {
			
			sb.append("<script>");
			sb.append("	alert('정상적인 접근이 아닙니다.');");
			sb.append("</script>");
			
			/* Response 객체에 연결된 출력 스트림에 
			 * 자바스크립트 문자열을 출려하고 메서드를 종료한다.  
			 **/			
			out.println(sb.toString());
			return;
		}
		
		/* id에 해당하는 pass가 맍는지를 처리하기 위해 MemberDAO 객체를
		 * 구해 회원 테이블의 회원 정보와 비교하여 가입된 회원이 아니면 -1을
		 * 로그인 성공 이면 1을 비밀번호가 맞지 않으면 0을 반환 받는다.
		 **/ 
		MemberDao dao = new MemberDao();		
		int result = dao.checkMember(id, pass);
		
		/* StringBuilder를 이용해 응답 데이터를 json 타입으로 작성한다.
		 * 클라이언트 쪽에서 json 형식의 데이터를 받아 자바스크립트 객체로
		 * 다룰수 있도록 StringBuilder의 toString() 메서드를 이용해
		 * 객체를 직렬화 해서 json 형식의 문자열로 응답하고 있다.
		 * 
		 * 클라이언트(jQuery)에서 JSON 데이터로 파싱하기 위해서는 JSON 형식의
		 * 문자열 데이터를 만들 때 아래와 같이 작성되어야 제대로 해석 할 수 있다.
		 *
		 * "{"속성 이름": "속성의 값"}" 와 같이 안쪽의 속성 이름과 속성의 
		 * 값이 반드시 쌍 따옴표("")로 감싸서 작성해야 제대로 해석할 수 있다.
		 **/
		sb.append("{ \"result\" : \"" + result + "\" }");		
		out.println(sb.toString());
	}
}

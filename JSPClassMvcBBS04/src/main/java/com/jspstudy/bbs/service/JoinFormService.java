package com.jspstudy.bbs.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 회원가입 폼 요청을 처리하는 모델 클래스
public class JoinFormService implements CommandProcess {
	
	public String requestProcess(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {				
		
		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 * 
		 * 회원 가입 폼 요청을 처리하는 모델 클래스는 회원 정보를 입력하는 폼만
		 * 보여주면 되므로 화면에 출력할 모델 데이터가 필요없다. 그러므로 회원 정보를
		 * 입력하는 폼에 대한 View 정보만 반환하면 된다.
		 *
		 * 요청에 대한 결과(모델)를 출력할 View 페이지와 View 페이지를 호출하는 방식을
		 * 아래와 같이 문자열로 지정하면 된다. 현재 요청을 처리한 후에 Redirect 하려면
		 * 뷰 페이지를 지정하는 문자열 맨 앞에 "r:" 또는 "redirect:"를 접두어로 붙여서
		 * 반환하고 Redirect가 아니라 Forward 하려면 뷰 페이지의 경로만 지정하여
		 * 문자열로 반환하면 Controller에서 판단하여 Redirect 또는 Forward로 연결된다.   
		 * 또한 Forward 할 때 뷰 페이지의 정보 중에서 앞부분과 뒷부분에서 중복되는 
		 * 정보를 줄이기 위해서 Controller에서 PREFIX와 SUFFIX를 지정해 사용하기
		 * 때문에 매번 중복되는 부분을 제외하고 뷰 페이지의 정보를 지정하면 된다.
		 *  
		 * 웹 템플릿을 적용하여 뷰를 만드는 경우 Controller에서 PREFIX에 웹 템플릿의
		 * 위치가 지정되어 있으므로 PREFIX와 SUFFIX를 제외하고 뷰의 정보를 지정하면
		 * 되지만 만약 웹 템플릿을 적용하지 않고 별도로 뷰를 만드는 경우에는 Forward 
		 * 할 때 PREFIX가 적용되지 않도록 Controller에 알려주기 위해서 아래 주석으로
		 * 처리한 return 문과 같이 뷰 페이지 정보를 지정하는 문자열의 맨 앞에 "f:" 또는
		 * "forward:"를 접두어로 붙여서 반환하면 된다.  
		 **/
		// return "f:/WEB-IN/member/overlapidCheck.jsp"	
		return "member/memberJoinForm";
	}
}

package com.jspstudy.bbs.service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.jspstudy.bbs.dao.MemberDao;
import com.jspstudy.bbs.vo.Member;

/* 회원가입 폼에서 사용자가 입력한 데이터를 받아서
 * 회원정보를 DB에 저장하여 회원가입 요청을 처리하는 모델 클래스
 * 회원 가입이 완료되면 로그인 처리도 같이 한다.
 **/
public class JoinResultService implements CommandProcess {
	
	public String requestProcess(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {		
		
		// 회원 가입 폼으로부터 전달된 파라미터를 읽어 변수에 저장 한다.
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass1");
		String emailId = request.getParameter("emailId");
		String emailDomain = request.getParameter("emailDomain");
		String mobile1 = request.getParameter("mobile1");
		String mobile2 = request.getParameter("mobile2");
		String mobile3 = request.getParameter("mobile3");
		String zipcode = request.getParameter("zipcode");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String emailGet = request.getParameter("emailGet");		
		
		/* MemberBean 인스턴스를 생성하여 
		 * 회원 가입 폼으로부터 넘어온 데이터를 저장 한다. 
		 **/	
		Member member = new Member();
		member.setId(id);
		member.setName(name);
		member.setPass(pass);
		member.setEmail(emailId + "@" + emailDomain);
		member.setMobile(mobile1 + "-" + mobile2 + "-" + mobile3);
		member.setZipcode(zipcode);
		member.setAddress1(address1);
		member.setAddress2(address2);		
		
		if(phone2.equals("") || phone3.equals("")) {			
			member.setPhone("");
			
		} else {			
			member.setPhone(phone1 + "-" + phone2 + "-" + phone3);
		}
		
		member.setEmailGet(Boolean.valueOf(emailGet));		

		
		/* 회원 가입을 처리하기 위해 MemberDAO 객체를 얻어
		 * 회원 테이블에 새로운 회원 정보를 추가 한다.
		 **/ 
		MemberDao dao = new MemberDao();
		dao.joinMember(member);		
		
		/* request 객체로부터 HttpSessiion 객체를 구하고
		 * 세션 영역의 속성에 id와 로그인 상태 정보를 저장하여 로그인 처리 한다.
		 **/
		HttpSession session = request.getSession();
		session.setAttribute("id", member.getId());
		session.setAttribute("isLogin", true);

		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 * 
		 * 회원가입 요청을 처리하고 Redirect 시키지 않으면 사용자가 브라우저를
		 * 새로 고침 하거나 재요청할 때 마다 이미 가입된 회원정보를 계속 추가하려는
		 * 동작으로 인해서 중복된 데이터가 저장되거나 또 다른 문제가 발생할 수 있다.
		 * 이런 경우에는 Redirect 기법을 이용해 DB에 추가, 수정, 삭제하는 동작이 아닌
		 * 조회하는 곳으로 이동하도록 하면 문제를 해결 할 수 있다. 
		 * 
		 * 현재 요청을 처리한 후에 Redirect 하려면 뷰 페이지를 지정하는 문자열 맨 앞에
		 * "r:" 또는 "redirect:"를 접두어로 붙여서 반환하고 Redirect가 아니라 Forward
		 * 하려면 뷰 페이지의 경로만 지정하여 문자열로 반환하면 Controller에서 판단하여
		 * Redirect 또는 Forward로 연결된다. 
		 * 
		 * 회원가입 폼으로부터 넘어온 신규 회원정보를 DB에 저장한 후 게시글 리스트
		 * 페이지로 이동시키기 위해 View 페이지 정보를 반환할 때 맨 앞에 "r:" 접두어를
		 * 붙여서 게시글 리스트 보기 요청을 처리하는 URL를 지정하여 Controller로 넘기면
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

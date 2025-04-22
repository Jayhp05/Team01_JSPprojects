package com.jspstudy.ch04.exam.formdata;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 회원등록 폼 데이터를 처리하는 서블릿
@WebServlet("/formData01")
public class FormData02 extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		
		// 파라미터로 전송된 데이터를 저장할 변수 선언
		String name, id, pass, gender, nMail, aMail, iMail, job;
		
		/* GET 방식으로 전달되는 파라미터의 값을 읽어온다.
		 * 폼 컨트롤 중에서 한 줄 텍스트 입력 상자, 비밀번호 입력 상자, 
		 * 여러 줄 입력 상자에 입력된 값이 없으면 요청 파라미터는 존재하지만 
		 * 데이터가 입력되지 않은 상태이므로 공백 문자열("")을 받게 된다.
		 * 하지만 라디오 버튼과 체크 박스는 사용자가 선택하지 않으면 해당
		 * 요청 파라미터는 아예 서버로 전달되지 않기 때문에 null 값을 받게 된다.
		 **/		
		name = request.getParameter("name");		
		id = request.getParameter("id");
		pass = request.getParameter("pass");
		gender = request.getParameter("gender");		
		nMail = request.getParameter("nMail"); // value가 없으면 
		aMail = request.getParameter("aMail");
		iMail = request.getParameter("iMail");		
		job = request.getParameter("job");
		
//		받은 데이터 DB 저장 - 지금은 출력
		
//		1. 서블릿에서 응답을 직접 생성
//		응답 문서 형식 설정
		response.setContentType("text/html; charset-utf-8");
		
//		응답할 수 있는 스트림을 취득해서 응답을 보냄
		PrintWriter out = response.getWriter();
		out.println("<h2>회원 정보</h2>");
		out.println("이름 : " + name);
		out.println("공지메일 : " + nMail);
		out.close();
		
//		2. 응답을 편리하게 만드는 - JSP로 이동해서 작성
//		JSP로 이동할 수 있는 객체 - 메서드를 이용해 이동
//		JSP에서 응답을 작성
		request.setAttribute("name", name);
		request.setAttribute("id", id);
		request.setAttribute("pass", pass);
		request.setAttribute("gender", gender);
		request.setAttribute("iMail", receiveMail(iMail));
		request.setAttribute("aMail", receiveMail(aMail));
		request.setAttribute("nMail", receiveMail(nMail));
		request.setAttribute("job", job);
		
		RequestDispatcher rd = request.getRequestDispatcher("/view/formDataView.jsp");
		rd.forward(request, response);
			
	}
	
	// 체크박스의 선택 여부에 따라 문자열을 지정하는 메소드 
	private String receiveMail(String mail) {
		if(mail == null) {
			return "받지 않음";
		} 
		else {
			return "받음";
		}
	}
}

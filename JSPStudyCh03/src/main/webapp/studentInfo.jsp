<%-- 표현식과 EL을 이용해 학생정보 출력하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Student 클래스를 사용하기 위해 page 지시자를 이용해 import 하고 있다. --%>    
<%@ page import="com.jspstudy.ch03.vo.Student" %>    
<%
	// Student 객체를 생성하고 각 프로퍼티를 설정하고 있다.
	Student student1 = new Student("이순신", 23, "남성");
	Student student2 = new Student();
	student2.setName("홍길동");
	student2.setAge(25);
	student2.setGender("남성");
	
	// request 내장객체에 속성 이름을 지정하여 Student 객체를 저장한다.
	request.setAttribute("student", student1);

	/* 요청을 처리한 결과를 다른 JSP에 출력하기 위해 pageContext 내장객체의 
	 * forward() 메서드를 이용해 studentInfoResult.jsp로 포워딩 하여 프로그램의
	 * 제어 흐름을 이동시킨다.	일반적으로 서버 프로그래밍에서 요청을 처리하는
	 * 코드와 요청을 처리한 결과를 출력하는 코드를 분리하여 작성하는데 이럴 경우	 
	 * 아래와 같이 포워딩 기법을 사용해 프로그램 흐름을 다른 JSP로 이동시킨다.
	 **/	
	pageContext.forward("studentInfoResult.jsp");
%>
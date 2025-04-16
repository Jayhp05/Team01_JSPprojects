<%-- 표현식과 EL을 이용해 학생정보 출력하기 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Student 클래스를 사용하기 위해 page 지시자를 사용해 import 하고 있다. --%>      
<%@ page import="com.jspstudy.ch03.vo.Student"%>

<%
	/* JSP 페이지로부터 포워딩되어 넘어온 request 영역의 속성에 저장된 데이터를 읽는다.
	 * Object 타입으로 넘어오기 때문에 다운 캐스팅이 필요하다. 
	 * 아래에서 request.getAttribute("student")를 호출했을 때 request 내장객체의
	 * 속성에 student 라는 이름을 가진 객체가 존재하지 않으면 null을 반환한다.
	 **/
	 Student s = (Student) request.getAttribute("student");
%>
<!DOCTYPE html>
<html>
<head>	
<title>표현식과 EL을 이용해 학생정보 출력하기</title>
</head>
<body>
	<!-- 
		위쪽의 스크립틀릿에서 request 내장객체의 속성값을 읽어서 변수에 
		저장하고 그 변수의 값을 아래와 같이 표현식을 이용해 출력할 수 있다.
		
		studentInfo.jsp 페이지를 실행해서 현재 페이지로 forward 되어야
		아래에 데이터가 제대로 출력된다. 만약 studentInfo.jsp 페이지를
		실행하지 않고 현재 페이지만 실행하게 되면 위쪽의 스크립틀릿에서 request
		영역의 속성을 읽어 올 때 null 값을 받으므로 NullPointerException이 발생한다.
		그러므로 studentInfo.jsp를 실행하여 그 페이지에서 Student 클래스의
		인스턴스가 생성되고 request 내장객체의 속성에 student 라는 이름으로 객체가
		저장되어 이 페이지로 포워딩 될 수 있도록 studentInfo.jsp를 먼저 실행해야 한다.
	-->
	<h2>학생정보 출력하기 - 표현식(Expression)</h2>
	이 름 : <%= s.getName() %><br/>
	나 이 : <%= s.getAge() %><br/>
	성 별 : <%= s.getGender() %><br/><br/><br/>
	
	<!-- 
		표현언어(EL)를 사용해 내장객체의 속성 명을 지정해 값을 읽어 올 수 있다.
		스크립팅요소를 사용하는 것에 비해 간단히 내장객체 영역에 저장된 속성의 값을
		읽을 수 있다. 내장객체의 속성에 객체가 저장되어 있으면 내장객체의 속성명과
		dot 연자자(.)를 사용해 객체의 프로퍼티 값을 읽어 올 수 있다.
		표현언어(EL)로 객체의 프로퍼티를 읽기 위해서는 그 객체의 클래스에 읽어 올 
		프로퍼티에 대한 getter 메서드가 반드시 존재해야 한다. 
		그렇지 않으면 JasperException이 발생한다.
		
		이 JSP 페이지만 실행하면 request 내장객체의 속성에서 읽어올 것이 없으므로
		null 값이 되지만 표현 언어(EL)를 사용하면 NullPointerException은
		발생하지 않고 화면에 아무것도 출력되지 않는다.
		
		아래는 이전 studentInfo.jsp에서 request 영역의 속성에 student 라는
		이름으로 Student 클래스의 인스턴스를 저장하였으므로 스크립틀릿을 사용하지
		않아도 표현언어(EL)을 이용해 request 영역의 속성에 바로 접근할 수 있다.
		이렇게 표현언어는 pageContext, request, session, application 내장 객체의
		속성에 차례로 접근해 지정한 이름의 속성 이름이 있는지 탐색하여 데이터가
		존재하면 그 데이터를 읽어 출력하고 데이터가 없으면 아무것도 출력되지 않는다. 
	-->
	<h2>학생정보 출력하기 - 표현 언어(Expression Language)</h2>
	이 름 : ${ student.name }<br/>
	나 이 : ${ student.age }<br/>
	성 별 : ${ student.gender }<br/>
</body>
</html>
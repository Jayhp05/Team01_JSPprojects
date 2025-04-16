<%-- <jsp:useBean> 액션태그 - 요청 처리 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 등록 완료</title>
<style type="text/css">
	table {
		border: 1px solid blue;
		border-collapse: collapse; 
	}
	td { border: 1px solid blue; height: 30px; }
	.title {
		width: 100px;
		padding-left: 5px;
	}
	.content {
		width: 200px;
		padding-left: 5px;
	}
</style>	
</head>
<body style="font-size: 0.8em">	
	<%--		
		<jsp:useBean> 태그를 사용하게 되면 이 JSP 페이지가 서블릿 클래스로
		변경될 때 _jspService() 메소드 안에서 <jsp:useBean> 태그의 class 속성에
		지정한 클래스 타입으로 id에 지정한 이름으로 변수가 선언되고 scope에 지정한 
		영역의 속성에 저장된다. 속성 저장되는 이름은 id에 지정한 이름이 사용된다.
		
		<jsp:useBean id="student" class="com.jspstudy.ch09.vo.Student" >
		태그는 아래와 같은 자바 코드로 변경된다.
		 
		Student student = null;
		if(pageContext.getAttribute("student") == null) {
			student = new Student();
			pageContext.setAttribute("student", student);
		}
		
		<jsp:setProperty> 태그의 property 속성의 값을 "*"로 지정하면
		폼으로부터 전송된 요청 파라미터와 동일한 이름을 가진 자바 빈 클래스의
		프로퍼티에 요청 파라미터의 값을 자동으로 설정할 수 있다. 만약 같은
		이름을 가진 요청 파라미터가 없으면 에러는 발생하지 않고 자바 빈 클래스의
		프로퍼티는 기본값으로 설정된다. 만약 요청 파라미터의 이름과 자바 빈
		클래스에 프로퍼티의 이름이 다를 경우 property의 속성에 자바 빈 클래스의
		프로퍼티를 지정하고 param 속성에 요청 파라미터 이름을 지정하면 된다.
		또한 EL(Expression Language)의 param 내장 객체를 이용하여
		파라미터를 읽어 올 수도 있다
		
		Bean 클래스의 속성이 age 이고 요청 파라미터가 age1 이라면 아래와 같은
		<jsp:setProperty> 태그를 사용해 Bean 클래스의 age 값을 요청 파라미터로
		들어오는 age1으로 설정한다.
		 
		<jsp:setProperty name="student" property="age" param="age1" />
		
		
		위의 <jsp:setProperty> 태그는 아래와 같은 자바 코드로 변경된다.
		
		String age1 = request.getParameter("age1");		
		student.setAge(age1)
	--%>	
	<jsp:useBean id="student" class="com.jspstudy.ch09.vo.Student" />
		<jsp:setProperty name="student" property="*" />
		<jsp:setProperty name="student" property="age" param="age1" />		
		<jsp:setProperty name="student" property="phone" 
			value="${ param.phone1 }-${ param.phone2 }-${ param.phone3 }"/>	
	<table>
		<tr>
			<td colspan="2" style="text-align: center; 
				height: 30px; line-height: 30px">
				<h3>학생 등록 완료</h3></td>
		</tr>
		<tr>
			<td  class="title">이 름</td>
			<td class="content">
				<%--
					아래 <jsp:getProperty> 태그는 다음과 같은 자바 코드로 변경된다.
					pageCotext.getAttribute("student").getName(); 
				--%>
				<jsp:getProperty name="student" property="name" /></td>
		</tr>
		<tr>
			<td class="title">성 별</td>
			<td class="content">${ student.gender == null ? "선택않됨" : 
				student.gender == "male" ? "남자" : "여자" }</td>
		</tr>
		<tr>
			<td class="title">나 이</td>
			<%-- 
				<jsp:useBean> 태그의 id 속성에 지정한 값은 이 JSP 페이지가
				서블릿 클래스로 변경될 때 _jspService() 메소드 안에서 지역 변수로
				선언되고 이 변수가 사용되기 때문에 같은 _jspService() 메소드 안에 
				자바 코드로 변경되는 스크립틀릿이나 표현식에서도 <jsp:useBean>
				태그의 id 속성에 지정한 이름을 변수로 하여 객체에 접근할 수 있다.
			--%>
			<td class="content"><%= student.getAge() %></td>
		</tr>
		<tr>
			<td class="title">연락처</td>
			<td class="content">
				<jsp:getProperty name="student" property="phone" /></td>
		</tr>		
	</table>	
</body>
</html>
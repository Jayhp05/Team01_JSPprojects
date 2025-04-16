<%-- include 지시자를 이용해 다른 JSP 페이지 포함하기 - 자식 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%-- 
	include 지시자를 사용하면 이 JSP 페이지를 include 하는 부모 JSP 페이지가
	컴파일 될 때 자식 페이지인 현재 JSP 페이지가 포함되어 같이 컴파일되므로
	부모 JSP 페이지의 스크립틀릿에 선언된 변수를 별도의 선언이나 값 전달 없이
	아래 코드와 같이 자식 JSP 페이지의 표현식에서 그대로 사용할 수 있다.
	아래는 이클립스에서 에러로 표시되지만 문제없이 잘 실행된다. 
--%>
<%-- 다른 JSP 페이지에 포함되므로 전체 HTML 태그는 필요 없다. --%>
<table>
	<tr>
		<td rowspan="3">
			<img src="../<%= image %>"/></td>
		<td>&nbsp;</td>
		<td><h3><%= book %></h3></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>정가 <%= price %>원</td>
	</tr>	
	<tr>
		<td>&nbsp;</td>
		<td>저자 : 성윤정 | 출판일 : 2014년 07월 28일</td>
	</tr>	
</table>

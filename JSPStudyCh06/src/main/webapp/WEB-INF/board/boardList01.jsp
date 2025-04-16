<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시 글 리스트</title>
<style type="text/css">
	table{
		border: 1px solid blue;
		border-collapse: collapse;
	}
	td {
		border: 1px dotted blue;
		padding: 5px 10px;
	}
</style>
</head>
<body>	
	<h2>게시 글 리스트</h2>
	<table>
		<tr>
			<td>no</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>	
		<%--			
			게시 글 리스트가 존재하면 게시 글 수만큼 반복하면서 게시 글을 출력		
		--%>	
		<c:if test="${ not empty bList }">		
			<%-- 
				<c:forEach> 태그는 자바의 for문과 forEach문 두 가지 기능을 제공한다.
				자바의 for문과 같이 초깃값부터 순차적으로 반복하기 위해 아래와 같은 
				속성을 지정하여 사용한다. var 속성에는 forEach문 안에서 사용할 변수 
				이름을 지정하고 begin 속성은 var 속성에 지정한 변수의 시작 값을 지정하며
				end 속성에는 변수의 끝 값을 지정한다.(i <= end 가 적용된다.)
				step 속성은 생략할 수 있고 생략하게 되면 기본값은 1이 된다.
				
				<c:forEach var="i" begin="0" end="10" step="1" >
				
				아래에 사용된 코드는 forEach문의 기능을 하는 코드로 items 속성에 배열
				변수의 이름을 지정하고 var 속성에 forEach문 안에서 배열의 각 항목을
				저장하는 변수의 이름을 지정한다. items 속성에 지정할 수 있는 변수는
				스크립틀릿에서 선언한 변수를 표현식<%= member %>으로 지정할 수
				있고 아래와 같이 속성에 저장된 속성 이름을 EL 식을 이용해 지정할 수도 있다. 
				items 속성에 지정할 수 있는 데이터는 배열, Collection 객체, Iterator 객체, 
				Enumeration 객체, Map 객체와 콤마(,)로 구분된 문자열 등을 지정할 수 있다. 
			 --%>
			<c:forEach var="b" items="${bList}"> 
			<tr>
				<td>${ b.no }</td>
				<td>${ b.title }</td>
				<td>${ b.writer }</td>
				<td>${ b.regDate }</td>
				<td>${ b.readCount }</td>
			</tr>
			</c:forEach>
		</c:if>
		<%-- 게시 글 리스트가 존재하지 않으면 --%>
		<c:if test="${ empty bList }">
			<tr>
				<td colspan="5">게시 글이 존재하지 않습니다.</td>
			</tr>
		</c:if>			
	</table>
</body>
</html>
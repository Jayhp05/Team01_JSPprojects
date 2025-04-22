<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table{
		border: 1px solid blue;
		border-collapse: collapse;
	}
	
	td{
		border: 1px dotted blue;
		padding: 5px 10px;
	}
</style>

</head>
<body>
	<h1>게시글 리스트</h1>
	<table>
		<tr>
			<th>no</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<c:forEach var="board" items="${bList}">
			<tr>
				<td>${board.no}</td>
				<td>${board.title}</td>
				<td>${board.writer}</td>
				<td>${board.regDate}</td>
				<td>${board.readCount}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
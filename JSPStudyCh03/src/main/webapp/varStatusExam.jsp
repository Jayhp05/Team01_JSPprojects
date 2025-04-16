<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%
String[] deliveryData = {
		"도착 예상일 : 지금 주문하면 오늘 도착예정 | 판매지수 : 1,980",
		"도착 예상일 : 지금 주문하면 내일 도착예정 | 판매지수 : 6,860",
		"도착 예상일 : 지금 주문하면 오늘 도착예정 | 판매지수 : 4,960",
		"도착 예상일 : 지금 주문하면 모레 도착예정 | 판매지수 : 5,060",
		"도착 예상일 : 지금 주문하면 내일 도착예정 | 판매지수 : 1,560",			
};

pageContext.setAttribute("deliveryData", deliveryData);
pageContext.setAttribute("l", deliveryData.length);
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	
	<c:forEach begin="0" end="${ l - 1}" varStatus="s">
		${ s.index }, ${ s.count } <br/>
	</c:forEach>
	<c:forEach begin="0" end="${ l - 1 }" varStatus="s">
		${ deliveryData[s.index] }, ${ s.last } <br/>
	</c:forEach>
</body>
</html>
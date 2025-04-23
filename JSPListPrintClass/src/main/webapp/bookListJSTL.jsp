<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.jspstudy.ch03.vo.Book" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL과 JSTL을 이용해 도서 리스트 출력</title>
<style>
	body {
		display: flex;
		justify-content: center;
		align-items: center;
		min-height: 100vh;
		margin: 0;
		background-color: #f5f5f5;
		font-family: '맑은 고딕', sans-serif;
	}

	.wrapper {
		width: 850px;
		margin: 40px auto;
		background: #fff;
		box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
		padding: 30px;
		border-radius: 10px;
	}

	h2 {
		text-align: center;
		margin-bottom: 20px;
		padding-bottom: 10px;
		border-bottom: 3px solid green;
	}

	table {
		width: 100%;
		border-collapse: collapse;
		margin-top: 20px;
	}

	td {
		padding: 10px;
		border-bottom: 1px solid #ccc;
		vertical-align: top;
	}

	img {
		width: 100px;
	}

	.footer {
		text-align: center;
		margin-top: 30px;
		border-top: 2px dotted green;
		padding-top: 20px;
	}
</style>
</head>
<body>

<%
	ArrayList<Book> bookList = new ArrayList<Book>();
	bookList.add(new Book("images/javawebprogramming.jpg", "[도서]프로젝트로 배우는 자바 웹 프로그래밍", "황희정", "한빛아카데미", "2014년 01월", "30,000원 -→ 24,000원(20%할인)", "포인트 2,400원(10%지급)", "1,980"));
	bookList.add(new Book("images/jsp&servlet.jpg", "[도서]뇌를 자극하는 JSP & Servlet", "김윤명", "한빛미디어", "2010년 12월", "30,000원 -→ 27,000원(10%할인)", "포인트 2,700", "1,023"));
	bookList.add(new Book("images/headfirstjsp.jpg", "[도서]Head First Servlet & JSP", "케이시 시에라 등저/김종호", "한빛미디어", "2009년 02월", "32,000원 -→ 25,600원(20%할인)", "포인트 2,560", "1,011"));
	bookList.add(new Book("images/jsp2.3webprogramming.jpg", "[도서]최범균의 JSP2.3 웹프로그래밍", "최범균", "가메출판사", "2013년 02월", "25,000원 -→ 22,500원(10%할인)", "포인트 2,250", "2,012"));
	bookList.add(new Book("images/jsp&servlet_oracle&eclipse.jpg", "[도서]백견불여일타 JSP&Servlet : Oracle&Eclipse", "성윤정", "로드북", "2014년 07월", "27,000원 -→ 24,300원(10%할인)", "포인트 2,430", "1,095"));
	request.setAttribute("bookList", bookList);
%>

<div class="wrapper">
	<h2>도서 리스트</h2>

	<table>
		<c:forEach var="book" items="${bookList}">
		<tr>
			<td><img src="${book.img}"></td>
			<td>
				<b>${book.title}</b><br><br>
				${book.author} | ${book.publisher} | ${book.date}<br><br>
				${book.price} | ${book.point}<br><br>
				도서 예약일 : 지금 주문하면 내일 도착 예정 | 판매지수 : ${book.total}
			</td>
		</tr>
		</c:forEach>
	</table>

	<div class="footer">
		<a href="bookListJSTL.jsp">도서 리스트로 이동</a> 
		<a href="lottoNumListJSTL.jsp">로또 당첨 번호 리스트로 이동</a>
	</div>
</div>

</body>
</html>

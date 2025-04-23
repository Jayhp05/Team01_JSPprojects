<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="com.jspstudy.ch03.vo.Book" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스크립틀릿과 표현식을 이용해 도서 리스트 출력</title>
<style>
	body {
		font-family: '맑은 고딕', sans-serif;
		display: flex;
		justify-content: center;
		align-items: center;
		min-height: 100vh;
		margin: 0;
		background-color: #f5f5f5;
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
	
	content {
	}

	table {
		width: 100%;
		border-collapse: collapse;
		margin-top: 20px;
	}

	td {
		padding: 10px;
		vertical-align: top;
		border-bottom: 1px dotted #ccc;
	}

	img {
		width: 100px;
		height: auto;
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
<div class="wrapper">
	<h2>도서 리스트</h2>
	<%
		ArrayList<Book> bookList = new ArrayList<>();
		bookList.add(new Book("images/javawebprogramming.jpg", "프로젝트로 배우는 자바 웹 프로그래밍", "황희정", "한빛아카데미", "2014년 01월", "30,000원 -→ 24,000원(20%할인)", "포인트 2,400원(10%지급)", "1,980"));
		bookList.add(new Book("images/jsp&servlet.jpg", "뇌를 자극하는 JSP & Servlet", "김윤명", "한빛미디어", "2010년 12월", "30,000원 -→ 27,000원(10%할인)", "포인트 2,700", "1,023"));
		bookList.add(new Book("images/headfirstjsp.jpg", "Head First Servlet & JSP", "케이시 시에라 등저/김종호", "한빛미디어", "2009년 02월", "32,000원 -→ 25,600원(20%할인)", "포인트 2,560", "1,011"));
		bookList.add(new Book("images/jsp2.3webprogramming.jpg", "최범균의 JSP2.3 웹프로그래밍", "최범균", "가메출판사", "2013년 02월", "25,000원 -→ 22,500원(10%할인)", "포인트 2,250", "2,012"));
		bookList.add(new Book("images/jsp&servlet_oracle&eclipse.jpg", "백견불여일타 JSP&Servlet : Oracle&Eclipse", "성윤정", "로드북", "2014년 07월", "27,000원 -→ 24,300원(10%할인)", "포인트 2,430", "1,095"));
	%>
	<div class="content">
		<table>
			<%
				for (Book book : bookList) {
			%>
			<tr>
				<td><img src="<%= book.getImg() %>" alt="<%= book.getTitle() %>"></td>
				<td>
					<strong>[도서]</strong><%= book.getTitle() %><br><br>
					<%= book.getAuthor() %> 저 | <%= book.getPublisher() %> | <%= book.getDate() %><br><br>
					<%= book.getPrice() %> | <%= book.getPoint() %><br><br>
					도서 예약일 : 지금 주문하면 내일 도착 예정 | 판매지수 : <%= book.getTotal() %>
				</td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	
	<div class="footer">
		<a href="bookList.jsp">도서 리스트</a> 
		<a href="lottoNumList.jsp">로또 당첨 번호 리스트</a>
	</div>
</div>
</body>
</html>
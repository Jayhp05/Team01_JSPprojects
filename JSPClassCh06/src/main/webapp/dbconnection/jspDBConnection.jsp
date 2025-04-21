<%@page import="com.jspstudy.ch06.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
	String user = "hr";
	String pass = "hr";
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	/* 1. 접속드라이버 로딩 */
	Class.forName(driver);
	
	/* 2. DB에 연결 */
	Connection conn = DriverManager.getConnection(url, user, pass);
	
	/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
	/* PreparedStatement */
	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM jspbbs ORDER BY no DESC");
	
	/* 4. 쿼리를 발행하여 SELECT한 결과를 ResultSet 객체로 받는다. */
	ResultSet rs = pstmt.executeQuery();
	
	/* 여러 개의 Board 객체를 담을 ArrayList를 사용 */
	ArrayList<Board> bList = new ArrayList<>(); // 타입을 추론하여 뒤에 있는 배열의 타입은 따로 작성안해도 됨.
	
	/* 5. 퀴리를 실행한 결과를 while문 반복하면서 Board 객체에 담고 list에 담는다. */
	while(rs.next()) {
	 Board b = new Board();
	 
	 b.setNo(rs.getInt("no")); //  이름으로 가져옴 (이름이나 위치값으로 가져올 수 있음.)
	 b.setTitle(rs.getString(2)); // 위치값으로 가져오는 방법
	 b.setWriter(rs.getString("writer"));
	 b.setRegDate(rs.getTimestamp("reg_date"));
	 b.setReadCount(rs.getInt("read_count"));
	 
	 bList.add(b);
	}
	
	/* 6. DB 작업에 사용한 객체는 처음 가져온 역순으로 닫는다. */
	if(rs != null) {
		rs.close();
	}
	
	if(pstmt != null) {
		pstmt.close();
	}
	
	if(conn != null) {
		conn.close();
	}
%>
    
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
		<c:forEach var="board" items="<%= bList %>">
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
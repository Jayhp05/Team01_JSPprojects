<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="bootstrap/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div class="container">
		<!-- header -->
		<%@include file="../pages/header.jsp"%>
		
		<!-- content -->
		<div class="row my-5 text-center">
			<div class="col">
				<h2>게시글 리스트</h2>
			</div>
		</div>
		<form name="searchForm" id="searchFrom" action="#">
			<div class="row justify-content-center my-3">
				<div class="col-auto">
					<select name="type" class="form-select">
						<option value="title">제목</option>
						<option value="writer">작성자</option>
						<option value="content">내용</option>
					</select>
				</div>
				<div class="col-4">
					<input type="text" name="keyword" class="form-control" id="keyword">
				</div>
				<div class="col-auto">
					<input type="submit" class="btn btn-primary" value="검색">
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col text-end">
				<a href="writeForm" class="btn btn-outline-success">글쓰기</a>
			</div>
		</div>
		<div class="row my-3"> <!-- my-3 : margin 위아래로 3정도 사이즈 조절 -->
			<div class="col">
				<table class="table table-hover">
					<thead class="table-dark">
						<tr>
							<th>no</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody class="text-secondary">
					<!-- 게시글이 있는 경우 -->
						<c:if test="${not empty bList}">
							<c:forEach var="board" items="${bList}">
								<tr>
									<td>${board.no}</td>
									<td><a href="boardDetail?no=${board.no}"
										class="text=decoration-nonelink-secondary">${board.title}</a></td>
									<td>${board.writer}</td>
									<td>${board.regDate}</td>
									<td>${board.readCount}</td>
								</tr>
							</c:forEach>
						</c:if>
					<!-- 게시글이 없는 경우 -->
						<c:if test="${empty bList}">
							<tr>
								<td colspan="5">게시글이 존재하지 않음</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		
		<!-- footer -->
		<%@include file="../pages/footer.jsp"%>

	</div>
	<script src="bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
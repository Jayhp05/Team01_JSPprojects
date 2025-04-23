<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-3.7.1.min.js"></script>
<script src="js/formcheck.js"></script>

</head>
<body>
	<div class="container">
		<!-- header -->
		<%@include file="../pages/header.jsp"%>
		<!-- content -->
		<div class="row my-5" id="global-content">
			<div class="col">
				<div class="row text-center">
					<div class="col">
						<h2 class="fs-3 fw-bold">게시글 쓰기</h2>
					</div>
				</div>
				<form action="writeProcess" name="writeForm" id="writeForm" class="row border-primary g-3" method="post">
					<div class="col-4 offset-md-2">
					    <label for="writer" class="form-label">글쓴이</label>
					    <input type="text" class="form-control" name="writer"  id="writer" 
					    placeholder="작성자를 입력해 주세요">
					</div>
					<div class="col-4">
					    <label for="pass" class="form-label">비밀번호</label>
					    <input type="password" class="form-control" name="pass"  id="pass" >
					</div>
					<div class="col-8 offset-md-2">
					    <label for="title" class="form-label">제 목</label>
					    <input type="text" class="form-control" name="title"  id="title" >
					</div>
					<div class="col-8 offset-md-2">
					    <label for="content" class="form-label">내 용</label>
					    <textarea class="form-control" name="content" id="content" rows="10"></textarea>
					</div>
					
					<div class="row">
						<div class="col-8 offset-md-2 text-center mt-5">
							<input type="submit" class="btn btn-primary" value="등록하기"> &nbsp;&nbsp;
							<input type="button" class="btn btn-primary" value="목록보기"
									onclick="location.href='boardList'">
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- footer -->
		<%@include file="../pages/footer.jsp"%>
	</div>
	<script src="bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
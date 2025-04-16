<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
 <!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>게시 글 수정하기</title>
    <link href="bootstrap/bootstrap.min.css" rel="stylesheet" >
    <script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/formcheck.js"></script> 
  </head>
  <body>
  	<div class="container">
		<!-- header  -->
  		<%@ include file="../pages/header.jsp" %>
		<!-- content -->
		<div class="row my-5" id="global-content">
			<div class="col">
				<div class="row text-center">
					<div class="col">
						<h2 class="fs-3 fw-bold">게시 글 수정하기</h2>
					</div>
				</div>
				<!--
					게시 글을 수정하기 위해서는 테이블에서 게시 글을 유일하게 구분할 수 있는 
					데이터가 필요하다. 아래에서 no는 테이블에서 하나의 게시 글을 유일하게
					구분할 수 있는 데이터로 아래 폼이 서버로 전송될 때 이 no도 같이 서버로
					전송되어야 게시 글 정보를 제대로 수정할 수 있기 때문에 화면에는 보이지 
					않고 폼이 전송될 때 요청 파라미터에 추가될 수 있도록 hidden 폼 컨트롤로
					폼에 추가하였다.
				-->  
				<form name="updateForm" action="updateProcess" id="updateForm" 
					class="row g-3 border-primary" method="post">
					<input type="hidden" name="no" value="${board.no}">
				  	<div class="col-4 offset-md-2">
					    <label for="writer" class="form-label">글쓴이</label>
					    <input type="text" class="form-control" name="writer"  id="writer" 
					    	placeholder="작성자를 입력해 주세요" value="${board.writer}">
			  		</div>
			  		<div class="col-4 ">
					    <label for="pass" class="form-label">비밀번호</label>
					    <input type="password" class="form-control" name="pass"  
					    	id="pass">
			  		</div>
			  		<div class="col-8 offset-md-2">
					    <label for="title" class="form-label">제 목</label>
					    <input type="text" class="form-control" name="title"  
					    	id="title" value="${board.title}">
			  		</div>
					<div class="col-8 offset-md-2">
					    <label for="content" class="form-label">내 용</label>
					    <textarea class="form-control" name="content" id="content" 
					    	rows="10">${board.content}</textarea>
			  		</div>	
			  		<div class="col-8 offset-md-2 text-center mt-5">
					   <input type="submit" value="수정하기" class="btn btn-primary"/>
							&nbsp;&nbsp;<input type="button" value="목록보기" 
								onclick="location.href='boardList'" class="btn btn-primary"/>
			  		</div>	
				</form>
			</div>
		</div>
		<!-- footer  -->
		<%@ include file="../pages/footer.jsp" %>
	</div>
    <script src="bootstrap/bootstrap.bundle.min.js"></script>
  </body>
</html>
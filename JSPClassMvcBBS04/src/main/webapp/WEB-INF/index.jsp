<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP MVC 게시판</title>	
	<link href="bootstrap/bootstrap.min.css" rel="stylesheet" >
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
	<link rel="stylesheet" type="text/css" href="css/global.css" />
	<link rel="stylesheet" type="text/css" href="css/member.css" />		
    <style>      	
    </style>
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/formcheck.js"></script>
	<script src="js/member.js"></script>
</head>
<body>
	<div class="container">
		<%@ include file="pages/header.jsp" %>
		<jsp:include page="${ param.body }" />
		<%@ include file="pages/footer.jsp" %>
	</div>
    <script src="bootstrap/bootstrap.bundle.min.js"></script>	

	<!-- 로그인 모달 -->
	<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true"
		data-bs-backdrop="static" data-bs-keyboard="false">
		<div class="modal-dialog">
		    <div class="modal-content">
		      	<div class="modal-header bg-primary bg-gradient text-white">
			        <h1 class="modal-title fs-5 fw-bold" id="modalLabel">회원 로그인</h1>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      	</div>
				<form action="login.mvc" method="post">
					<div class="modal-body">	        
						<div class="mb-3">
		            		<label for="userId" class="col-form-label fw-bold">아이디 : </label>
		            		<input type="text" class="form-control" id="userId" name="id">
		          		</div>
			          	<div class="mb-3">
				            <label for="pass" class="col-form-label fw-bold">비밀번호 : </label>
			            	<input type="password" class="form-control" id="pass" name="pass">
			          	</div>	        
		    		</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
				        <button type="submit" class="btn btn-primary">로그인</button>
			      	</div>
		      	</form>
	    	</div>	    
		</div>
	</div>
	
</body>
</html>
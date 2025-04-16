<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>세션을 이용한 사용자 로그인 유지하기</title>
    <link href="../bootstrap/bootstrap.min.css" rel="stylesheet" >
    <link href="../css/member.css" rel="stylesheet" >
  </head>
  <body>
  	<div class="container">	
		<!-- header  -->
		<%@ include file="../pages/header.jsp" %>
  		<!-- content -->
		<div class="row my-5" id="global-content">
			<div class="col">
				<form class="my-5" id="loginForm" action="loginProcess.jsp" method="post">
					<h2 class="fw-bold">Member Login</h2>
					<fieldset>	
						<legend>Member Loin</legend>
						<div id="login">
							<p>
								<label for="userId" class="labelStyle">아이디</label>
								<input type="text" id="userId" name="id" />
							</p>
							<p>
								<label for="userPass" class="labelStyle">비밀번호</label>
								<input type="password" id="userPass" name="pass"/>
							</p>
						</div>
							<input type="submit" value="로그인" id="btnLogin" />
						<div id="searchBtn">
							<p>
								<span>비밀번호 찾기</span> | <span>아이디 찾기</span> | <span>회원가입</span>
							</p>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		<!-- footer -->
		<%@ include file="../pages/footer.jsp" %>
	</div>
    <script src="../bootstrap/bootstrap.bundle.min.js"></script>
  </body>
</html>

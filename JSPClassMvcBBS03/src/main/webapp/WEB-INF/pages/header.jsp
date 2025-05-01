<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header  -->
<div class="row border-bottom border-primary" id="global-header">
	<div class="col-4">
		<p><img src="https://via.placeholder.com/200x100"></p>
	</div>
	<div class="col-8">
		<div class="row mt-1">
			<div class="col">
				<ul class="nav justify-content-end">
					<li class="nav-item">
						<a class="nav-link" 
							href='${ sessionScope.isLogin ? "logout.mvc" : "loginForm.mvc" }'>
							${ sessionScope.isLogin ? "로그아웃" : "로그인-폼" }
						</a>						
					</li>
					<li class="nav-item">
						<a class="nav-link " 
							${ not sessionScope.isLogin ? "data-bs-toggle='modal' data-bs-target='#loginModal'" : ""}
							href='${ sessionScope.isLogin ? "logout.mvc" : "#" }'>
							${ sessionScope.isLogin ? "로그아웃" : "로그인-모달" }
						</a>						
					</li>
					<li class="nav-item">
						<a class="nav-link" href="boardList.mvc">게시글 리스트</a>
					</li>
					<li class="nav-item">						
						<c:if test="${ not sessionScope.isLogin }" >	
							<a class="nav-link" href="joinForm.mvc">회원가입</a>
						</c:if>
						<c:if test="${ sessionScope.isLogin }" >
							<a class="nav-link" href="memberUpdateForm.mvc">정보수정</a>
						</c:if>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">주문/배송조회</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">고객센터</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="row">
			<div class="col text-end">&nbsp;</div>
		</div>
		<div class="row">
			<div class="col text-end pe-5 text-primary">
				<c:if test="${ sessionScope.isLogin }" >
					<div>안녕하세요 ${ sessionScope.id }님</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
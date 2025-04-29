<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	JSP 표준 태그 라이브러리(JSTL)를 사용하기 위한 taglib 지시자
	http://jakarta.apache.org, http://tomcat.apache.org 에서
	다운로드 하여 WEB-INF/lib 폴더에 추가해야 표준 태그를 사용할 수 있다. 
	JSTL의 코어 라이브러리는 말 그대로 JSTL의 가장 핵심적인 기능을 제공하는
	라이브러리로 프로그래밍 언어에서 일반적으로 제공하고 있는 변수 선언, 조건문,
	반복문에 해당하는 태그를 지원한다. 또한 익셉션, URL 저장, 데이터 출력과 관련된
	태그와 다른 JSP 페이지 호출(import, redirect)과 관련된 태그를 지원한다.	   
 --%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>게시글 리스트</title>
<link href="bootstrap/bootstrap.min.css" rel="stylesheet" >

<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/formcheck.js"></script>
        
</head>
<body>
  	<div class="container">
  		<%-- 
  			여러 페이지에 걸쳐서 중복되는 내용을 별도의 JSP 페이지로 만들고 include 지시자를
  			이용해 현재 JSP 페이지에 포함 시킬 수 있다. include 지시자를 이용해 외부의 JSP
  			페이지를 포함 시키면 컴파일 타임에 현재 JSP 페이지에 하나로 합쳐져서 컴파일 된다.
  			중복되는 내용을 별도의 JSP 페이지로 만드는 것을 페이지 모듈화라고 한다.
  		--%>  	
		<!-- header  -->
		<%@ include file="../pages/header.jsp" %>
  		<!-- content -->
  		<div class="row my-5" id="global-content">
 			<div class="col">
		  		<div class="row text-center">
		  			<div class="col">
		  				<h2 class="fs-3 fw-bold">게시글 리스트</h2>
		  			</div>
		  		</div>  		
				<form name="searchForm" id="searchForm" action="#" 
					class="row justify-content-center my-3">
					<div class="col-auto">
						<select name="type" class="form-select">
							<option value="title">제목</option>
							<option value="writer">작성자</option>
							<option value="content">내용</option>
						</select>
					</div>
					<div class="col-4">
						<input type="text" name="keyword" class="form-control" id="keyword"/>
					</div>
					<div class="col-auto">
						<input type="submit" value="검 색" class="btn btn-primary"/>
					</div>
				</form>  
				
				<!-- 검색 요청인 경우 -->
				<c:if test="${ searchOption }">
					<div class="row my-3">
						<div class="col text-center">"${keyword}" 검색 결과
						</div>
					</div> 
					<%-- 검색 요청일 경우 일반 게시글 리스트로 이동할 수 있도록 링크를 설정했다. --%>
					<div class="row my-3">
						<div class="col-6">
							<a href="boardList" class="btn btn-outline-success">리스트</a>
						</div>
						<div class="col-6 text-end">
							<a href="writeForm" class="btn btn-outline-success">글쓰기</a>
						</div>
					</div>
				</c:if>
				<!-- 검색 요청이 아닌 경우 -->
				<c:if test="${ not searchOption }">
					<div class="row my-3">
						<div class="col text-end">
							<a href="writeForm" class="btn btn-outline-success">글쓰기</a>
						</div>
					</div>
				</c:if>
				
		  		<div class="row my-3">  			
		  			<div class="col">
		  				<table class="table table-hover">
		  					<thead>
			 					<tr class="table-dark">
									<th>NO</th>
									<th>제목</th>
									<th>작성자</th>
									<th>작성일</th>
									<th>조회수</th>
								</tr>
							</thead>
							<tbody class="text-secondary">
		  					<%-- 게시글이 있는 경우 게시글 수만큼 반복하면서 게시글을 출력 (검색 요청이면서 게시글이 있는 경우) --%>	
							<c:if test="${searchOption and not empty bList }">
								<c:forEach var="b" items="${bList}">
								<tr>
									<td>${ b.no }</td>
									<td class="text-secondary">
										<a href="boardDetail?no=${b.no}&pageNum=${currentPage}&type=${type}&keyword=${keyword} class="text-decoration-none link-secondary">${ b.title }</a>
									</td>
									<td>${ b.writer }</td>
									<td>${ b.regDate }</td>
									<td>${ b.readCount }</td>
								</tr>
								</c:forEach>
							</c:if>
							<!-- 일반 게시글 리스트 요청이면서 게시글이 있는 경우 -->
							<c:if test="${not searchOption and not empty bList }">
								<c:forEach var="b" items="${bList}">
								<tr>
									<td>${ b.no }</td>
									<td>
										<a href="boardDetail?no=${b.no}" class="text-decoration-none link-secondary">${ b.title }</a>
									</td>
									<td>${ b.writer }</td>
									<td>${ b.regDate }</td>
									<td>${ b.readCount }</td>
								</tr>
								</c:forEach>
							</c:if>
							
							<%-- 검색 게시글 리스트이면서 게시글이 없는 경우 --%>
							<c:if test="${searchOption and empty bList }">
								<tr>
									<td colspan="5" class="text-center">
										"${keyword}"가 포함된 게시글이 존재하지 않습니다.
									</td>
								</tr>
							</c:if>
							<%-- 일반 게시글 리스트이면서 게시글이 없는 경우 --%>
							<c:if test="${not searchOption and empty bList }">
								<tr>
									<td colspan="5" class="text-center">게시글이 존재하지 않습니다.</td>
								</tr>
							</c:if>
							</tbody>
		  				</table>
		  			</div>  			
		  		</div>
		  		<!-- 검색 요청이면서 검색된 리스트가 존재할 경우 페이지네이션 -->
		  		<c:if test="${searchOption and not empty bList}">
		 		<div class="row">
					<div class="col">
						<nav aria-label="Page navigation example">
							<ul class="pagination justify-content-center">
								<c:if test="${startPage > pageGroup}">
									<li class="page-item">
										<a class="page-link" href="boardList?pageNum=${ startPage - pageGroup }&type=${type}&keyword=${keyword}">Pre</a>
									</li>
								</c:if>
								<c:forEach var="i" begin="${startPage}" end="${endPage}">
									<!-- 현재 페이지 - 링크x active -->
									<c:if test="${i == currentPage }">
										<li class="page-item active" aria-current="page">
											<span class="page-link active">${i}</span>
										</li>
									</c:if>
									<c:if test="${i != currentPage }">
										<li class="page-item"><a class="page-link" href="boardList?pageNum=${i}">${i}</a></li>
									</c:if>    
								</c:forEach>
								<c:if test="${ endPage < pageCount }">
									<li class="page-item">
										<a class="page-link" href="boardList?pageNum=${ startPage + pageGroup }&type=${type}&keyword=${keyword}">Next</a>
									</li>
								</c:if>
							</ul>
						</nav>
					</div>
				</div>
				</c:if>
		  		
		  		<!--  -->
		  		<c:if test="${ not searchOption and not empty bList }">
		 		<div class="row">
					<div class="col">
						<nav aria-label="Page navigation example">
							<ul class="pagination justify-content-center">
								<c:if test="${startPage > pageGroup}">
									<li class="page-item">
										<a class="page-link" href="boardList?pageNum=${ startPage - pageGroup }">Pre</a>
									</li>
								</c:if>
								<c:forEach var="i" begin="${startPage}" end="${endPage}">
									<!-- 현재 페이지 - 링크x active -->
									<c:if test="${i == currentPage }">
										<li class="page-item active" aria-current="page">
											<span class="page-link active">${i}</span>
										</li>
									</c:if>
									<c:if test="${i != currentPage }">
										<li class="page-item"><a class="page-link" href="boardList?pageNum=${i}">${i}</a></li>
									</c:if>    
								</c:forEach>
								<c:if test="${ endPage < pageCount }">
									<li class="page-item">
										<a class="page-link" href="boardList?pageNum=${ startPage + pageGroup }">Next</a>
									</li>
								</c:if>
							</ul>
						</nav>
					</div>
				</div>
				</c:if>
			</div>
		</div>
		<!-- footer  -->
		<%@ include file="../pages/footer.jsp" %>
	</div>
<script src="bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
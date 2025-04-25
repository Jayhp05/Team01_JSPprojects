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
  </head>
  <body>
  	<div class="container">	
		<!-- header -->
		<%@ include file="../pages/header.jsp" %>
  		<!-- content -->
  		<div class="row my-5 text-center" id="global-content">
  			<div class="col">
		  		<div class="row">
		  			<div class="col">
		  				<h2 class="fs-3 fw-bold">게시 글 리스트</h2>
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
						<input type="text" name="keyword" class="form-control"/>
					</div>
					<div class="col-auto">
						<input type="submit" value="검 색" class="btn btn-primary"/>
					</div>
				</form>  		
		  		<div class="row">
		  			<div class="col text-end">
		  				<a href="#" class="btn btn-outline-success">글쓰기</a>
		  			</div>
		  		</div>
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
								<tr>
									<td>200</td>							
									<td><a href="#" class="text-decoration-none link-secondary">감사합니다.</a></td>
									<td>회원1</td>
									<td>2023-04-27 05:44:32</td>
									<td>162</td>
								</tr>						
								<tr>
									<td>199</td>							
									<td><a href="#" class="text-decoration-none link-secondary">궁금한게 해결 되었네요</a></td>
									<td>회원8</td>
									<td>2023-04-27 05:50:21</td>
									<td>77</td>
								</tr>						
								<tr>
									<td>198</td>							
									<td><a href="#" class="text-decoration-none link-secondary">저두 궁금했는데</a></td>
									<td>회원7</td>
									<td>2023-04-27 05:44:32</td>
									<td>81</td>
								</tr>						
								<tr>
									<td>197</td>							
									<td><a href="#" class="text-decoration-none link-secondary">아 줄 바꿈 문제 해결 했습니다.</a></td>
									<td>관리자</td>
									<td>2023-04-27 04:58:45</td>
									<td>35</td>
								</tr>						
								<tr>
									<td>196</td>							
									<td><a href="#" class="text-decoration-none link-secondary">감사합니다.</a></td>
									<td>관리자</td>
									<td>2023-04-27 03:40:43</td>
									<td>16</td>
								</tr>						
								<tr>
									<td>195</td>							
									<td><a href="#" class="text-decoration-none link-secondary">그러게요</a></td>
									<td>회원3</td>
									<td>2023-04-27 04:59:15</td>
									<td>46</td>
								</tr>						
								<tr>
									<td>194</td>							
									<td><a href="#" class="text-decoration-none link-secondary">회원이면 당연한 것을...</a></td>
									<td>회원3</td>
									<td>2023-04-27 05:44:32</td>
									<td>38</td>
								</tr>						
								<tr>
									<td>13</td>							
									<td><a href="#" class="text-decoration-none link-secondary">별 말씀을 다하시네요</a></td>
									<td>회원1</td>
									<td>2023-04-27 05:44:32</td>
									<td>79</td>
								</tr>						
								<tr>
									<td>192</td>							
									<td><a href="#" class="text-decoration-none link-secondary">당연하죠~</a></td>
									<td>회원6</td>
									<td>2023-04-27 05:44:32</td>
									<td>9</td>
								</tr>						
								<tr>
									<td>191</td>							
									<td><a href="#" class="text-decoration-none link-secondary">저두요~</a></td>
									<td>회원5</td>
									<td>2023-04-27 05:44:32</td>
									<td>15</td>
								</tr>
							</tbody>
		  				</table>
		  			</div>  			
		  		</div>
		  		<!-- 페이지 네이션 -->
				<div class="row">
					<div class="col">
						<nav aria-label="Page navigation">
						  <ul class="pagination justify-content-center">	
						    	<li class="page-item active" aria-current="page">
						    		<span class="page-link">1</span>
						    	</li>	
						    	<li class="page-item"><a class="page-link" href="#">2</a></li>
						    	<li class="page-item"><a class="page-link" href="#">3</a></li>	
						    	<li class="page-item"><a class="page-link" href="#">4</a></li>	
						    	<li class="page-item"><a class="page-link" href="#">5</a></li>	
						    	<li class="page-item"><a class="page-link" href="#">6</a></li>	
						    	<li class="page-item"><a class="page-link" href="#">7</a></li>	
						    	<li class="page-item"><a class="page-link" href="#">8</a></li>	
						    	<li class="page-item"><a class="page-link" href="#">9</a></li>	
						    	<li class="page-item"><a class="page-link" href="#">10</a></li>	
							    <li class="page-item">
							      <a class="page-link" href="#">Next</a>
							    </li>						  	
						  </ul>
						</nav>
					</div>
				</div>		  		
		  	</div>
		</div>			  		
		<!-- footer -->
		<%@ include file="../pages/footer.jsp" %>
	</div>
    <script src="../bootstrap/bootstrap.bundle.min.js"></script>
  </body>
</html>

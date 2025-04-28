<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>게시글 쓰기</title>
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
						<h2 class="fs-3 fw-bold">게시글 쓰기</h2>
					</div>
				</div>
				<!--		 
					form은 사용자로부터 데이터를 입력받기 위한 폼 컨트롤들로 구성된다.
					사용자가 입력한 일반적인 데이터 즉 이름, 직업 등의 데이터는 문자열로 
					이루어진 데이터로 사용자가 submit 버튼을 클릭하게 되면 브라우저는
					이 데이터를 서버로 보내기 전에 페이지의 문자 셋을 기준으로 인코딩을 한 후
					서버로 전송하게 된다. 문자열로 이루어진 데이터를 브라우저가 인코딩 하는
					방식은 application/x-www-form-urlencoded 방식으로 form의
					기본 enctype 이다. 그래서 enctype을 생략할 수 있지만 파일과 같은
					데이터를 전송하기 위해서는 위의 인코딩 방식으로는 서버로 전송할 수 없다.
					파일은 문자열 형태의 데이터가 아닌 바이너리 형태의 데이터로 파일을 업로드
					하기 위해서는 form 태그의 전송방식을 method 속성에 post를 지정하고
					인코딩 타입(enctype)을 multipart/form-data로 지정해야 한다. 
				-->	  
				<form name="writeForm" action="writeProcess" id="writeForm" 
					class="row g-3 border-primary" method="post" enctype="multipart/form-data">
				  	<div class="col-4 offset-md-2">
					    <label for="writer" class="form-label">글쓴이</label>
					    <input type="text" class="form-control" name="writer"  id="writer" 
					    	placeholder="작성자를 입력해 주세요">
			  		</div>
			  		<div class="col-4 ">
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
			  		<div class="col-8 offset-md-2">
					    <label for="file1" class="form-label">파 일</label>
					    <input type="file" class="form-control" name="file1"  id="file1" >
			  		</div>			  		
			  		<div class="col-8 offset-md-2 text-center mt-5">
					   <input type="submit" value="등록하기" class="btn btn-primary"/>
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
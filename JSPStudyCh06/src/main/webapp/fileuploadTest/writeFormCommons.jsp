<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시 글쓰기 폼</title>
	<link type="text/css" href="../css/board.css" rel="stylesheet" /> 
	<script src="../js/jquery-3.3.1.min.js"></script>
	<script src="../js/formcheck.js"></script>
</head>
<body>	
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
	<form name="writeForm" action="writeProcessCommons.jsp" id="writeForm"
			 method="post" enctype="multipart/form-data">
		<table class="readTable">
			<tr>
				<td colspan="4" class="boardTitle"><h2>게시 글쓰기</h2></td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class="readTh">글쓴이</td>
				<td class="readTd">
					<input type="text" name="writer" size="30" id="writer" maxlength="10"/>
				</td>
				<td class="readTh">비밀번호</td>
				<td class="readTd">
					<input type="password" name="pass" size="30" id="pass" 
						maxlength="10"/>
				</td>
			</tr>		
			<tr>
				<td class="readTh">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
				<td class="readTd" colspan=3>
					<input type="text" name="title" size="90" id="title" maxlength="50"/>
				</td>
			</tr>			
			<tr>
				<td class="readTh">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
				<td class="readTd" colspan="3">
					<textarea name="content" id="content" rows="20" cols="80"></textarea>
				</td>
			</tr>
			<tr>
				<td class="readTh">파일첨부</td>
				<td class="readTd" colspan=3>
					<input type="file" name="file1" size="70" id="file" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4" class="tdSpan"><input type="reset" value="다시쓰기"/>
					&nbsp;&nbsp;<input type="submit" value="등록하기" />
					&nbsp;&nbsp;<input type="button" value="목록보기" 
						onclick="javascript:window.location.href='boardList.jsp'"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
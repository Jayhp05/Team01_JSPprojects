<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- content -->
<div class="row my-5" id="global-content">
	<div class="col">
		<form name="checkForm" id="checkForm">
			<input type="hidden" name="no" id="no" value="${ board.no }"/>
			<input type="hidden" name="pass" id="rPass" />
			<input type="hidden" name="pageNum" value="${ pageNum }" />

			<%-- 
				검색 리스트에서 들어온 요청일 경우 다시 keyword에 해당하는 
				검색 리스트로 돌려보내기 위해서 아래의 파라미터가 필요하다.
			 --%>
			<c:if test="${ searchOption }">
				<input type="hidden" name="type" value="${ type }" />
				<input type="hidden" name="keyword" value="${ keyword }" />
			</c:if>			
		</form>
		<div class="row text-center">
			<div class="col">
				<h2 class="fs-3 fw-bold">게시글 상세보기</h2>
			</div>
		</div> 
		<div class="row my-3">
			<div class="col">
				<table class="table table-bordered" >
					<tbody>					
						<tr>
							<th class="table-secondary">제 목</th>
							<td colspan="3">${ board.title }</td>		
						</tr>
						<tr>
							<th>글쓴이</th>
							<td>${ board.writer }</td>
							<th>작성일</th>
							<td><fmt:formatDate value="${ board.regDate }" 
								pattern="yyyy-MM-dd HH:mm:ss" /></td>		
						</tr>
						<tr>		
							<th>비밀번호</th>
							<td>
								<div class="col-sm-8">
									<input class="form-control" type="password" name="pass" id="pass">
								</div>
							</td>
							<th>조회수</th>
							<td>${ board.readCount }</td>
						</tr>	
						<tr>
							<th>파&nbsp;&nbsp;&nbsp;&nbsp;일</th>
							<td colspan="3">
							<c:if test="${ empty board.file1 }">
								첨부파일 없음
							</c:if>
							<c:if test="${ not empty board.file1 }">
								<a href="upload/${ board.file1 }">파일 다운로드</a>
							</c:if>
							</td>		
						</tr>
						<tr>		
							<td colspan="4">
								<pre>${ board.content }</pre>
							</td>
						</tr>	
					</tbody>
				</table>
			</div>
		</div>
		<div class="row my-3">
			<div class="col text-center">				
				<input class="btn btn-warning" type="button" id="detailUpdate" value="수정하기"/>
				&nbsp;&nbsp;<input class="btn btn-danger"  type="button" id="detailDelete" value="삭제하기" />				
				<%-- 
					일반 게시글 리스트에서 온 요청이면 일반 게시글 리스트로 돌려보낸다. 
				--%>
				<c:if test="${ not searchOption }">	
				&nbsp;&nbsp;<input class="btn btn-primary" type="button" value="목록보기" 
						onclick="location.href='boardList.mvc?pageNum=${pageNum}'"/>
				</c:if>
				<%-- 
					검색 리스트에서 온 요청이면 검색 리스트의 동일한 페이지로 돌려보낸다. 
				--%>
				<c:if test="${ searchOption }">	
				&nbsp;&nbsp;<input class="btn btn-primary" type="button" value="목록보기" 
						onclick="location.href='boardList.mvc?pageNum=${pageNum}&type=${ type }&keyword=${ keyword }'"/>
				</c:if>
			</div>
		</div>
	</div>	
</div>
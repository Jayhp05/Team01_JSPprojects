<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="js/reply.js"></script>

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
		
		<!-- 게시글 상세보기 영역  --> 
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
								<a href="fileDownload.mvc?fileName=${ board.file1 }">파일 다운로드</a>
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
		
		<!-- 상세보기 버튼 영역 -->
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
		
		<!-- 
			아래부터 추천/땡큐와 댓글 기능을 처리하는 부분으로
			기존의 게시글 상세보기 뷰 페이지의 아래쪽에 다음의 코드를 추가하면 된다.  
		-->
		<!-- 추천/땡큐 영역 -->
		<div class="row my-5">
			<div class="col border p-3">
				<div id="recommend" class="text-end">
					<span id="commend" class="btnCommend text-primary" style="cursor: pointer;"> 
						<img src="images/recommend.png" alt="추천하기" />&nbsp;추천
						<span class="recommend" >(${ board.recommend })</span>
					</span> |
					<span id="thank" class="btnCommend text-primary" style="cursor: pointer;">
						<img src="images/smile.png" alt="땡큐"/>&nbsp;땡큐
						<span class="recommend">(${ board.thank })</span>
					</span> |	
					<span id="replyWrite" class="text-primary" style="cursor: pointer;">						
						<i class="bi bi-file-earmark-text-fill" style="color: cornflowerblue;"></i> 댓글쓰기
					</span>					
				</div>
			</div>
		</div>
		
		<!-- 댓글 헤더 영역 -->
		<div class="row" id="replyTitle">
			<div class="col p-2 text-center bg-dark text-white">	
				<h3 class="fs-4">이 글에 대한 댓글 리스트</h3>								
			</div>
		</div>
				
		<!-- 댓글 리스트 영역 -->
		<!-- 댓글이 존재하는 경우 -->
		<c:if test="${ not empty replyList }" >		
		<div class="row mb-3">
			<div class="col" id="replyList">
				<c:forEach var="reply" items="${ replyList }" >
				<div class="replyRow row border border-top-0">
					<div class="col">
						<div class="row bg-light p-2">
							<div class="col-4">
								<span>${ reply.replyWriter }</span>
							</div>
							<div class="col-8 text-end">
								<span class="me-3">
									<fmt:formatDate value="${ reply.regDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</span>
								<button class="modifyReply btn btn-outline-success btn-sm" data-no="${ reply.no }">
									<i class="bi bi-journal-text">수정</i>									
								</button>
								<button class="deleteReply btn btn-outline-warning btn-sm" data-no="${ reply.no }">
									<i class="bi bi-trash">삭제</i>
								</button>
								<button class="btn btn-outline-danger btn-sm" onclick="reportReply('${ reply.no }')">
									<i class="bi bi-telephone-outbound">신고</i>									
								</button>
							</div>
						</div>	
						<div class="row">
							<div class="col p-3">
								<pre>${ reply.replyContent }</pre>
							</div>
						</div>
					</div>	
				</div>				
				</c:forEach>
			</div>
		</div>
		</c:if>
		
		<!-- 댓글이 존재하지 않는 경우 -->
		<c:if test="${ empty replyList }" >		
		<div class="row mb-5" id="replyList">
			<div class="col text-center border p-5">
				<div>이 게시글에 대한 댓글이 존재하지 않습니다.</div>
			</div>
		</div>
		</c:if>
		
		<!-- 댓글 쓰기 폼 -->
		<div class="row my-3 d-none" id="replyForm">
			<div class="col">
				<form name="replyWriteForm" id="replyWriteForm">
					<input type="hidden" name="bbsNo" value="${ board.no }"/>
					<input type="hidden" name="replyWriter" value="${ sessionScope.id }" />
					<div class="row bg-light my-3 p-3 border">
						<div class="col">
							<div class="row">
								<div class="col text-center">
									<span>악의적인 댓글은 예고 없이 삭제될 수 있으며 글쓰기 제한과 아이디 삭제 처리됩니다.</span>
								</div>
							</div>						
							<div class="row my-3">								
								<div class="col-md-10">
									<textarea name="replyContent" id="replyContent" class="form-control" rows="4"></textarea>
								</div>
								<div class="col-md">
									<input type="submit" value="댓글쓰기"	 class="btn btn-primary h-100 w-100" id="replyWriteButton">									
								</div>
							</div>
						</div>
					</div>
				</form>	
			</div>	
		</div><!-- end replyForm -->
		
	</div>	
</div>
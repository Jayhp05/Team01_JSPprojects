/**
 * 
 */
$(function() { // 안에 아래 이벤트 처리 코드를 기술해야 함
	/* DOM이 준비되면 */
	
	/* 게시글 상세보기에서 삭제하기 버튼이 클릭되면 */
	$("#detailDelete").on("click", function() {
		/* 비밀번호 입력란에 입력된 데이터 읽어오기 - 입력되었는지 체크 */
		let pass = $("#pass").val();
		
		if(pass.length <= 0) {
			alert("비밀번호를 입력해 주세요.");
			
			return false;
		}
		
		/* 입력된 비밀번호 폼에 추가하고 폼을 전송 - POST 방식으로 */
		$("#rPass").val(pass);
		$("#checkForm").attr("action", "deleteProcess");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
	});
	
	/* 게시글 상세보기에서 수정하기 버튼이 클릭되면 */
	$("#detailUpdate").on("click", function() {
		/* 비밀번호 입력란에 입력된 데이터 읽어오기 - 입력되었는지 체크 */
		let pass = $("#pass").val();
		
		if(pass.length <= 0) {
			alert("비밀번호를 입력해 주세요.");
			
			return false;
		}
		
		/* 입력된 비밀번호 폼에 추가하고 폼을 전송 - POST 방식으로 */
		$("#rPass").val(pass);
		$("#checkForm").attr("action", "updateForm");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
	});

	// 게시 글쓰기 폼 유효성 검사
	$("#writeForm").on("submit", function() {
 		if($("#writer").val().length <= 0) {
 			alert("작성자가 입력되지 않았습니다.\n작성자를 입력해주세요");
 			$("#writer").focus();
 			return false;
 		}
	
 		if($("#title").val().length <= 0) { 
 			alert("제목이 입력되지 않았습니다.\n제목을 입력해주세요");
 			$("#title").focus();
 			return false;
 		}
		
 		if($("#pass").val().length <= 0) {
 			alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
 			$("#pass").focus();
 			return false;
 		}
		
 		if($("#content").val().length <= 0) {
 			alert("내용이 입력되지 않았습니다.\n내용을 입력해주세요");
 			$("#content").focus();
 			return false;
 		}
	});
	
	$("#updateForm").on("submit", function() {
		if($("#writer").val().length <= 0) {
			alert("작성자가 입력되지 않았습니다.\n작성자를 입력해주세요");
			$("#writer").focus();
			return false;
		}
		
		if($("#title").val().length <= 0) {
			alert("제목이 입력되지 않았습니다.\n제목을 입력해주세요");
			$("#title").focus();
			return false;
		}
		
		if($("#pass").val().length <= 0) {
			alert("비밀번호가 입력되지 않았습니다.\n비밀번호를 입력해주세요");
			$("#pass").focus();
			return false;
		}
		
		if($("#content").val().length <= 0) {
			alert("내용이 입력되지 않았습니다.\n내용을 입력해주세요");
			$("#content").focus();
			return false;
		}
	});
});
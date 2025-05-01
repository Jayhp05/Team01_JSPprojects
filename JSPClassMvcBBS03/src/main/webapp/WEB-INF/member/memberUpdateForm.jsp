<%-- 회원가입 폼 요청 처리 결과를 출력할 View 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 
	새로운 5자리 우편번호로 회원 주소를 입력 받기 위해 daum.net에서
	제공하는 우편번호 찾기 API를 사용하였다.
	참고 사이트 : http://postcode.map.daum.net/guide
-->    
<script src=
"https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<div class="row my-5" id="global-content">
	<div class="col">
		<div class="row my-3 text-center">
			<div class="col">
				<h2 class="fs-3 fw-bold">회원 정보 수정</h2>
			</div>
		</div>
		<form action="memberUpdateResult.mvc" name="memberUpdateForm" 
			method="post" id="memberUpdateForm">
			<div class="row mt-5 mb-3">
				<div class="col-8 offset-2">				
					<label for="name" class="form-label">* 이 름 : </label>
					<input type="text" class="form-control" name="name" id="name" 
						value="${sessionScope.member.name}" readonly>
				</div>				
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="id" class="form-label">* 아이디 : </label>
					<div class="row">
						<div class="col">
							<input type="text" class="form-control" name="id" id="userId" 
								value="${sessionScope.member.id}" readonly>
						</div>
					</div>
				</div>
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="pass1" class="form-label">* 기존 비밀번호 : </label>
					<div class="row">
						<div class="col-6">
							<input type="password" class="form-control" name="oldPass" id="oldPass">
						</div>
						<div class="col-4">
							<input type="button" class="btn btn-warning" id="btnPassCheck" value="비밀번호 확인">
						</div>
					</div>					
				</div>
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="pass1" class="form-label">* 새 비밀번호 : </label>
					<input type="password" class="form-control" name="pass1" id="pass1">					
				</div>
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="pass2" class="form-label">* 새 비밀번호 확인 : </label>
					<input type="password" class="form-control" name="pass2" id="pass2">
				</div>
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="zipcode" class="form-label">* 우편번호 : </label>
					<div class="row">
						<div class="col-4">
							<input type="password" class="form-control" name="zipcode" id="zipcode" 
								maxlength="5" readonly value="${sessionScope.member.zipcode}">
						</div>
						<div class="col-4">
							<input type="button" class="btn btn-warning" id="btnZipcode" value="우편번호 찾기">
						</div>
					</div>
				</div>
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="address1" class="form-label">* 자택주소 : </label>
					<input type="text" class="form-control" name="address1" id="address1"  
						readonly value="${sessionScope.member.address1}">
				</div>	
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="address2" class="form-label">상세주소 : </label>				
					<input type="text" class="form-control" name="address2" id="address2"
						value="${sessionScope.member.address2}">
				</div>
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="emailId" class="form-label">* 이 메 일 : </label>
					<div class="row">
						<div class="col-md-4">
							<input type="text" class="form-control" name="emailId" id="emailId"
								value="${sessionScope.member.email.split('@')[0]}">
						</div> @ 
						<div class="col-md-4">
							<input type="text" class="form-control" name="emailDomain" id="emailDomain"
								value="${sessionScope.member.email.split('@')[1]}">
						</div>
						<div class="col-md-3">
							<select class="form-select" name="selectDomain" id="selectDomain">
								<option>직접입력</option>
								<option>네이버</option>
								<option>다음</option>
								<option>한메일</option>
								<option>구글</option>
							</select>
						</div>
					</div>
				</div>	
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="mobile2" class="form-label">* 휴 대 폰 : </label>
					<div class="row">
						<div class="col-md-3">
							<select class="form-select" name="mobile1" id="mobile1">
								<option ${ member.mobile.split('-')[0] == 010 ? "selected" : "" }>
									010</option>
								<option ${ member.mobile.split('-')[0] == 011 ? "selected" : "" }>
									011</option>
								<option ${ member.mobile.split('-')[0] == 016 ? "selected" : "" }>
									016</option>
								<option ${ member.mobile.split('-')[0] == 017 ? "selected" : "" }>
									017</option>
								<option ${ member.mobile.split('-')[0] == 018 ? "selected" : "" }>
									018</option>
								<option ${ member.mobile.split('-')[0] == 019 ? "selected" : "" }>
									019</option>
							</select>
						</div>-
						<div class="col-md-4">
							<input type="text" class="form-control" name="mobile2" id="mobile2" maxlength="4"
								value="${ sessionScope.member.mobile.split('-')[1] }">
						</div>-
						<div class="col-md-4">
							<input type="text" class="form-control" name="mobile3" id="mobile3" maxlength="4"
								value="${ sessionScope.member.mobile.split('-')[2] }">
						</div>
					</div>
				</div>
			</div>			
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label class="form-label">메일 수신여부 : </label>					
					<div class="row">
						<div class="col-md-3">
							<div class="form-check">
								<input type="radio" class="form-check-input" name="emailGet" id="emailOk" 
									value="true" ${ member.emailGet ? "checked" : "" }>
								<label class="form-check-label" for="emailOk">수신함</label>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-check">
								<input type="radio" class="form-check-input" name="emailGet" id="emailNo" 
									value="false" ${ member.emailGet ? "" : "checked" }>
								<label class="form-check-label" for="emailNo">수신않함</label>
							</div>
						</div>	
					</div>					
				</div>	
			</div>
			<div class="row my-3">
				<div class="col-8 offset-2">
					<label for="phone2" class="form-label">자택전화 : </label>
					<div class="row">
						<div class="col-md-3">
							<select class="form-select" name="phone1" id="phone1">
								<option ${ member.phone.split('-')[0] == 02 ? "selected" : "" }>
									02</option>
								<option ${ member.phone.split('-')[0] == 031 ? "selected" : "" }>
									031</option>
								<option ${ member.phone.split('-')[0] == 032 ? "selected" : "" }>
									032</option>
								<option ${ member.phone.split('-')[0] == 033 ? "selected" : "" }>
									033</option>
								<option ${ member.phone.split('-')[0] == 041 ? "selected" : "" }>
									041</option>
								<option ${ member.phone.split('-')[0] == 042 ? "selected" : "" }>
									042</option>
								<option ${ member.phone.split('-')[0] == 043 ? "selected" : "" }>
									043</option>
								<option ${ member.phone.split('-')[0] == 044 ? "selected" : "" }>
									044</option>
								<option ${ member.phone.split('-')[0] == 051 ? "selected" : "" }>
									051</option>
								<option ${ member.phone.split('-')[0] == 052 ? "selected" : "" }>
									052</option>
								<option ${ member.phone.split('-')[0] == 053 ? "selected" : "" }>
									053</option>
								<option ${ member.phone.split('-')[0] == 054 ? "selected" : "" }>
									054</option>
								<option ${ member.phone.split('-')[0] == 055 ? "selected" : "" }>
									055</option>
								<option ${ member.phone.split('-')[0] == 061 ? "selected" : "" }>
									061</option>
								<option ${ member.phone.split('-')[0] == 062 ? "selected" : "" }>
									062</option>
								<option ${ member.phone.split('-')[0] == 063 ? "selected" : "" }>
									063</option>
								<option ${ member.phone.split('-')[0] == 064 ? "selected" : "" }>
									064</option>
								<option ${ member.phone.split('-')[0] == 010 ? "selected" : "" }>
									010</option>
								<option ${ member.phone.split('-')[0] == 011 ? "selected" : "" }>
									011</option>
								<option ${ member.phone.split('-')[0] == 016 ? "selected" : "" }>
									016</option>
								<option ${ member.phone.split('-')[0] == 017 ? "selected" : "" }>
									017</option>
								<option ${ member.phone.split('-')[0] == 018 ? "selected" : "" }>
									018</option>
								<option ${ member.phone.split('-')[0] == 019 ? "selected" : "" }>
									019</option>
							</select>
						</div> - 
						<div class="col-md-4">
							<input type="text" class="form-control" name="phone2" id="phone2" 
								maxlength="4" value="${ member.phone.split('-')[1] }">
						</div> - 
						<div class="col-md-4">
							<input type="text" class="form-control" name="phone3" id="phone3" 
								maxlength="4" value="${ member.phone.split('-')[2] }">
						</div>
					</div>
				</div>	
			</div>			
			<div class="row mb-3 mt-5">				
				<div class="col-8 offset-2">
					<input type="submit" value="수정하기" class="btn btn-primary">
				</div>
			</div>
		</form>
	</div>
</div>



<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록 결과</title>
</head>
<body>
	<h2>학생 등록 정보</h2>
	<p>이름 : ${name}</p>
	<p>성별 : ${gender}</p>
	<p>연락처 : ${phone}</p>
	<p>희망 취업 분야 : ${hopeJobs}</p>
	<p>관심 분야 : 
     <% 
         String[] stars = (String[]) request.getAttribute("stars");
         if (stars != null && stars.length > 0) {
             for (String star : stars) {
                 out.println(star);
             }
         } else {
             out.println("선택한 관심 분야가 없습니다.");
         }
     %>
     </p>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>선호도 테스트</title>
</head>
<body>
    <h2>선호도 테스트</h2>
    <form action="ch0501ExamProcess.jsp" method="post">
        이름: <input type="text" name="name" /><br/><br/>

        좋아하는 색:
        <input type="radio" name="color" value="빨강색" />빨강색
        <input type="radio" name="color" value="초록색" />초록색
        <input type="radio" name="color" value="파랑색" />파랑색
        <br/><br/>

        좋아하는 음식:
        <select name="food">
            <option value="짜장면">짜장면</option>
            <option value="짬뽕">짬뽕</option>
            <option value="햄버거">햄버거</option>
            <option value="피자">피자</option>
        </select>
        <br/><br/>

        좋아하는 동물(모두 고르세요):<br/>
        <input type="checkbox" name="animal" value="햄스터" />햄스터
        <input type="checkbox" name="animal" value="고양이" />고양이
        <input type="checkbox" name="animal" value="호랑이" />호랑이
        <input type="checkbox" name="animal" value="사자" />사자
        <input type="checkbox" name="animal" value="개" />개
        <br/><br/>

        취미(모두 고르세요):&nbsp;&nbsp;&nbsp;
        <select name="hobby" multiple size="4" style="vertical-align: top;">
            <option value="게임">게임</option>
            <option value="여행">여행</option>
            <option value="독서">독서</option>
            <option value="낚시">낚시</option>
        </select>
        <br/><br/>

        <input type="reset" value="다시쓰기" />
        <input type="submit" value="전송하기" />
    </form>
</body>
</html>

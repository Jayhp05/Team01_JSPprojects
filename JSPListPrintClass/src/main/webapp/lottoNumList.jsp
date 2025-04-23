<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.jspstudy.ch03.vo.LottoNum" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로또 당첨 번호 리스트 출력</title>
<style>
    body {
        font-family: Arial, sans-serif;
        text-align: center;
        background-color: #f9f9f9;
    }

    h2 {
        margin-top: 30px;
        margin-bottom: 40px;
    }

    .lotto-container {
        display: inline-block;
        padding: 20px;
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }

    .lotto-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: #f0f2f5;
        border-radius: 10px;
        margin-bottom: 12px;
        padding: 10px 20px;
        width: 650px;
        margin-left: auto;
        margin-right: auto;
        box-shadow: 0 0 5px rgba(0,0,0,0.05);
    }

    .lotto-round {
        font-weight: bold;
        color: #3366cc;
        flex: 0 0 60px;
    }

    .lotto-balls {
        display: flex;
        gap: 6px;
        flex-wrap: nowrap;
    }

    .ball img {
        width: 40px;
        height: 40px;
        vertical-align: middle;
    }

    .footer {
        margin-top: 40px;
    }

    .footer a {
        margin: 0 10px;
        color: #333;
        text-decoration: none;
        font-weight: bold;
    }

    .footer a:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>
    <h2>로또 당첨 번호 리스트</h2>

    <div class="lotto-container">
    <%
        List<LottoNum> lottoList = new ArrayList<LottoNum>();
        lottoList.add(new LottoNum("907회", 21, 27, 29, 38, 40, 44, 37));
        lottoList.add(new LottoNum("908회", 3, 16, 21, 22, 23, 44, 30));
        lottoList.add(new LottoNum("909회", 7, 24, 29, 30, 34, 35, 33));
        lottoList.add(new LottoNum("910회", 1, 11, 17, 27, 35, 39, 31));
        lottoList.add(new LottoNum("911회", 4, 5, 12, 14, 32, 42, 35));
        lottoList.add(new LottoNum("912회", 5, 8, 18, 21, 22, 38, 10));
        lottoList.add(new LottoNum("913회", 6, 14, 16, 21, 27, 37, 40));
        lottoList.add(new LottoNum("914회", 16, 19, 24, 33, 42, 44, 27));
        lottoList.add(new LottoNum("915회", 2, 6, 11, 13, 22, 37, 14));
        lottoList.add(new LottoNum("916회", 9, 21, 22, 32, 35, 36, 17));

        for (LottoNum l : lottoList) {
    %>
        <div class="lotto-item">
            <div class="lotto-round"><%= l.getTimes() %></div>
            <div class="lotto-balls">
                <span class="ball"><img src="images/lotto_img/ball_<%= String.format("%02d", l.getNum1()) %>.png" alt="<%= l.getNum1() %>" /></span>
                <span class="ball"><img src="images/lotto_img/ball_<%= String.format("%02d", l.getNum2()) %>.png" alt="<%= l.getNum2() %>" /></span>
                <span class="ball"><img src="images/lotto_img/ball_<%= String.format("%02d", l.getNum3()) %>.png" alt="<%= l.getNum3() %>" /></span>
                <span class="ball"><img src="images/lotto_img/ball_<%= String.format("%02d", l.getNum4()) %>.png" alt="<%= l.getNum4() %>" /></span>
                <span class="ball"><img src="images/lotto_img/ball_<%= String.format("%02d", l.getNum5()) %>.png" alt="<%= l.getNum5() %>" /></span>
                <span class="ball"><img src="images/lotto_img/ball_<%= String.format("%02d", l.getNum6()) %>.png" alt="<%= l.getNum6() %>" /></span>
                <span>+ 보너스 번호</span>
                <span class="ball"><img src="images/lotto_img/ball_<%= String.format("%02d", l.getBonusNum()) %>.png" alt="<%= l.getBonusNum() %>" /></span>
            </div>
        </div>
    <%
        }
    %>
    </div>

    <div class="footer">
        <a href="bookList.jsp">도서 리스트로 이동</a>
        <a href="lottoNumList.jsp">로또 당첨 번호 리스트로 이동</a>
    </div>
</body>
</html>

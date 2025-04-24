<%@ page import="java.util.*, com.jspstudy.ch03.vo.LottoNum" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL과 JSTL을 이용해 로또 당첨 번호 리스트 출력</title>
<style>
    body {
        font-family: Arial, sans-serif;
        text-align: center;
        background-color: #f5f5f5;
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
        align-items: center;
    }
    
    .ball-img {
    	width: 36px;
    	height: 36px;
    	border-radius: 50%;
    	margin: 0 3px;
	}
    

    .ball {
        display: inline-block;
        width: 36px;
        height: 36px;
        line-height: 36px;
        text-align: center;
        border-radius: 50%;
        font-weight: bold;
        color: white;
        font-size: 15px;
    }
    
    .bonus-text {
	    font-size: small;
	    color: gray;
	    position: relative;
	    top: 1px;
	    margin: 0 5px;
	}
    
    .yellow { background: gold; }
    .blue { background: deepskyblue; }
    .red { background: tomato; }
    .gray { background: gray; }
    .green { background: limegreen; }

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
	
	List<Map<String, String>> formattedList = new ArrayList<>();
    for (LottoNum lotto : lottoList) {
        Map<String, String> map = new HashMap<>();
        map.put("times", lotto.getTimes());
        map.put("num1", String.format("%02d", lotto.getNum1()));
        map.put("num2", String.format("%02d", lotto.getNum2()));
        map.put("num3", String.format("%02d", lotto.getNum3()));
        map.put("num4", String.format("%02d", lotto.getNum4()));
        map.put("num5", String.format("%02d", lotto.getNum5()));
        map.put("num6", String.format("%02d", lotto.getNum6()));
        map.put("bonusNum", String.format("%02d", lotto.getBonusNum()));
        formattedList.add(map);
    }
    request.setAttribute("formattedList", formattedList);
%>

<div class="lotto-container">
	<h2>로또 당첨 번호 리스트</h2>
	<c:forEach var="lotto" items="${formattedList}">
	    <div class="lotto-item">
	        <div class="lotto-round">${lotto.times}</div>
	        <div class="lotto-balls">
	            <img class="ball-img" src="images/lotto_img/ball_${lotto.num1}.png" alt="${lotto.num1}">
	            <img class="ball-img" src="images/lotto_img/ball_${lotto.num2}.png" alt="${lotto.num2}">
	            <img class="ball-img" src="images/lotto_img/ball_${lotto.num3}.png" alt="${lotto.num3}">
	            <img class="ball-img" src="images/lotto_img/ball_${lotto.num4}.png" alt="${lotto.num4}">
	            <img class="ball-img" src="images/lotto_img/ball_${lotto.num5}.png" alt="${lotto.num5}">
	            <img class="ball-img" src="images/lotto_img/ball_${lotto.num6}.png" alt="${lotto.num6}">
	            <span class="bonus-text">+ 보너스 번호</span>
	            <img class="ball-img" src="images/lotto_img/ball_${lotto.bonusNum}.png" alt="${lotto.bonusNum}">
	        </div>
	    </div>
	</c:forEach>

	<div class="footer">
	    <a href="bookListJSTL.jsp">도서 리스트로 이동</a>
	    <a href="lottoNumListJSTL.jsp">로또 당첨 번호 리스트로 이동</a>
	</div>
</div>

</body>
</html>

<%@page import="java.util.ArrayList"%>
<%@page import="com.jspstudy.ch03.vo.LottoNum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	현재 jsp 페이지는 요청을 받아서 처리하는 역학
	다 처리한 후에 lottoNumResult.jsp로 제어 이동
	
	// 로또 5회차 당첨번호 DB - 리스트에 저장
--%>

<%
	ArrayList<LottoNum> lottoList = new ArrayList<>();
	LottoNum lotto = new LottoNum("968회", 2, 5, 12, 14, 24, 39, 33);
	lottoList.add(lotto);
	lotto = new LottoNum("969회", 3, 9, 10, 29, 40, 45, 7);
	lottoList.add(lotto);
	lotto = new LottoNum("970회", 9, 11, 16, 21, 28, 36, 5);
	lottoList.add(lotto);
	lotto = new LottoNum("971회", 2, 6, 17, 18, 21, 26, 7);
	lottoList.add(lotto);
	lotto = new LottoNum("972회", 3, 6, 17, 23, 37, 39, 26);
	lottoList.add(lotto);
	
//	HttpServletRequest
	request.setAttribute("lottoList", lottoList);
	pageContext.forward("lottoNumResult.jsp");
%>


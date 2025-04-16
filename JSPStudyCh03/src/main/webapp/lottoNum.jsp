<!-- EL과 JSLT을 이용해 로또 당첨 번호 리스트 출력하기 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.jspstudy.ch03.vo.*, java.util.*" %>
<%
	// 회 차별 로또 당첨 번호를 저장할 ArrayList 객체 생성
	ArrayList<LottoNum> lottoList = new ArrayList<LottoNum>();
	
	// 각 회 차별 로또 당첨 번호를 저장할 LottoNum 객체를 생성하고 ArrayList 에 저장
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
	
	// Request 영역에 속성 이름을 지정하고 위에서 추출한 로또번호 리스트를 저장
	request.setAttribute("lottoList", lottoList);
	
	/* 요청을 처리한 결과를 다른 JSP에 출력하기 위해 pageContext 내장객체의 
	 * forward() 메서드를 이용해 lottoNumResult.jsp로 포워딩 하여 프로그램의
	 * 제어 흐름을 이동시킨다.	일반적으로 서버 프로그래밍에서 요청을 처리하는
	 * 코드와 요청을 처리한 결과를 출력하는 코드를 분리하여 작성하는데 이럴 경우	 
	 * 아래와 같이 포워딩 기법을 사용해 프로그램 흐름을 다른 JSP로 이동시킨다.
	 **/
	pageContext.forward("lottoNumResult.jsp");
%> 


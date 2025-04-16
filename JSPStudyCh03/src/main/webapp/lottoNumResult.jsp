<!-- EL과 JSLT을 이용해 로또 당첨 번호 리스트 출력하기 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	표준 태그 라이브러리(JSTL)의 코어 라이브러리를 사용하기 위해 taglib 지시자를 사용해
	접두어와 URI 식별자를 지정하고 있다. JSTL 라이브러리는 JSP 페이지에서 공통으로
	사용하는 코드의 집합으로 코어, 포맷팅, 데이터베이스, XML처리, 함수지원 등의 기능을
	제공한다. JSTL 라이브러리를 사용하기 위해서는 필요한 라이브러리를 구분하여 지정해야
	하는데 바로 URI 식별자가 이 라이브러리를 구분하는 역할을 한다. prefix는 JSP 
	페이지에서 라이브러리를 간편히 사용할 수 있도록 지원하는 접두어 이다.
	taglib 지시자를 사용해 아래와 같이 지정하게 되면 코어 라이브러리의 URI 식별자가
	접두어 c에 연결되고 JSP 페이지에서는 "<c:"로 시작하는 태그를 사용하면 된다.
	코어 라이브러리는 말 그대로 핵심적인 기능을 제공하는 라이브러리로 프로그래밍에서
	필요한 변수 선언, 조건문, 반복문 등의 기능을 간편하게 사용할 수 있도록 지원하는
	라이브러리 이다. 또한 익셉션, URL 저장, 데이터 출력과 관련된 태그와 다른 JSP
	페이지 호출(import, redirect)과 관련된 태그를 지원한다.	
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL과 JSTL을 이용해 로또 당첨 번호 리스트 출력</title>
<style>
	ul {
		list-style-type: none;
	}
	ul > li {
		height: 30px;
	}
	li > span {
		color: blue;
	}
</style>
</head>
<body>
	<h2>로또 당첨 번호 리스트</h2>
	<%-- 
		표준 태그 라이브러리와 표현 언어를 이용해 로또 당첨 번호가 
		존재하면 반복문을 이용해 로또 당첨 번호 리스트를 출력한다. 
	--%>
	<c:if test="${not empty lottoList}">
		<ul>
		<%-- 
			<c:forEach> 태그는 자바의 for문과 forEach문 두 가지 기능을 제공한다.
			자바의 for문과 같이 초깃값부터 순차적으로 반복하기 위해 아래와 같은 
			속성을 지정하여 사용한다. var 속성에는 forEach문 안에서 사용할 변수 
			이름을 지정하고 begin 속성은 var 속성에 지정한 변수의 시작 값을 지정하며
			end 속성에는 변수의 끝 값을 지정한다.(i <= end 가 적용된다.)
			step 속성은 생략할 수 있고 생략하게 되면 기본값은 1이 된다.
			
			<c:forEach var="i" begin="0" end="10" step="1" >
			
			아래에 사용된 코드는 forEach문의 기능을 하는 코드로 items 속성에 배열
			변수의 이름을 지정하고 var 속성에 forEach문 안에서 배열의 각 항목을
			저장하는 변수의 이름을 지정한다. items 속성에 지정할 수 있는 변수는
			스크립틀릿에서 선언한 변수를 표현식<%= member %>으로 지정할 수
			있고 아래와 같이 속성에 저장된 속성 이름을 EL 식을 이용해 지정할 수도 있다. 
			items 속성에 지정할 수 있는 데이터는 배열, Collection 객체, Iterator 객체, 
			Enumeration 객체, Map 객체와 콤마(,)로 구분된 문자열 등을 지정할 수 있다. 
			
			이전 페이지인 lottoNum.jsp에서 request.setAttribute("lottoList", lottoList);를
			이용해 하나의 요청 범위 안에서 유효한 request 객체의 속성에 "lottoList"라는
			이름으로 저장했으므로 아래와 같이 EL을 이용해 items 속성에 값으로 지정하면
			lottoList의 길이만큼 forEach 문이 반복되어 로또 당첨 번호 리스트가 출력된다.  
		 --%>		 
		<c:forEach var="lottoNum" items="${ lottoList }" >
		
			<%-- 	
				표현언어(EL)로 객체의 프로퍼티를 읽기 위해서는 아래와 같이 속성 
				이름에 dot(.) 연산자를 사용해 객체의 프로퍼티 이름을 지정하면 읽어
				올 수 있는데 이때 해당 객체는 반드시 getter 메서드를 가지고 있어야
				한다. 그렇지 않으면 JasperException이 발생한다. 
			--%>
			<li>${ lottoNum.times }차 - ${ lottoNum.num1 }, ${ lottoNum.num2}, 
				${ lottoNum.num3}, ${ lottoNum.num4}, ${ lottoNum.num5},
				${ lottoNum.num6} + <span>보너스번호</span>
				${ lottoNum.bonusNum}</li>
		</c:forEach>
		</ul>
	</c:if>
	
	 <%-- 
	 	로또 당첨 번호가 존재하지 않으면 존재하지 않는다는 메시지를 출력한다.
	  --%>
	<c:if test="${ empty lottoList }">
		로또번호가 존재하지 않습니다.
	</c:if>
</body>
</html>
<%-- <jap:include> 액션태그를 이용해 다른 JSP페이지 포함하기 - 자식 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 다른 JSP 페이지에 포함되므로 전체 HTML 태그는 필요 없다. --%>
<table>
	<tr>
		<td rowspan="3">
			<img src="../${ param.image }"/></td>
		<td>&nbsp;</td>
		<td><h3>${ param.book }</h3></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>정가 ${ param.price }원</td>
	</tr>	
	<tr>
		<td>&nbsp;</td>
		<td>저자 : 성윤정 | 출판일 : 2014년 07월 28일</td>
	</tr>	
</table>

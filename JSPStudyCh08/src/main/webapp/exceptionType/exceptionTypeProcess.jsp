<%-- ExceptionType 별로 Exception을 발생시키는 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.jspstudy.ch07.Product"%>
<%
	// 정수를 0으로 나누어 ArithmeticException을 발생시킨다.
	int num = 1 / (int) 0.1f;

	// 객체를 생성하지 않고 사용하여 NullPointerException을 발생시킨다.
	//Product product = null;
	//out.println(product.getName());
	
	/* 숫자로 변환 불가능한 문자를 숫자 변환을 시도하여 
	 * NumberFormatException을 발생시킨다. 
	 **/	 
	//String str = "13.0f";	
	//int num = Integer.parseInt(str);
	
	/* 배열의 index 범위를 초과하는 index 접근으로
	 * ArrayIndexOutOfBoundsException을 발생시킨다.
	 **/	 
	/* String[] names = { "홍길동", "이순신", "강감찬", "임꺽정", "장산박" };
	for(int i = 0; i <= names.length; i++) {
		out.println(names[i] + "<br/>");
	} */
%>
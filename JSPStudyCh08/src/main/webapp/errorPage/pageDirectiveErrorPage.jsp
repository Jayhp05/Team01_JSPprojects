<%-- 에러 페이지 지정과 에러 페이지 작성하기 - 에러가 발생될 가능성이 있는 페이지 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%-- 
	이 JSP 페이지에서 에러가 발생하면  page 지시자의 errorPage 속성에
	지정된 JSP 페이지가 호출되어 그 페이지에서 에러를 처리할 수 있다.
--%>    
<%@ page errorPage="pageDirectiveIsErrorPage.jsp" %>    
<%	
	
	// NullPointerException
	String str = null;
	System.out.println(str.equals("error"));
	
	// ArithmeticException
	// int num = 10 / 0;
	
	// ArrayIndexOutOfBoundsException
	// int[] nums = { 1, 2, 3, 4, 5 };
	// int num = nums[5];	
%>





<%@page import="java.nio.file.Path"%>
<%@page import="java.nio.charset.Charset"%>
<%@page import="org.apache.commons.fileupload2.core.FileItem"%>
<%@page import="org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload"%>
<%@page import="org.apache.commons.fileupload2.core.DiskFileItemFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*, java.io.*"%>    
<%@ page import="com.jspstudy.ch06.vo.*, com.jspstudy.ch06.dao.*"  %>


<%!
	/* JSP 선언부를 이용해 JSP 초기화 메서드를 정의하고 애플리케이션
	 * 초기화 파라미터인 uploadDir을 읽어서 파일이 저장되는 폴더로 사용할 것임 
	 **/
	static String uploadDir;
	static File parentFile;

	public void jspInit() {		
		/* web.xml에서 설정한 웹 어플리케이션 초기화 파라미터를 읽는다.
		 **/
		uploadDir = getServletContext().getInitParameter("uploadDir");
		
		/* 웹 어플리케이션 초기화 파라미터에서 읽어온 파일이 저장될 폴더의 
		 * 로컬 경로를 구하여 그 경로와 파일명으로 File 객체를 생성한다.
		 **/
		String realPath = getServletContext().getRealPath(uploadDir);		
		parentFile = new File(realPath);
		
		/* 파일 객체에 지정한 위치에 디렉토리가 존재하지 않거나 
		 * 파일 객체가 디렉토리가 아니라면 디렉토리를 생성한다.
		 **/
		if(! (parentFile.exists() && parentFile.isDirectory())) {
			parentFile.mkdir();
		}
		System.out.println("init - " + parentFile);		
	}
	
%>
<%
	/* Servlet 3.0부터 지원하는 Part AIP를 이용한 파일 업로드 구현하기
     *	
	 * 1. 폼에서 전송된 데이터가 multipart/form-data 인지 체크
	 * ServletFileUpload 클래스의 isMultipartContent(request) 메소드는
	 * 요청 정보에 포함되어 전송된 데이터가 multipart/form-data 이면 true를
	 * 그렇지 않으면 false를 반환한다.
	 **/
	 
	request.setCharacterEncoding("UTF-8");
	String contentType = request.getContentType();
	
	// Form에서 전송된 요청이 multipart/form-data 인지 체크
	if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
	
		// request 객체를 이용해 요청 본문(body)으로 들어온 데이터를 Part 리스트로 받음 
		Collection<Part> parts = request.getParts();
		
		for(Part part : parts) {
			System.out.println(part.getHeader("Content-Disposition"));
			System.out.printf("파라미터 : %s, contentTyp : %s, size : %dByte\n", part.getName(), part.getContentType(), part.getSize());
		}
		
		
		
		// 하나의 게시 글 정보를 저장하는 자바빈 객체 생성		 
		Board board = new Board();
		

		
		System.out.println(board.getTitle() + ", " + board.getWriter());
		
		
	} else {
		System.out.println("폼에서 전송된 요청이 mutipart/form-data가 아님");
	}		
	
%>

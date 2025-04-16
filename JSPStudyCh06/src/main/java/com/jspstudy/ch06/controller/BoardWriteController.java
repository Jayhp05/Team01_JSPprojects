package com.jspstudy.ch06.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

/* Part API를 이용한 파일 업로드를 구현한 서블릿 클래스
 * 
 * Servlet 2.5까지는 Apache Commons Fileupload와 같은 별도의 라이브러리를
 * 사용해 파일 업로드를 구현하였는데 Servlet 3.0부터 파일 업로드를 위한
 * Part API가 추가되어 보다 쉽게 파일 업로드를 쉽게 구현할 수 있게 되었다. 
 * Servlet 클래스에 @MultipartConfig 애노테이션을 사용해 Multipart에 대한
 * 설정을 할 수 있다.
 * fileSizeThreshold : 임시 파일 저장 여부를 결정할 크기, 파일 크기가 이 값을
 *   넘지 않으면 임시 파일로 저장하지 않고 메모리 상에만 저장되었다 삭제된다.
 * location : 파일 업로드시 임시 저장 디렉터리, 생략하면 기본 임시 디렉터리에 
 *   저장한다. System.getProperty("java.io.tmpdir")로 확인 할 수 있다. 
 * maxFileSize : 개별 파일의 최대 크기, -1을 지정하면 크기 제안 없음
 * maxRequestSiZe : 한 요청에서 여러 파일을 합한 최대 크기, -1은 제한 없음 
 **/
@MultipartConfig(fileSizeThreshold = 1024 * 10, // 10KB 
				maxFileSize = 1024 * 1024 * 10, // 10MB
				maxRequestSize = 1024 * 1024 * 10 * 10) // 100MB
@WebServlet("/writeProcess")
public class BoardWriteController extends HttpServlet {
	
	/* 서블릿 초기화 메서드를 정의하고 애플리케이션 초기화 파라미터인
	 * uploadDir을 읽어서 파일이 업로드 되는 폴더로 사용할 것임 
	 **/
	private static String uploadDir;
	private static File parentFile;

	@Override
	public void init() {
		// web.xml에 지정한 웹 어플리케이션 초기화 파라미터를 읽는다.
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

	// post 방식의 요청을 처리하는 메소드
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		// 하나의 게시 글 정보를 저장하는 자바빈 객체 생성		 
		Board board = new Board();
		
		// 기본 임시 디렉터리 확인
		System.out.println(System.getProperty("java.io.tmpdir"));

		// Servlet 3.0부터 지원하는 Part AIP를 이용한 파일 업로드 구현하기
		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		
		// Form에서 전송된 요청이 multipart/form-data 인지 체크
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
		
			// request 객체를 이용해 요청 본문(body)으로 들어온 데이터를 Part 리스트로 받음 
			Collection<Part> parts = request.getParts();
			
			for(Part part : parts) {
				
				// 각각의 Part 별로 Content-Disposition 속성이 있음
				String partHeader = part.getHeader("Content-Disposition");
				System.out.println(partHeader);
				System.out.printf("파라미터 : %s, contentType : %s, size : %dByte, \n", 
						part.getName(), part.getContentType(), part.getSize());
				
				/* 현재 Part가 파일 데이터라면 getHeader("Content-Disposition")
				 * 메서드로 데이터를 읽어오면 다음과 같다.
				 * 
				 * form-data; name="file1"; filename="업로드된 파일명"
				 * 
				 * "filename="이 포함되어 있으면 현재 Part는 파일 데이터가 된다. 
				 * 이외에 파일 데이터인지 판단하는 방법으로 Servlet 3.1부터 
				 * 지원하는 getSubmittedFileName() 메서드를 이용해서 파일인지를
				 * 판단할 수 있다. 이 메서드는 현재 Part가 파일 데이터 일 때는 파일
				 * 이름을 반환하지만 파일 데이터가 아니라면 null을 반환한다.  
				 **/				
				if(partHeader.contains("filename=")) { // 현재 Part가 파일 데이터라면
					
					// 파일이 업로드되지 않으면 현재 Part의 크기는 0이 된다.
					if(part.getSize() > 0) {
						
						/* UUID(Universally Unique Identifier, 범용 고유 식별자)
						 * 소프트웨어 구축에서 쓰이는 식별자의 표준으로 네트워크상에서 서로 모르는
						 * 개체들을 식별하고 구별하기 위해서 사용된다. UUID 표준에 따라 이름을
						 * 부여하면 고유성을 완벽하게 보장할 수는 없지만 실제 사용상에서 중복될 
						 * 가능성이 거의 없다고 인정되기 때문에 실무에서 많이 사용되고 있다.
						 * 
						 * 파일 이름의 중복을 막고 고유한 파일 이름으로 저장하기 위해 java.util
						 * 패키지의 UUID 클래스를 이용해 랜덤한 UUID 값을 생성한다.
						 **/
						UUID uid = UUID.randomUUID();
						String saveName = uid.toString() + "_" + part.getSubmittedFileName();						
						String savePath = parentFile.getAbsolutePath() + File.separator + saveName;
						
						// 서버의 지정한 경로에 업로드된 파일을 복사한다.
						part.write(savePath);
						
						/* 파일 업로드시 사용된 임시 저장 데이터를 삭제한다. 
					 	 * 가비지 컬렉터가 알아서 처리하므로 필수는 아니다.
					 	 **/
						part.delete();
						
						// 수정한 파일명을 Board 객체의 file1 프로퍼티로 저장한다.
						board.setFile1(saveName);
						
					} else {
						System.out.println("파일이 업로드 되지 않음");
					}					
					
				} else { // 현재 Part가 파일 데이터가 아니라면
					
					/* 폼에서 전송된 폼 데이터의 파라미터 이름과 값을 읽는다.
					 *
					 * getName() 메소드는 현재 Part에서 파라미티 이름을 읽어온다.				 
					 * 이 이름을 이용해 Request 객체로부터 값을 읽어오면 된다.
					 **/				
					String paramName = part.getName();
					String paramValue = request.getParameter(paramName);
										
					// 파라미터를 구분하여 Board 객체에 저장한다. 
					if(paramName.equals("title")) {
						board.setTitle(paramValue);
						
					} else if(paramName.equals("writer")) {
						board.setWriter(paramValue);
						
					} else if(paramName.equals("pass")) {
						board.setPass(paramValue);
						
					} else if(paramName.equals("content")) {
						board.setContent(paramValue);						
					}					
				}
			}			
			System.out.println(board.getTitle() + ", " + board.getWriter());			
			
		} else {
			System.out.println("폼에서 전송된 요청이 mutipart/form-data가 아님");
		}	
		
		// BoardDao 객체를 생성해 게시 글을 DB에 추가한다.
		BoardDao dao = new BoardDao();
		dao.insertBoard(board);
		
		/* 게시 글쓰기가 완료된 후 response 내장객체의 sendRedirect() 메서드를
		 * 이용해 게시 글 리스트로 Redirect 시킨다. response 내장객체의 sendRedirect()
		 * 메서드는 요청한 자원이 다른 곳으로 이동되었다고 웹브라우저에게 응답하면서
		 * 이동할 URL을 알려주고 그 쪽으로 다시 요청하라고 응답하는 메소드이다. 
		 * 웹브라우저가 요청한 컨텐츠가 다른 곳으로 이동되었다고 응답하면서 그 쪽으로
		 * 다시 요청하라고 이동할 주소를 웹브라우저에게 알려주면 웹브라우저는 그 주소로
		 * 다시 요청하게 되는데 이를 리다이렉션이라고 한다.
		 *	 
		 * Redirect 기법은 웹 브라우저를 새로 고침(F5) 했을 때 동일한 코드가 다시
		 * 실행되면 문제가 될 수 있는 경우에 클라이언트의 요청을 모두 처리한 후 특정
		 * URL로 이동시키기 위해 주로 사용한다. 예를 들어 게시 글쓰기에 대한 요청을
		 * 처리한 후에 Redirect 시키지 않는다면 브라우저의 주소 표시줄에 게시 
		 * 글쓰기에 대한 URL이 그대로 남아 있기 때문에 사용자가 브라우저를 새로
		 * 고침(F5) 하게 되면 바로 이전에 실행된 게시 글쓰기 작업이 반복 실행되어
		 * 중복된 데이터가 DB에 저장되는 문제가 발행할 수 있다. 
		 * 이를 방지하기 위해서 게시 글쓰기가 완료되면 게시 글 리스트로 이동시키기
		 * 위해서 response 내장객체의 sendRedirect() 메서드를 사용해 게시 글
		 * 리스트의 URL을 웹 브라우저에게 응답하고 웹 브라우저는 응답 받은 URL로
		 * 다시 요청하도록 하는 것이다. 왜 게시 글 리스트로 리다이렉트 시켜야 하는가?
		 * 게시 글 리스트는 DB에서 데이터를 조회하는 쿼리인 SELECT 쿼리를 사용하기
		 * 때문에 이 쿼리가 실행되어도 데이터가 중복되어 저장되거나, 동일한 데이터를
		 * 반복적으로 수정 또는 삭제하려는 문제가 발생되지 않기 때문이다. 이렇게 게시
		 * 글쓰기와 같이 DB에서 데이터의 입력, 수정, 삭제 작업과 연동되는 경우에	 
		 * 사용자의 새로 고침(F5) 등으로 문제가 발생할 수 있기 때문에 Redirect를
		 * 사용한다. 이외에 다른 사이트로 이동시킬 때에도 Redirect 기법을 사용 한다.
		 **/
		response.sendRedirect("boardList");
	}
}

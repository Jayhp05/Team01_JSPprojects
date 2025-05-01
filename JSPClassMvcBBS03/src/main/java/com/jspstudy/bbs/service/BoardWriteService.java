package com.jspstudy.bbs.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

// 게시글 쓰기 요청을 처리하는 서비스 클래스
public class BoardWriteService  implements CommandProcess {
	
	public String requestProcess(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		// 하나의 게시글 정보를 저장하는 자바빈 객체 생성		 
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
						
						/* ServletContext 객체의 속성에 저장된 파일을 업로드할
						 * 디렉터리 정보를 읽어와 시스템에서 절대 경로를 구한다. 
						 **/
						File parentFile = 
								(File) request.getServletContext().getAttribute("parentFile");
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
		
		// BoardDao 객체를 생성해 게시글을 DB에 추가한다.
		BoardDao dao = new BoardDao();
		dao.insertBoard(board);

		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 * 
		 * 게시글 쓰기 요청을 처리하고 Redirect 시키지 않으면 사용자가 브라우저를
		 * 새로 고침 하거나 재요청할 때 마다 이미 DB에 추가된 게시글을 계속 추가하려는
		 * 동작으로 인해서 중복된 데이터가 저장되거나 또 다른 문제가 발생할 수 있다.
		 * 이런 경우에는 Redirect 기법을 이용해 DB에 추가, 수정, 삭제하는 동작이 아닌
		 * 조회하는 곳으로 이동하도록 하면 문제를 해결 할 수 있다. 
		 * 
		 * 현재 요청을 처리한 후에 Redirect 하려면 뷰 페이지를 지정하는 문자열 맨 앞에
		 * "r:" 또는 "redirect:"를 접두어로 붙여서 반환하고 Redirect가 아니라 Forward
		 * 하려면 뷰 페이지의 경로만 지정하여 문자열로 반환하면 Controller에서 판단하여
		 * Redirect 또는 Forward로 연결된다. 
		 * 
		 * 게시글 쓰기 폼으로부터 넘어온 신규 게시글을 DB에 저장한 후 게시글 리스트
		 * 페이지로 이동시키기 위해 View 페이지 정보를 반환할 때 맨 앞에 "r:" 접두어를
		 * 붙여서 게시글 리스트 보기 요청을 처리하는 URL를 지정하여 Controller로 넘기면
		 * Controller는 넘겨받은 View 페이지 정보를 분석하여 Redirect 시키게 된다.
		 * 
		 * Redirect는 클라이언트 요청에 대한 결과 페이지가 다른 곳으로 이동되었다고 
		 * 브라우저에게 알려주고 그 이동된 주소로 다시 요청하라고 브라우저에게 URL을
		 * 보내서 브라우저가 그 URL로 다시 응답하도록 처리하는 것으로 아래와 같이
		 * View 페이지 정보의 맨 앞에 "r:" 또는 "redirect:"를 접두어로 붙여서 반환하면
		 * Controller에서 View 페이지 정보를 분석해 Redirect 시키고 이 응답을 받은
		 * 브라우저는 게시글 리스트를 보여주는 페이지를 다시 요청하게 된다. 
		 *
		 * 지금과 같이 리다이렉트를 해야 할 경우 웹브라우저가 다시 요청할 주소만 응답하고
		 * 웹브라우저에서는 이 주소로 재요청하는 동작을 하므로 웹 템플릿 페이지인
		 * index.jsp를 기준으로 뷰 페이지를 지정하면 안 된다. 왜냐하면 리다이렉트는
		 * 뷰 페이지를 거쳐서 클라이언트로 응답되는 것이 아니라 현재 클라이언트가 요청한
		 * 주소가 다른 곳으로 이동되었다고 알려주기 위해 웹브라우저가 이동할 주소만
		 * 응답하고 웹 브라우저는 서버로부터 응답 받은 주소로 다시 요청하는 동작을 하기
		 * 때문에 뷰 페이지의 정보가 아닌 웹 브라우저가 이동할 주소를 지정해야 한다.
		 **/	
		return "r:boardList.mvc";		
	}
}

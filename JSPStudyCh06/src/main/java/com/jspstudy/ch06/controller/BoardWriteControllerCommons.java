package com.jspstudy.ch06.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.ch06.dao.BoardDao;
import com.jspstudy.ch06.vo.Board;

// Apache Commons Fileupload2를 이용해 파일 업로드를 구현한 서블릿 클래스
@WebServlet("/writeProcessCommons")
public class BoardWriteControllerCommons extends HttpServlet {
	
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
		
		/* Apache Commons Fileupload2를 이용한 파일 업로드 구현하기
	     *	
		 * 1. 폼에서 전송된 데이터가 multipart/form-data 인지 체크
		 * JakartaServletFileUpload 클래스의 isMultipartContent(request) 메소드는
		 * 요청 정보에 포함되어 전송된 데이터가 multipart/form-data 이면 true를
		 * 그렇지 않으면 false를 반환한다.
		 **/
		boolean isMultipart = JakartaServletFileUpload.isMultipartContent(request);	
		if(isMultipart) {

			// 2. 메모리나 파일로 업로드된 파일을 보관하는 FileItem의 Factory 객체 생성
			/* 요청 데이터가 multipart/form-data 일 때 서버로 전송된 파라미터와
			 * 파일을 보관하여 파일 업로드를 처리하기 위해 FileItem 객체를 사용해야 한다.
			 * FileItem 객체는 multipart/form-data로 전송된 파라미터(문자 데이터)와
			 * 파일 정보를 저장하고 있는 객체이다.
			 * 
			 * FileItem 객체를 생성해 주는 DiskFileItemFactory 객체를 생성한다.
			 *
			 * DiskFileItemFactory 객체는 업로드된 파일의 크기가 지정한 크기를
			 * 넘기 전까지는 업로드된 파일을 메모리에 저장하고 지정한 크기를 초과하면
			 * 임시 디렉터리에 파일로 저장해 작업한다.
			 * setSizeThreshold(int sizeThreshold) 메소드를 이용해 메모리에
			 * 저장할 수 있는 최대 크기를 Byte 단위로 지정할 수 있다. 이 작업을 생략하면
			 * 기본 크기는 10240Byte(10KB) 이다. 또한 setRepository(File repository)
			 * 메소드를 이용해 지정한 메모리 크기를 초과할 경우 사용할 임시 디렉터리를
			 * 지정할 수 있다. 이를 생략하면 시스템의 기본 임시 디렉터리를 사용하게 된다.
			 * 시스템의 기본 임시 디렉터리는 System.getProperty("java.io.tmpdir")
			 * 메소드를 통해 알아낼 수 있다.
			 **/
			DiskFileItemFactory factory = DiskFileItemFactory.builder().get();
			System.out.println("임시 디렉터리 : " + System.getProperty("java.io.tmpdir"));		
			
			// 3. 업로드 요청을 처리하는 ServletFileUpload 객체 생성한다.
			JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);
			
			// 개별 파일의 최대 크기를 10MB로 지정
			upload.setFileSizeMax(1024 * 1024 * 10);
			
			// 전체 파일의 최대 크기를 100MB로 지정
			upload.setSizeMax(1024 * 1024 * 100);

			// 4. 요청 정보에 포함되어 전송된 데이터를 처리하기 위해 FileItem 목록 구함
			/* FileItem은 multipart/form-data로 전송된 파라미터 또는 파일 정보를
			 * 저장하고 있는 객체로 JakartaServletFileUpload 클래스의 parseRequest()를
			 * 이용해 서버로 전송된 FileItem의 리스트를 List 타입으로 얻을 수 있다. 
			 **/		 
			List<FileItem> items = upload.parseRequest(request);
			
			// 5. 서버로 전송된 데이터에 접근하기 위해 Iterator 객체를 얻는다.
			Iterator<FileItem> iter = items.iterator();

			while(iter.hasNext()) {
				
				FileItem item = iter.next();
				
				/* 6. 현재 목록(item)이 폼 컨트롤에 입력된 문자열 데이터 인지 또는
				 * 업로드된 파일 데이터 인지를 구분해 처리한다.
				 *
				 * 아래는 현재 목록(item)이 폼에 입력된 파라미터 데이터 인지를 체크한다.
				 * isFormField() 메소드는 입력 파라미터일 경우 true를 반환 한다.
				 *
				 * file 선택상자가 아니라 일반적인 폼 필드에 입력된 데이터라면
				 * 아래 if() 문이 실행되고 파일이면 else 문이 실행된다.
				 **/
				if(item.isFormField()) { // 파일이 아니라 일반 폼 컨트롤(문자) 데이터라면
					
					/* 폼에서 전송된 폼 데이터의 파라미터 이름과 값을 읽는다.
					 *
					 * getFieldName() 메소드는 현재 목록(item)의 파라미터				 
					 * 이름을 반환한다. getString() 메소드는 지정한 문자 셋을 
					 * 적용해 현재 목록(item)의 파라미터의 값을 반환한다.
					 **/				
					String paramName = item.getFieldName();
					
					// 파라미터 데이터 UTF-8 문자셋 처리
					String paramValue = item.getString(Charset.forName("utf-8"));
					
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
					
				} else { // 파일 데이터라면					
					/* 폼으로부터 전송된 파일 데이터라면 전송된 파일을
					 * 서버의 특정 폴더에 저장하는 작업을 한다.
					 *
					 * 현재 목록(item)에 해당하는 파일 데이터의 파라미터 이름을 구한다.
					 **/
					String paramName = item.getFieldName();
					System.out.println("file 파리미터 이름 : " + paramName);
					System.out.println("원본 파일명 : " + item.getName());
					
					/* 파일명의 길이가 0보다 크면 업로드된 파일을 upload 폴더에 저장한다. 
					 * 파일명이 아닌 실제 전송된 파일의 크기로 유효한 파일인지를
					 * 검사하려면 파일의 크기를 Byte 단위로 환산해 long 형으로
					 * 반환하는 getSize() 메소드를 사용해 파일의 크기를 구하면 된다.
					 * 업로드된 파일의 크기가 0인 경우 즉 사용자가 업로드 하는 파일을
					 * 선택하지 않고 폼을 전송하는 경우 적절한 예외 처리가 필요하다.
					 **/
					System.out.println("업로드된 파일 크기 : " + item.getSize());
					if(item.getName().length() > 0) {
						
						/* UUID(Universally Unique Identifier, 범용 고유 식별자)
						 * 소프트웨어 구축에 쓰이는 식별자의 표준으로 네트워크상에서 서로 모르는
						 * 개체들을 식별하고 구별하기 위해서 사용된다. UUID 표준에 따라 이름을
						 * 부여하면 고유성을 완벽하게 보장할 수는 없지만 실제 사용상에서 중복될 
						 * 가능성이 거의 없다고 인정되기 때문에 실무에서 많이 사용되고 있다.
						 * 
						 * 파일 이름의 중복을 막고 고유한 파일 이름으로 저장하기 위해 java.util
						 * 패키지의 UUID 클래스를 이용해 랜덤한 UUID 값을 생성한다.
						 **/
						UUID uid = UUID.randomUUID();
						String saveName = uid.toString() + "_" + item.getName();
					
						// 서버의 지정한 경로에 업로드된 파일을 복사한다.
						item.write(Path.of(parentFile.getAbsolutePath(), saveName));						
						
						/* 파일 업로드시 사용된 임시 저장 데이터를 삭제한다. 
					 	 * 가비지 컬렉터가 알아서 처리하므로 필수는 아니다.
					 	 **/
						item.delete();
					
						// 수정된 파일명을 Board 객체의 file1 프로퍼티로 저장한다.
						board.setFile1(saveName);
						
					} else {
						System.out.println("파일이 업로드 되지 않음");
					}					
				} // end if(item.isFormField())
			} // end while
			
		} else {
			System.out.println("폼에서 전송된 요청이 mutipart/form-data가 아님");
		}
		
		// BoardDao 객체를 생성해 게시 글을 DB에 추가한다.
		BoardDao dao = new BoardDao();
		dao.insertBoard(board);
		System.out.println(board.getTitle() + ", " + board.getWriter());
		
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

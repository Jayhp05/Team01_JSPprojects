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
	/* commons-upload2 라이브러리를 이용한 파일 업로드 구현하기
     *	
	 * 1. 폼에서 전송된 데이터가 multipart/form-data 인지 체크
	 * ServletFileUpload 클래스의 isMultipartContent(request) 메소드는
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
		 * 저장할 수 있는 최대 크기를 byte 단위로 지정할 수 있다. 이 작업을 생략하면
		 * 기본 크기는 10240byte(10kb) 이다. 또한 setRepository(File repository)
		 * 메소드를 이용해 지정한 메모리 크기를 초과할 경우 사용할 임시 디렉터리를
		 * 지정할 수 있다. 이를 생략하면 시스템의 기본 임시 디렉터리를 사용하게 된다.
		 * 시스템의 기본 임시 디렉터리는 System.getProperty("java.io.tmpdir")
		 * 메소드를 통해 알아낼 수 있다.
		 **/
		DiskFileItemFactory factory = DiskFileItemFactory.builder().get();
		System.out.println("임시 디렉터리 : " + System.getProperty("java.io.tmpdir"));		
		
		// 3. 업로드 요청을 처리하는 ServletFileUpload 객체 생성한다.
		JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);
		
		// 업로드되는 개별 파일의 최대 크기를 Byte 단위로 지정
		upload.setFileSizeMax(1024 * 1024 * 10);
		
		// 업로드되는 전체 파일의 최대 크기를 Byte 단위로 지정
		upload.setSizeMax(1024 * 1024 * 50);


		// 4. 요청 정보에 포함되어 전송된 데이터를 처리하기 위해 FileItem 목록 구함
		/* FileItem은 multipart/form-data로 전송된 파라미터 또는 파일 정보를
		 * 저장하고 있는 객체로 JakartaServletFileUpload 클래스의 parseRequest()를
		 * 이용해 서버로 전송된 FileItem의 리스트를 List 타입으로 얻을 수 있다. 
		 **/		 
		List<FileItem> items = upload.parseRequest(request);
		
		// 5. 전송된 데이터에 접근하기 위해 Iterator 객체를 얻는다.
		Iterator<FileItem> iter = items.iterator();
		
		// 하나의 게시 글 정보를 저장하는 자바빈 객체 생성		 
		Board board = new Board();
		
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
				
				/* 6-1. 폼에서 전송된 폼 데이터의 파라미터 이름과 값을 읽는다.
				 *
				 * getFieldName() 메소드는 현재 목록(item)의 파라미터				 
				 * 이름을 반환한다. getString() 메소드는 지정한 문자 셋을 
				 * 적용해 현재 목록(item)의 파라미터의 값을 반환한다.
				 **/				
				String paramName = item.getFieldName();			
				//String paramValue = item.getString("utf-8");
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
				
				/* 6-2. 폼으로부터 전송된 파일 데이터라면 전송된 파일을
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
			
					File childFile = new File(parentFile, saveName);
					System.out.println("childFile : " + childFile);
				
					// 파일을 업로드할 위치에 복사한다.
					item.write(Path.of(parentFile.getAbsolutePath(), saveName));
					
					/* 파일 업로드시 사용된 임시 파일을 제거 한다. 
				 	 * 가비지 컬렉터가 알아서 하니 필수는 아니다.
				 	 **/
					item.delete();
				
					/* 업로드 파일명이 존재하면 파일명을 지정하고
					 * 파일명이 존재하지 않으면 파일 정보를 null로 지정 한다.
					 **/
					board.setFile1(saveName);
				}
				
			} // end if(item.isFormField())
		} // end while
		
		System.out.println(board.getTitle() + ", " + board.getWriter());
		
	} else {
		System.out.println("폼에서 전송된 요청이 mutipart/form-data가 아님");
	}	
%>

package com.jspstudy.bbs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 게시글 상세 보기에서 들어오는 파일을 다운로드 요청을 처리하는 모델 클래스
public class FileDownloadService implements CommandProcess {

	@Override
	public String requestProcess(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		/* 서블릿 초기화 메서드에서 업로드 파일이 저장될 폴더 정보를 서블릿 초기화
		 * 파라미터로부터 읽어와 그 로컬 경로를 ServletContext 객체에 저장하였다.
		 * 
		 * ServletContext 객체는 웹 애플리케이션 당 1개가 생성되며 웹 애플리케이션이
		 * 구동되는데 필요한 정보를 담고 있는 객체로 JSP 페이지에서는 application
		 * 내장객체로 접근할 수 있다. 아래와 같이 ServletContext 객체의 속성에
		 * 저장되면 이 웹 애플리케이션의 모든 컴포넌트에서 이 정보를 사용할 수 있다. 
		 *
		 * request 객체의 getServletContext() 메서드를 이용해 ServletContext 
		 * 객체를 구하고 그 속성에 저장된 uploadDir 정보를 읽고 있다.
		 * 
		 * 이 예제는 경로를 포함해서 파일 이름을 저장했기 때문에 아래가 필요 없을 수 있지만
		 * 파일명만 저장하고 필요한 경로를 추가로 지정하는 방식을 사용할 경우 아래와 같이
		 * 기준이 되는 폴더를 ServletContext 객체의 속성에 저장하고 웹 애플리케이션의
		 * 여러 컴포넌트에서 이를 가져다 사용할 수 있도록 구현할 수 있다.
		 **/
		ServletContext ctx = request.getServletContext();
		String downDir = (String) ctx.getAttribute("uploadDir");
		
		/* 보안적인 부분을 생각한다면 게시글의 no 정도만 요청 파라미터로 받아 그 no에 
		 * 해당하는 파일 정보를 DB로부터 읽어와 처리하는 방식으로 처리해야 하나, 여기서는
		 * 파일 이름을 받아서 바로 다운로드 처리하는 방식을 사용했다.
		 **/ 
		String fileName = request.getParameter("fileName");
		
		// 파일의 위치를 현재 시스템을 기준으로 실제 경로를 구하여 File 객체를 생성한다.		  
		String downPath = ctx.getRealPath(downDir + "\\" + fileName);
		File file = new File(downPath);
				
		System.out.println("downPath : " + downPath);
		System.out.println("filePath : " + file.getPath() + ", " + file.getName());
				
		// ServletContext 객체를 통해 다운로드할 파일 유형을 구한다.
		String mimeType =  ctx.getMimeType(downPath);
		System.out.println("mimeType : " + mimeType);
		
		// 알려지지 않은 파일 유형이 이라면  
		if(mimeType == null) {
			/* 파일 유형이 알려지지 않은 파일일 경우 일련된 8bit 스트림 형식을 지정한다.
			 * 이 방식은 알려지지 않은 파일 유형의 대한 읽기 형식을 지정할 때 사용한다.
			 **/
			mimeType = "application.octec-stream";
		}
		
		// 응답 객체에 파일 유형에 대한 컨텐츠 타입을 지정한다.
		response.setContentType(mimeType);
		
		/* 파일 이름이 한글이 포함되어 있으면 깨질수 있기 때문에 인코딩 처리를 해 준다.
		 * 파일명에 공백이 존재하면 아래 인코딩 시에 공백은 "+" 기호로 바뀌게 된다. 
		 **/
		String fileNameEncoding = URLEncoder.encode(file.getName(), "UTF-8");
		
		/* 파일명에 공백이 있으면 위에서 "+"로 처리되기 때문에 
		 * "+" 기호를 다시 공백으로 변경해 준다.
		 **/
		fileName = fileNameEncoding.replaceAll("\\+", "%20");
		
		/* 기타 추가적인 정보를 응답 객체의 헤더에 추가한다.
		 * 브라우저가 이 헤더를 읽어 다운로드로 연결되는 화면을 출력해 준다.  
		 **/
		response.setHeader("Content-Disposition", 
				"attachment; filename=" + fileName);
		
		// 업로드된 파일을 읽어올 InputStream을 개설한다.
		FileInputStream in = new FileInputStream(file);
		
		// 응답 객체를 통해 파일을 보내기 위해 OutputStream을 개설한다.
		ServletOutputStream downStream = response.getOutputStream();
		
		// 버퍼로 사용할 byte 배열 생성 - 1MB 씩 한 번에 읽을 예정
		byte[] b = new byte[1 * 1024 * 1024];
		int readByte = 0;
		
		while((readByte = in.read(b, 0, b.length)) != -1) {
			downStream.write(b, 0, readByte);
		}
		
		// 스트림에 남은 모든 데이터를 전송하고 스트림을 닫는다.
		downStream.flush();
		downStream.close();
		in.close();		
	
		/* viewPage 정보가 null 이면 컨트롤러에서 뷰를 거치지 않고 그대로 응답되기
		 * 때문에 위에서 응답 객체를 통해 연결한 스트림으로 파일이 전송되어 브라우저로
		 * 응답되고 브라우저는 이를 다운로드로 연결해 사용자에게 보여준다.
		 **/		
		return null;
	}
}

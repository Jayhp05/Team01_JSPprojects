package com.jspstudy.bbs.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.UUID;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


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
@WebServlet("/updateProcess")
public class BoardUpdateController extends HttpServlet {

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
		
		// 요청 본문으로 들어오는 데이터의 문자셋을 UTF-8로 설정
		request.setCharacterEncoding("UTF-8");
		
		/* 요청 객체에서 no, pass, pageNum을 먼저 읽어와 이 데이터들이 비어
		 * 있는지 확인한다. 만약 이 데이터들이 비어있다면 우리가 의도하지
		 * 않은 비정상적인 접근이 된다. 
		 **/		
		String sNo = request.getParameter("no");		
		String pass = request.getParameter("pass");
		String pageNum = request.getParameter("pageNum");
		
		/* no, pass, pageNum이 비어 있으면 비정상적인 요청이므로 경고 창을
		 * 띄우고 브라우저의 history 객체를 이용해 바로 이전으로 돌려보내기
		 * 위해서 자바스크립트로 응답을 작성해 클라이언트로 보낸다.
		 **/
		if(sNo == null || sNo.equals("") || pass == null || pass.equals("")
			|| pageNum == null || pageNum.equals("")) {				
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();				
			out.println("<script>");
			out.println("	alert('잘못된 접근입니다.');");
			out.println("	history.back();");
			out.println("</script>");
			return;
		}		
		
		// BoardDao 객체 생성
		BoardDao dao = new BoardDao();	
		
		// 게시글의 비밀번호를 체크해 맞지 않으면 이전으로 돌려보낸다.
		boolean isPassCheck = dao.isPassCheck(Integer.parseInt(sNo), pass);
		if(! isPassCheck) {
			/* 문자열을 보다 효율적으로 다루기 위해서 StringBuilder 객체를 이용해
			 * 응답 데이터를 작성하고 있다. 아래에서는 비밀번호가 틀리면 사용자에게
			 * 경고 창을 띄우고 브라우저의 history 객체를 이용해 바로 이전으로 
			 * 돌려보내기 위해서 자바스크립트로 응답을 작성하고 있다.
			 **/
			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("	alert('비밀번호가 맞지 않습니다.');");
			sb.append("	history.back();");
			sb.append("</script>");
			
			/* 응답 객체에 연결된 PrintWriter 객체를 이용해 응답 데이터를 전송하고
			 * 더 이상 실행할 필요가 없으므로 return 문을 이용해 현재 메서드를 종료한다.
			 **/
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(sb.toString());			
			return;
		}
		
		/* 비밀번호가 맞으면 하나의 게시글 정보를 저장하는 VO 객체를 생성하고
		 * 요청 파라미터로 넘겨받은 데이터를 Board 객체에 저장한다.
		 **/
		Board board = new Board();
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");		
		String content = request.getParameter("content");
		
		board.setNo(Integer.parseInt(sNo));
		board.setTitle(title);
		board.setWriter(writer);
		board.setPass(pass);
		board.setContent(content);
		
		// Servlet 3.0부터 지원하는 Part AIP를 이용한 파일 업로드 구현하기
		// 파일에 해당하는 요청 정보를 Part로 얻어온다.		
		Part part = request.getPart("file1");
		
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
		
		// BoardDao 객체를 이용해 게시글을 수정한다.
		dao.updateBoard(board);
		
		String type = request.getParameter("type");	
		String keyword = request.getParameter("keyword");
		
		/* 요청 파라미터에서 type이나 keyword가 비어 있으면 일반 
		 * 게시글 리스트에서 넘어온 요청으로 간주하여 false 값을 갖게 한다.
		 * 이 정보는 게시글 리스트와 검색 리스트로 구분해 돌려보내기 위해 필요하다.
		 **/
		boolean searchOption = (type == null || type.equals("") 
				|| keyword == null || keyword.equals("")) ? false : true; 	
		
		/* 리다이렉트 할 때 게시글 리스트의 페이지 번호를 파라미터로 넘겨 사용자가 
		 * 게시글 수정을 요청한 페이지와 동일한 페이지로 리다이렉트 시킨다.
		 **/
		String url = "boardList?pageNum=" + pageNum;
		
		/* 검색 리스트 상태에서 게시글 상세보기로 들어와 게시글을 수정하는 것이라면 
		 * 검색 옵션에 해당하는 검색한 결과에 대한 게시글 리스트 페이지로 Redirect
		 * 시켜야 하므로 type과 keyword를 Redirect 주소에 추가한다.
		 * Redirect 기법은 요청한 결과가 이동했다고 브라우저에게 이동할 주소를 응답하는
		 * 것으로 브라우저는 주소 표시줄에 주소를 입력해 요청하게 되므로 GET 방식 요청이다. 
		 **/
		if(searchOption) {
			
			/* 리다이렉트 할 때 파라미터에 한글이 포함되어 있으면 한글로 된 파라미터 값은
			 * 공백문자로 변경되어 리다이렉트 되기 때문에 한글 데이터는 깨지게 된다.
			 * 이런 경우에는 java.net 패키지의 URLEncoder 클래스를 이용해 아래와
			 * 같이 수동으로 URL 인코딩을 하면 이 문제를 해결할 수 있다.
			 **/	
			keyword = URLEncoder.encode(keyword, "utf-8");
			url += "&type=" + type + "&keyword=" + keyword;
		}
		System.out.println("keyword : " + keyword);
		System.out.println("url : " + url);		

		/* 게시글 수정이 완료된 후 response 내장객체의 sendRedirect() 메서드를
		 * 이용해 게시글 리스트로 Redirect 시킨다. response 내장객체의 sendRedirect()
		 * 메서드는 요청한 자원이 다른 곳으로 이동되었다고 웹브라우저에게 응답하면서
		 * 이동할 URL을 알려주고 그 쪽으로 다시 요청하라고 응답하는 메소드이다.
		 * 웹 브라우저가 요청한 컨텐츠가 다른 곳으로 이동되었다고 응답하면서 그 쪽으로
		 * 다시 요청하라고 이동할 주소를 웹브라우저에게 알려주면 웹브라우저는 그 주소로
		 * 다시 요청하게 되는데 이를 리다이렉션이라고 한다.
		 * 
		 * 게시글 수정이 완료된 후 Redirect 시키지 않으면 이 페이지를 새로 고침 하여
		 * 재요청 할 때 마다 이미 수정된 게시글을 계속해서 수정하려고 하는 문제가 발생한다.
		 * 
		 * 리다이렉트 할 때 페이지 번호를 요청 파라미터로 넘겨줘야 사용자가 이전에 있었던
		 * 게시글 리스트 페이지로 이동할 수 있다.
		 **/		
		response.sendRedirect(url);
	}
}

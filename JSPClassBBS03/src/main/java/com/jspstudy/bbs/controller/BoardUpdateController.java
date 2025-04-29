package com.jspstudy.bbs.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

@MultipartConfig(fileSizeThreshold = 1024 * 10,
					maxFileSize = 1024 & 1024 * 10,
					maxRequestSize = 1024 * 1024 * 10 * 10)
@WebServlet("/updateProcess")
public class BoardUpdateController extends HttpServlet {

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
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		// POST 방식의 요청에 대한 문자 셋 처리
		request.setCharacterEncoding("utf-8");
		
		// 요청 파라미터를 저장할 변수 선언 
		String pass= null, title = null, writer = null, content = null;
		int no = 0;	

		/* 사용자가 폼에서 수정한 데이터를 요청 파라미터로부터 읽어온다.
		 *
		 * 게시글을 수정하기 위해서 updateForm.jsp에서 게시글 수정 요청을
		 * 하면서 테이블에서 게시글 하나를 유일하게 구분할 수 있는 게시글 번호를
		 * 요청 파라미터로 보냈기 때문에 이 게시글 번호를 요청 파라미터로부터 읽어
		 * BoardDao를 통해서 게시글 번호에 해당하는 게시글의 내용을 수정 할 수 있다.
		 *
		 * 아래에서 no라는 요청 파라미터가 없다면 NumberFormatException 발생
		 **/	 
		String sNo = request.getParameter("no");
		pass = request.getParameter("pass");
		String pageNum = request.getParameter("pagaNum");
		
//		no, pageNum이 없으면 우리가 의도한 것이 아님
//		직접 응답을 만들어서 (js로 응답) 알림하고 게시글 리스트로 보냄
		if(sNo == null || sNo.equals("") || pass == null || pass.equals("") || pageNum == null || pageNum.equals("")) {
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("	alert('잘못된 접근입니다.');");
//			out.println("	history.back();");
			out.println("	location.href='boardList'");
			out.println("</script>");
		}
		
		// BoardDao 객체를 생성하고 게시글 비밀번호를 체크해 맞지 않으면 이전으로 돌려보낸다.
		BoardDao dao = new BoardDao();	
		boolean isPassCheck = dao.isPassCheck(Integer.parseInt(sNo), pass);
		
		// 게시글 번호에 해당하는 게시글 비밀번호가 틀리다면 
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

			/* 응답 객체에 연결된 JspWriter 객체를 이용해 응답 데이터를 전송하고
			 * 더 이상 실행할 필요가 없으므로 return 문을 이용해 현재 메서드를 종료한다.
			 **/
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(sb.toString());
			System.out.println("비밀번호 맞지 않음");
			return;
		} 
		
		// 비밀번호가 맞으면 사용자가 게시글 수정 폼에 입력한 데이터를 읽어온다.
		title = request.getParameter("title");
		writer = request.getParameter("writer");		
		content = request.getParameter("content");
		
		/* 하나의 게시글 정보를 저장하는 자바빈 객체를 생성하고 파라미터로
		 * 넘겨받은 요청 데이터를 Board 객체에 저장한다.
		 **/	 
		Board board = new Board();
		board.setNo(Integer.parseInt(sNo));
		board.setTitle(title);
		board.setWriter(writer);
		board.setPass(pass);
		board.setContent(content);
		
//		파일 처리
		Part part = request.getPart("file1");
		if(part.getSize() > 0) { // 파일이 업로드된 경우
			UUID uid = UUID.randomUUID();
			String saveName = uid.toString() + "_" + part.getSubmittedFileName();
			String savePath = parentFile.getAbsolutePath() + File.separator + saveName;
			
			part.write(savePath);
			part.delete();
			board.setFile1(saveName);
		}
		else {
			System.out.println("파일이 업로드 되지 않았습니다.");
		}
		
		// BoardDao의 updateBoard() 메서드를 이용해 DB에서 게시글을 수정한다.	
		dao.updateBoard(board);	
		
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		
		boolean searchOption = (type == null || type.equals("") || keyword == null || keyword.equals("")) ? false : true; 

		String url = "boardList?pageNum=" + pageNum;

		if(searchOption) {
				keyword = URLEncoder.encode(keyword, "utf-8");
				url += "&type=" + type + "&keyword=" + keyword;
		}

		System.out.println("keyword : " + keyword);
		System.out.println("url : " + url);	

//		현재 요청한 자원이 이동되었다고 응답하면서
//		이동되는 url을 알려주는 것 - 리다이렉트
		response.sendRedirect("boardList?pageNum=" + pageNum);
	}
}

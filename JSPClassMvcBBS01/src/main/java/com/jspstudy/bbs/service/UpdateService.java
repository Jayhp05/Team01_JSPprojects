package com.jspstudy.bbs.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.UUID;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

//	게시글 수정 요청을 처리하는 서비스 클래스
public class UpdateService {

	public String requestProcess (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sNo = request.getParameter("no");		
		String pass = request.getParameter("pass");
		String pageNum = request.getParameter("pageNum");
		
		if(sNo == null || sNo.equals("") || pass == null || pass.equals("")
			|| pageNum == null || pageNum.equals("")) {				
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();				
			out.println("<script>");
			out.println("	alert('잘못된 접근입니다.');");
			out.println("	history.back();");
			out.println("</script>");
			return null;
		}		
		
		// BoardDao 객체 생성
		BoardDao dao = new BoardDao();	
		
		// 게시글의 비밀번호를 체크해 맞지 않으면 이전으로 돌려보낸다.
		boolean isPassCheck = dao.isPassCheck(Integer.parseInt(sNo), pass);
		if(! isPassCheck) {

			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("	alert('비밀번호가 맞지 않습니다.');");
			sb.append("	history.back();");
			sb.append("</script>");
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(sb.toString());			
			return null;
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

		Part part = request.getPart("file1");
		
		// 파일이 업로드되지 않으면 현재 Part의 크기는 0이 된다.
		if(part.getSize() > 0) {

			UUID uid = UUID.randomUUID();
			String saveName = uid.toString() + "_" + part.getSubmittedFileName();
			File parentFile = (File) request.getServletContext().getAttribute("parentFile");
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

		boolean searchOption = (type == null || type.equals("") 
				|| keyword == null || keyword.equals("")) ? false : true; 	
		
		String url = "boardList?pageNum=" + pageNum;
		
		if(searchOption) {	
			keyword = URLEncoder.encode(keyword, "utf-8");
			url += "&type=" + type + "&keyword=" + keyword;
		}
		System.out.println("keyword : " + keyword);
		System.out.println("url : " + url);	
		
		return "r:boardList.mvc";
	}
	
}

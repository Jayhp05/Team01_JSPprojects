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

// 게시글 수정 폼에서 요청한 데이터를 받아 DB에 수정하는 모델 클래스
public class UpdateService  implements CommandProcess {
	
	public String requestProcess(
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
			return null;
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
		String url = "boardList.mvc?pageNum=" + pageNum;
		
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
		
		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 * 
		 * 게시글 수정하기 요청을 처리하고 Redirect 시키지 않으면 사용자가 브라우저를
		 * 새로 고침 하거나 재요청할 때 마다 이미 DB에서 수정된 게시글을 계속 수정하려는
		 * 동작으로 인해서 문제가 발생할 수 있다. 이런 경우에는 Redirect 기법을 이용해 DB에
		 * 추가, 수정, 삭제가 아닌 조회하는 곳으로 이동하도록 하면 문제를 해결 할 수 있다. 
		 * 
		 * 현재 요청을 처리한 후에 Redirect 하려면 뷰 페이지를 지정하는 문자열 맨 앞에
		 * "r:" 또는 "redirect:"를 접두어로 붙여서 반환하고 Redirect가 아니라 Forward
		 * 하려면 뷰 페이지의 경로만 지정하여 문자열로 반환하면 Controller에서 판단하여
		 * Redirect 또는 Forward로 연결된다. 
		 * 
		 * 게시글 수정 폼으로부터 넘어온 게시글을 DB에서 수정한 후 게시글 리스트
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
		 **/
		return "r:" + url;	
	}
}
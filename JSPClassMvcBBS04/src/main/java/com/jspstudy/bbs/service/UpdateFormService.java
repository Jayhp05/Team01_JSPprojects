package com.jspstudy.bbs.service;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

// 게시글 수정 폼 요청을 처리하는 모델 클래스
public class UpdateFormService  implements CommandProcess {
	
	public String requestProcess(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {	
		
		String sNo = request.getParameter("no");
		String pass = request.getParameter("pass");
		String pageNum = request.getParameter("pageNum");
		String type = request.getParameter("type");	
		String keyword = request.getParameter("keyword");

		/* 글 번호와 페이지 번호가 파라미터로 전달되지 않는 요청은 정상적인 접근이
		 * 아니므로 자바스크립트를 사용해 경고 창을 띄우고 브라우저에 저장된 
		 * 이전 페이지로 돌려보낸다. Controller로 viewPage 정보를 반환해야 
		 * 하지만 이 경우 viewPage 정보가 없으므로 PrintWriter 객체를
		 * 이용해 클라이언트로 응답할 자바스크립트 코드를 출력하고 null을
		 * 반환하면 Controller에서는 viewPage 정보가 null이 아닐 경우만
		 * 처리하게 되므로 자바스크립트가 브라우저로 전송되어 경고 창이 뜨게 된다.   
		 **/
		if(sNo == null || sNo.equals("") || pass == null || pass.equals("")
			|| pageNum == null || pageNum.equals("")) {

			/* 스트림에 직접 쓰기위해 응답 객체로부터 스트림을 구한다.
			 * 응답 객체의 스트림을 구하기 전해 ContentType이 설정되어야 한다. 
			 * 그렇지 않으면 한글과 같은 데이터는 깨져서 출력된다.
			 **/			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("	alert('정상적인 접근이 아닙니다.');");
			out.println("	history.back();");
			out.println("</script>");
			return null;
		}
		
		BoardDao dao = new BoardDao();
		int no = Integer.parseInt(sNo);
		boolean isPassCheck = dao.isPassCheck(no, pass);
		
		if(! isPassCheck) {
			/* 스트림에 직접 쓰기위해 응답 객체로부터 스트림을 구한다.
			 * 응답 객체의 스트림을 구하기 전해 ContentType이 설정되어야 한다. 
			 * 그렇지 않으면 한글과 같은 데이터는 깨져서 출력된다.
			 **/			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("	alert('비밀번호가 맞지 않습니다.');");
			out.println("	history.back();");
			out.println("</script>");
			return null;
		}
		
		/* 요청 파라미터에서 type이나 keyword가 비어 있으면 일반 
		 * 게시글 리스트에서 넘어온 요청으로 간주하여 false 값을 갖게 한다.
		 * 이 정보는 게시글 리스트와 검색 리스트로 구분해 돌려보내기 위해 필요하다.
		 **/
		boolean searchOption = (type == null || type.equals("") 
				|| keyword == null || keyword.equals("")) ? false : true; 	
		
		/* BoardDAO 객체를 얻어 수정할 게시글 하나를 읽어와 Board
		 * 타입의 변수에 저장한다. BoardDAO 클래스의 getBoard()는 첫 번째
		 * 매개변수에 지정한 글 번호에 해당하는 게시글 하나를 반환하는 메소드로
		 * 두 번째 인자에 false를 지정하면 게시글 읽은 횟수는 증가되지 않는다.
		 **/		
		Board board = dao.getBoard(Integer.valueOf(no), false);
		
		// 게시글 정보와 페이지 정보를 Request 속성 영역에 저장 한다.
		request.setAttribute("board", board);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchOption", searchOption);

		// 검색 요청이면 type과 keyword를 request 영역에 저장한다.
		if(searchOption) {			
			request.setAttribute("type", type);			
			request.setAttribute("keyword", keyword);
		}		
		
		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 * 
		 * 게시글 수정 폼 요청에 대한 결과(모델)를 request 영역의 속성에 저장하고
		 * 요청에 대한 결과(모델)를 출력할 View 페이지와 View 페이지를 호출하는 방식을
		 * 아래와 같이 문자열로 지정하면 된다. 현재 요청을 처리한 후에 Redirect 하려면
		 * 뷰 페이지를 지정하는 문자열 맨 앞에 "r:" 또는 "redirect:"를 접두어로 붙여서
		 * 반환하고 Redirect가 아니라 Forward 하려면 뷰 페이지의 경로만 지정하여 
		 * 문자열로 반환하면 Controller에서 판단하여 Redirect 또는 Forward로 연결된다.   
		 * 또한 Forward 할 때 뷰 페이지의 정보 중에서 앞부분과 뒷부분에서 중복되는 
		 * 정보를 줄이기 위해서 Controller에서 PREFIX와 SUFFIX를 지정해 사용하기
		 * 때문에 매번 중복되는 부분을 제외하고 뷰 페이지의 정보를 지정하면 된다. 
		 * 
		 * 웹 템플릿을 적용하여 뷰를 만드는 경우 Controller에서 PREFIX에 웹 템플릿의
		 * 위치가 지정되어 있으므로 PREFIX와 SUFFIX를 제외하고 뷰의 정보를 지정하면
		 * 되지만 만약 웹 템플릿을 적용하지 않고 별도로 뷰를 만드는 경우에는 Forward 
		 * 할 때 PREFIX가 적용되지 않도록 Controller에 알려주기 위해서 아래 주석으로
		 * 처리한 return 문과 같이 뷰 페이지 정보를 지정하는 문자열의 맨 앞에 "f:" 또는
		 * "forward:"를 접두어로 붙여서 반환하면 된다.  
		 **/
		// return "f:/WEB-IN/member/overlapidCheck.jsp";	
		return "board/updateForm";
	}
}
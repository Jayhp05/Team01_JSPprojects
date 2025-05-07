package com.jspstudy.bbs.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.BoardDao;

// 게시글 삭제 요청을 받아 DB에서 게시글을 삭제하는 모델 클래스
public class DeleteService  implements CommandProcess {
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
		
		// BoardDao 객체를 이용해 게시글을 삭제한다.
		dao.deleteBoard(no);	
		
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

		/* 검색 리스트 상태에서 게시글 상세보기로 들어와 게시글을 삭제하는 것이라면 
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

		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 * 
		 * 게시글 삭제하기 요청을 처리하고 Redirect 시키지 않으면 사용자가 브라우저를
		 * 새로 고침 하거나 재요청할 때 마다 이미 DB에서 삭제된 게시글을 계속 삭제하려는
		 * 동작으로 인해서 문제가 발생할 수 있다. 이런 경우에는 Redirect 기법을 이용해 DB에
		 * 추가, 수정, 삭제가 아닌 조회하는 곳으로 이동하도록 하면 문제를 해결 할 수 있다. 
		 * 
		 * 현재 요청을 처리한 후에 Redirect 하려면 뷰 페이지를 지정하는 문자열 맨 앞에
		 * "r:" 또는 "redirect:"를 접두어로 붙여서 반환하고 Redirect가 아니라 Forward
		 * 하려면 뷰 페이지의 경로만 지정하여 문자열로 반환하면 Controller에서 판단하여
		 * Redirect 또는 Forward로 연결된다. 
		 * 
		 * 게시글 삭제하기 요청을 처리한 후에 게시글 리스트 페이지로 이동시키기 위해 
		 * View 페이지 정보를 반환할 때 맨 앞에 "r:" 접두어를 붙여서 게시글 리스트 보기
		 * 요청을 처리하는 URL를 지정하여 Controller로 넘기면 Controller는 넘겨 받은
		 * View 페이지 정보를 분석하여 Redirect 시키게 된다.
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
		return "r:" + url;
	}
}

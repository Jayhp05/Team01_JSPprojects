package com.jspstudy.bbs.controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

// 게시글 리스트 요청을 처리하는 컨트롤러 클래스
@WebServlet("/boardList")
public class BoardListController extends HttpServlet {

	// 한 페이지에 보여 줄 게시글의 수를 상수로 선언하고 있다.
	private static final int PAGE_SIZE = 10;
	 
	/* 한 페이지에 보여 질 페이지 그룹의 수를 상수로 선언하고 있다.
	 * [이전] 1 2 3 4 5 6 7 8 9 10 [다음]	
	 **/
	private static final int PAGE_GROUP = 10;
	
	// post 방식의 요청을 처리하는 메서드
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		/* 검색 기능의 POST 방식 요청에 따른 문자 셋 처리
		 * 게시글 리스트에서 검색 버튼이 클릭되면 자바스크립트로 유효성 검사를 한 후
		 * POST 방식으로 요청하게 되는데 검색어에 한글이 포함되어 있을 경우 적절한 
		 * 문자 셋 처리를 하지 않으면 한글이 깨지므로 DB에서 검색이 제대로 되지 못한다.    
		 **/
		request.setCharacterEncoding("utf-8");
		
		/* 이후의 처리는 GET 방식과 POST 방식 요청 처리가 동일하기 때문에
		 * doGet() 메서드를 호출해 이 메서드 안에서 나머지를 처리하도록 했다.  
		 */
		doGet(request, response);
	}
	
	// get 방식의 요청을 처리하는 메소드
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		// 요청 파라미터로 넘어 온 페이지 번호를 읽어온다.
		String pageNum = request.getParameter("pageNum");
		String type = request.getParameter("type");	
		String keyword = request.getParameter("keyword");
		
		/* pageNum이 null 이면 처음 게시글 리스트를 요청하거나 게시글 쓰기에서 
		 * Redirect 되어 넘어온 요청으로 pageNum을 1페이지로 설정한다.
		 * 즉 첫 번째 페이지에 해당하는 게시글 리스트를 화면에 출력한다. 
		 **/
		if(pageNum == null) {
			pageNum = "1";
		}

		// 요청 파라미터의 pageNum을 int 형으로 변환하여 현재 페이지로 설정
		int currentPage = Integer.parseInt(pageNum);
		
		/* 요청한 페이지에 해당하는 게시글의 첫 번째 행의 값을 계산한다.
		 * 현재 페이지가 1일 경우 startRow는 1, 2페이지 일 경우 startRow는 11이 된다.	 
		 * 
		 * 테이블에서 현재 페이지에 해당하는 게시글을 검색할 때 ROWNUM을 사용했다.
		 * ROWNUM은 쿼리의 결과로 검색되는 행들의 순서 값을 가진 의사컬럼으로
		 * 1부터 시작한다. 예를 들어 3페이지에 해당하는 게시글 리스트를 가져 온다면
		 * 한 페이지에 보여줄 게시글의 수가 10개로 지정되어 있으므로 startRow는 21이 된다. 
		 * 즉 아래의 공식에 의해 startRow(21) = 3 * 10 - (10 - 1);
		 * 첫 번째 페이지 startRow = 1, 두 번째 페이지 startRow = 11이 된다.
		 **/ 
		int startRow = currentPage * PAGE_SIZE - (PAGE_SIZE - 1);
		//int startRow = (currentPage - 1) * PAGE_SIZE + 1;
		
		int endRow = startRow + PAGE_SIZE - 1;	
		int listCount = 0;	
		ArrayList<Board> bList = null;	
		
		// BoardDao 객체 생성 
		BoardDao dao = new BoardDao();
		
		
		/* 요청 파라미터에서 type이나 keyword가 비어 있으면 일반 
		 * 게시글 리스트를 요청하는 것으로 간주하여 false 값을 갖게 한다.
		 **/
		boolean searchOption = (type == null || type.equals("") 
				|| keyword == null || keyword.equals("")) ? false : true; 
		
		// 검색 요청이 아니면
		if(! searchOption) {
			// 전체 게시글 수를 구한다.
			listCount = dao.getBoardCount();
			bList = dao.boardList(startRow, endRow);
			
		} else { // 검색 요청이면
			// 검색어에 해당하는 게시글 수를 구한다.
			listCount = dao.getBoardCount(type, keyword);
			bList = dao.searchList(type, keyword, startRow, endRow);
		}
		System.out.println("listCount : " + listCount);
		
		/* 페이지 그룹 이동 처리를 위해 게시판의 전체 페이지 수를 계산한다.
		 * [이전] 11 12 13...   또는   ... 8 9 10 [다음] 처리
		 * 전체 페이지 = 전체 게시글 수 / 한 페이지에 표시할 게시글 수가 되는데 
		 * 이 계산식에서 나머지가 존재하면 전체 페이지 수는 전체 페이지 + 1이 된다.
		 **/
		int pageCount = listCount / PAGE_SIZE 
					+ (listCount % PAGE_SIZE == 0 ? 0 : 1);
		
		/* 페이지 그룹 처리를 위해 페이지 그룹별 시작 페이지와 마지막 페이지를 계산하여
		 * 페이지 그룹의 시작 페이지 : 1, 11, 21, 31...
		 * 첫 번째 페이지 그룹에서 페이지 리스트는 1 ~ 10 되므로 currentPage가
		 * 1 ~ 10 사이에 있으면 startPage는 1이 되고 11 ~ 20 사이는 11이 된다. 
		 *
		 * 정수형 연산의 특징을 이용해 startPage를 아래와 같이 구할 수 있다.
		 * 아래 계산식으로 계산된 결과를 보면 현재 그룹의 마지막 페이지일 경우
		 * startPage가 다음 그룹의 시작 페이지가 나오게 되므로 삼항 연산자를
		 * 사용해 현재 페이지가 속한 그룹의 startPage가 되도록 조정 하였다.
		 **/
		 int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1
			- (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
			
		// 현재 페이지 그룹의 마지막 페이지 : 10, 20, 30...
		int endPage = startPage + PAGE_GROUP - 1;
		
		/* 위의 식에서 endPage를 구하게 되면 endPage는 항상 PAGE_GROUP의
		 * 크기만큼 증가(10, 20, 30 ...) 되므로 맨 마지막 페이지 그룹의 endPage가
		 * 정확하지 못할 경우가 발생하게 된다. 다시 말해 전체 페이지가 53페이지 라고
		 * 가정하면 위의 식에서 계산된 endPage는 60 페이지가 되지만 실제로 
		 * 60페이지는 존재하지 않는 페이지이므로 문제가 발생하게 된다.
		 * 그래서 맨 마지막 페이지에 대한 보정이 필요하여 아래와 같이 endPage와
		 * pageCount를 비교하여 현재 페이지 그룹에서 endPage가 pageCount 보다
		 * 크다면 pageCount를 endPage로 지정 하였다. 즉 현재 페이지 그룹이
		 * 마지막 페이지 그룹이면 endPage는 전체 페이지 수가 되도록 지정한 것이다.
		 **/
		if(endPage > pageCount) {
			endPage = pageCount;
		}
		
		/* 요청을 처리한 결과 데이터를 HttpServletRequest의 속성에 저장한다.
		 * 
		 * 요청을 처리한 결과 데이터를 모델이라고 부르며 이 데이터는 게시글 리스트에서
		 * 페이징 처리를 구현하기 위해 필요한 데이터이므로 뷰 페이지로 포워딩 하여
		 * 화면을 구현할 때 사용할 수 있도록 HttpServletRequest의 속성에 저장한다.	
		 **/
		request.setAttribute("bList", bList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageGroup", PAGE_GROUP);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("searchOption", searchOption);
		
		// 검색 요청이면 type과 keyword를 request 영역에 저장한다.
		if(searchOption) {
			request.setAttribute("keyword", keyword);
			request.setAttribute("type", type);	
		}
		
		/* view 페이지로 제어를 이동해 요청에 대한 결과를 출력하기 위해
		 * HttpServletRequest 객체로부터 RequestDispatcher 객체를 구하고
		 * /WEB-INF/board/boardList.jsp 페이지로 포워딩 한다. 
		 **/
		RequestDispatcher rd = 
				request.getRequestDispatcher("/WEB-INF/board/boardList.jsp");
		rd.forward(request, response);
	}
}

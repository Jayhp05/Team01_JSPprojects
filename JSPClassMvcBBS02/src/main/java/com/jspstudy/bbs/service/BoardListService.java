package com.jspstudy.bbs.service;

import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Board;

// 게시글 리스트 보기 요청을 처리하는 서비스 클래스
public class BoardListService  implements CommandProcess {
	
	// 한 페이지에 보여 줄 게시글의 수를 상수로 선언하고 있다.
	private static final int PAGE_SIZE = 10;
	
	/* 한 페이지에 보여 질 페이지 그룹의 수를 상수로 선언하고 있다.
	 * [이전] 1 2 3 4 5 6 7 8 9 10 [다음]	
	 **/
	private static final int PAGE_GROUP = 10;
	
	public String requestProcess(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException {
		
		// 요청 파라미터로 넘어 온 페이지 번호를 읽어온다.
		String pageNum = request.getParameter("pageNum");
		String type = request.getParameter("type");	
		String keyword = request.getParameter("keyword");	
		
		/* pageNum이 null 이면 처음 게시글 리스트를 요청하거나 게시글 쓰기에서 
		 * Redirect 되어 넘어온 요청으로 pageNum을 1페이지로 설정한다.
		 * 즉 최신 게시글의 첫 번째 페이지에 해당하는 게시글 리스트를 화면에 출력한다. 
		 **/
		if(pageNum == null) {
			pageNum = "1";
		}
		
		// 요청 파라미터의 pageNum을 int 형으로 변환하여 현재 페이지로 설정하고 있다.
		int currentPage = Integer.parseInt(pageNum);
		
		/* 요청한 페이지에 해당하는 게시글의 첫 번째 행의 값을 계산한다.
		 * 현재 페이지가 1일 경우 startRow는 1, 2페이지 일 경우 startRow는 6이 된다.	 
		 * 
		 * 테이블에서 현재 페이지에 해당하는 게시글을 검색할 때 ROWNUM을 사용했다.
		 * ROWNUM은 쿼리의 결과로 검색되는 행들의 순서 값을 가진 의사컬럼으로
		 * 1부터 시작한다. 예를 들어 3페이지에 해당하는 게시글 리스트를 가져 온다면
		 * 한 페이지에 보여줄 게시글의 수가 5개로 지정되어 있으므로 startRow는 11이 된다. 
		 * 즉 아래의 공식에 의해 startRow(11) = 3 * 5 - (5 - 1);
		 * 첫 번째 페이지 startRow = 1, 두 번째 페이지 startRow = 6이 된다.
		 **/ 
		int startRow = currentPage * PAGE_SIZE - (PAGE_SIZE - 1);
		//int startRow = (currentPage - 1) * PAGE_SIZE + 1;
		
		int endRow = startRow + PAGE_SIZE - 1;
				
		int listCount = 0;
		ArrayList<Board> boardList = null;
		
		// BoardDao 객체를 생성하고 전체 게시글의 수를 얻어온다.
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
			
			// 현재 페이지에 해당 하는 게시글 리스트를 DB로부터 읽어온다.
			boardList = dao.boardList(startRow, endRow);
			
		} else {
			// 검색어에 해당하는 게시글 수를 구한다.
			listCount = dao.getBoardCount(type, keyword);
			
			// 검색 결과에 대한 현재 페이지에 해당 하는 게시글 리스트를 DB로부터 읽어온다.
			boardList = dao.searchList(type, keyword, startRow, endRow);
		}
		System.out.println("listCount : " + listCount);
		
		/* 페이지 그룹 이동 처리를 위해 전체 페이지를 계산하고 있다.
		 * [이전] 11 12 13...   또는   ... 8 9 10 [다음] 처리
		 * 전체 페이지 = 전체 게시글 수 / 한 페이지에 표시할 게시글 수가 되는데 
		 * 이 계산식에서 나머지가 존재하면 전체 페이지 수 : 전체 페이지 + 1이 된다.
		 **/
		int pageCount = listCount / PAGE_SIZE 
							+ (listCount % PAGE_SIZE == 0 ? 0 : 1);
		
		/* 페이지 그룹 처리를 위해 페이지 그룹별 시작 페이지와 마지막 페이지를 계산
		 * 페이지 그룹의 시작 페이지 : 1, 11, 21, 31...
		 * 첫 번째 페이지 그룹에서 페이지 리스트는 1 ~ 10 되므로 currentPage가
		 * 1 ~ 10 사이에 있으면 startPage는 1이 되고 11 ~ 20 사이는 
		 * 11이 된다. 
		 *
		 * 정수형 연산의 특징을 이용해 startPage를 아래와 같이 구할 수 있다.
		 * 아래 계산식으로 계산된 결과를 보면 현재 그룹의 마지막 페이지일 경우
		 * startPage가 다음 그룹의 시작 페이지가 나오게 되므로 삼항 연산자를
		 * 사용해 현재 페이지가 속한 그룹의 startPage가 되도록 조정 하였다.
		 **/
		 int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1
			- (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
		
		// 아래와 같이 startPage를 구할 수도 있다.
		//int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP 
		//		+ (currentPage % PAGE_GROUP == 0 ? -PAGE_GROUP + 1 : 1);		
				
		// 현재 페이지 그룹의 마지막 페이지 : 10, 20, 30...
		int endPage = startPage + PAGE_GROUP - 1;
		
		/* 위의 식에서 endPage를 구하게 되면 endPage는 항상 PAGE_GROUP의
		 * 크기만큼 증가(10, 20, 30 ...) 되므로 맨 마지막 페이지 그룹의 endPage가
		 * 정확하지 못할 경우가 발생하게 된다. 다시 말해 전체 페이지가 53페이지라고
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
		
		/* View 페이지에서 필요한 데이터를 Request 영역의 속성에 저장한다.
		 * 현재 페이지, 전체 페이지 수, 페이지 그룹의 시작 페이지와 마지막 페이지
		 * 게시글의 수, 한 페이지에 보여 줄 게시글 리스트의 데이터를 속성에 저장
		 **/
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageGroup", PAGE_GROUP);
		request.setAttribute("listCount", listCount);
		request.setAttribute("boardList", boardList);
		request.setAttribute("searchOption", searchOption);
		
		// 검색 요청이면 type과 keyword를 request 영역에 저장한다.
		if(searchOption) {			
			request.setAttribute("type", type);			
			request.setAttribute("keyword", keyword);
		}

		/* 최종적으로 Redirect 정보와 View 페이지 정보를 문자열로 반환하면 된다.
		 * 
		 * 게시글 리스트 요청에 대한 결과(모델)를 request 영역의 속성에 저장하고
		 * 요청에 대한 결과(모델)를 출력할 View 페이지와 View 페이지를 호출하는 방식을
		 * 아래와 같이 문자열로 지정하면 된다. 현재 요청을 처리한 후에 Redirect 하려면
		 * 뷰 페이지를 지정하는 문자열 맨 앞에 "r:" 또는 "redirect:"를 접두어로 붙여서
		 * 반환하고 Redirect가 아니라 Forward 하려면 뷰 페이지의 경로만 지정하여 
		 * 문자열로 반환하면 Controller에서 판단하여 Redirect 또는 Forward로 연결된다.   
		 * 또한 Forward 할 때 뷰 페이지의 정보 중에서 앞부분과 뒷부분에서 중복되는 
		 * 정보를 줄이기 위해서 Controller에서 PREFIX와 SUFFIX를 지정해 사용하기
		 * 때문에 매번 중복되는 부분을 제외하고 뷰 페이지의 정보를 지정하면 된다.
		 **/
		return "board/boardList";		
	}
}

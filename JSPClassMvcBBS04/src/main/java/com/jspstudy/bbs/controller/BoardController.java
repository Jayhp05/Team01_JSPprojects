package com.jspstudy.bbs.controller;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.bbs.service.BoardDetailService;
import com.jspstudy.bbs.service.BoardListService;
import com.jspstudy.bbs.service.BoardWriteService;
import com.jspstudy.bbs.service.CommandProcess;
import com.jspstudy.bbs.service.DeleteService;
import com.jspstudy.bbs.service.FileDownloadService;
import com.jspstudy.bbs.service.JoinFormService;
import com.jspstudy.bbs.service.JoinResultService;
import com.jspstudy.bbs.service.LoginFormService;
import com.jspstudy.bbs.service.LoginService;
import com.jspstudy.bbs.service.LogoutService;
import com.jspstudy.bbs.service.MemberUpdateFormService;
import com.jspstudy.bbs.service.MemberUpdateResultService;
import com.jspstudy.bbs.service.OverlapIdCheckService;
import com.jspstudy.bbs.service.UpdateFormService;
import com.jspstudy.bbs.service.UpdateService;
import com.jspstudy.bbs.service.WriteFormService;

//게시판 및 회원관련 요청을 처리하는 프런트 컨트롤러
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* 뷰 페이지 정보 중에서 앞부분과 뒷부분에서 중복되는 데이터를
	 * 최소화하기 위해서 사용하는 접두어와 접미어를 상수로 설정
	 *
	 * 이번 프로젝트는 뷰 페이지를 구성할 때 중복되는 코드를 최소화하기 위해서
	 * 웹 템플릿을 사용하는 프로젝트로 여러 JSP 페이지를 모듈화 하여 분리해 놓고
	 * 실행될 때 웹 템플릿을 통해서 하나의 JSP 페이지로 동작하도록 구현되어 있다.
	 * 그러므로 포워딩을 해야 할 경우에는 웹 템플릿 역할을 하는 index.jsp에
	 * 동적으로 포함시킬 컨텐츠 페이지를 body라는 파라미터를 통해서 전달하여
	 * 웹 템플릿 페이지인 index.jsp에서 body라는 파라미터 값을 읽어서 동적으로
	 * 포함되는 컨텐츠 페이지를 include 하도록 상대 참조 방식으로 지정하면 된다.
	 **/
	private final String PREFIX = "/WEB-INF/index.jsp?body=";
	private final String SUFFIX = ".jsp";
	
	/* 서블릿 초기화 메서드
	 * 이 서블릿 클래스의 인스턴스가 생성되고 아래 초기화 메서드가 딱 한 번 호출 된다.
	 **/
	public void init() throws ServletException {
		
		/* 애플리케이션 초기화 파라미터로 읽어온 업로드 파일이 저장될 폴더의 
		 * 로컬 경로를 구하여 그 경로와 파일명으로 File 객체를 생성한다.
		 **/
		ServletContext sc = getServletContext();
		String uploadDir = sc.getInitParameter("uploadDir");
		String realPath = sc.getRealPath(uploadDir);
		File parentFile = new File(realPath);
		
		/* 파일 객체에 지정한 위치에 디렉토리가 존재하지 않거나 
		 * 파일 객체가 디렉토리가 아니라면 디렉토리를 생성한다.
		 **/
		if(! (parentFile.exists() && parentFile.isDirectory())) {
			parentFile.mkdir();
		}
		
		/* 업로드 폴더 정보를 ServletContext 객체의 속성에 저장한다.
		 * ServletContext 객체는 웹 애플리케이션 당 1개가 생성되며 웹 애플리케이션이
		 * 구동되는데 필요한 정보를 담고 있는 객체로 JSP 페이지에서는 application
		 * 내장객체로 접근할 수 있다. 아래와 같이 ServletContext 객체의 속성에
		 * 저장되면 이 웹 애플리케이션의 모든 컴포넌트에서 이 정보를 사용할 수 있다. 
		 **/		
		sc.setAttribute("uploadDir", uploadDir);
		sc.setAttribute("parentFile", parentFile);
		System.out.println("init - " + parentFile);
	}

	// get 방식의 요청을 처리하는 메소드
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		doProcess(request, response);
	}

	// post 방식의 요청을 처리하는 메소드
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doProcess(request, response);
	}

	/* doGet(), doPost()에서 호출하는 메소드
	 * 즉 get방식과 post방식 요청을 모두 처리하는 메소드 이다.
	 * 컨트롤러는 이 메소드 안에서 브라우저의 요청에 대한 처리를 요청 URL을 분석해
	 * 요청을 처리할 모델 클래스를 결정하고 해당 모델 클래스의 객체를 사용해(위임)
	 * 클라이언트의 요청을 처리한 후 그 결과를 뷰로 전달해 결과 화면을 만들게 된다.
	 * 뷰로 전달된 데이터는 html 형식의 문서에 출력하여 브라우저에게 응답한다.
	 **/
	public void doProcess(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {		
		
		
		/* 컨트롤러에 어떤 요청이 들어왔는지 파악하기 위해 Request 객체로부터
		 * 웹 어플리케이션의 컨텍스트 루트를 포함한 요청 페이지의 URI 정보와
		 * ContextPath를 얻어와 String 클래스의 substring()을 이용해 
		 * 웹 어플리케이션 루트부터 요청한 페이지의 정보를 추출 한다. 
		 **/
		/* 요청 정보를 담고 있는 Request 객체로부터 요청 URI를 구한다.
		 * /JSPStudyMvcBBS03/boardList.mvc
		 **/ 
		String requestURI = request.getRequestURI();
		
		/* 요청 정보를 담고 있는 Request 객체로부터 ContextPath를 구한다. 
		 * /JSPStudyMvcBBS03
		 **/
		String contextPath = request.getContextPath();
		System.out.println("uri : " + requestURI + ", ctxPath : " + contextPath);
		
		/* 요청 URI에서 ContextPath를 제외한 요청 명령을 추출 한다.
		 * /boardList.mvc
		 **/
		String command = requestURI.substring(contextPath.length());
		System.out.println("command : " + command);
	
		// 모든 모델 클래스가 상속받는 슈퍼 인터페이스
		CommandProcess service = null;

		/* 뷰 페이지 정보를 저장할 변수
		 **/
		String viewPage = null;
		
		/* 요청 URI에서 추출한 명령을 비교해 요청을 처리할 모델 클래스를
		 * 결정하여 객체를 생성하고 메서드를 호출해 요청을 처리한다.
		 **/
		if(command.equals("/boardList.mvc") 
				|| command.equals("/*.mvc")
				|| command.equals("/index.mvc")) {
			
			/* 게시글 리스트 보기가 요청된 경우의 처리
			 * 한 페이지에 출력 할 게시글 리스트를 DB로부터 읽어오는 
			 * BoardListService 클래스의 인스턴스를 생성한 후 Request와
			 * Response 객체를 매개변수로 requestProcess()를
			 * 호출하여 게시글 리스트 보기에 대한 요청을 처리 한다.
			 **/
			service = new BoardListService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/boardDetail.mvc")) {
			
			/* 게시글 내용보기가 요청된 경우의 처리
			 * 게시글 하나의 내용을 DB로부터 읽어오는 BoardDetailService
			 * 클래스의 인스턴스를 생성한 후 Request와 Response 객체를
			 * 매개변수로 requestProcess()를 호출하여 게시글 내용보기에
			 * 대한 요청을 처리 한다.
			 **/
			service = new BoardDetailService();
			viewPage = service.requestProcess(request, response);
			
		} else if (command.equals("/writeForm.mvc")) {

			/* 게시글 쓰기 폼을 요청한 경우의 처리
			 * 게시글 쓰기 폼 요청을 처리하는 WriteFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 게시글 쓰기 폼 요청을 처리한다.
			 **/
			service = new WriteFormService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/writeProcess.mvc")) {
			
			/* 게시글 쓰기 폼에서 등록하기 버튼이 클릭된 경우의 처리 
			 * 사용자가 작성한 게시글을 DB에 저장하는 BoardWriteService
			 * 클래스의 인스턴스를 생성한 후 Request와 Response 객체를
			 * 매개변수로 requestProcess()를 호출하여 새로운 게시글을
			 * DB에 저장 한다.
			 */
			service = new BoardWriteService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/updateForm.mvc")) {
			
			/* 게시글 내용보기에서 수정하기 버튼이 클릭된 경우의 처리 
			 * 게시글 수정 폼 요청을 처리하는  UpdateFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 게시글 수정 폼 요청을 처리한다.
			 */
			service = new UpdateFormService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/updateProcess.mvc")) {
			
			/* 게시글 수정 폼에서 수정하기 버튼이 클릭된 경우의 처리 
			 * 게시글 수정 요청을 처리하는 UpdateService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 게시글을 DB에서 수정한다.
			 */	
			service = new UpdateService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/deleteProcess.mvc")) {
			
			/* 게시글 내용보기에서 수정하기 버튼이 클릭된 경우의 처리 
			 * 게시글 수정 폼 요청을 처리하는  UpdateFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 게시글을 DB에서 삭제한다.
			 */
			service = new DeleteService();
			viewPage = service.requestProcess(request, response);
			
		}  else if(command.equals("/loginForm.mvc")) {

			/* 상단 메뉴에서 로그인 메뉴가 클릭된 경우의 처리
			 * 회원 로그인 폼 요청을 처리하는 LoginFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그인 요청을 처리한다.
			 **/
			service = new LoginFormService();
			viewPage = service.requestProcess(request, response); 
		
		} else if(command.equals("/login.mvc")) {

			/* 로그인 폼에서 로그인 버튼이 클릭된 경우의 처리
			 * 회원 로그인 요청을 처리하는 LoginService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그인 요청을 처리한다.
			 */
			service = new LoginService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/logout.mvc")) {
			
			/* 로그아웃이 클릭된 경우의 처리
			 * 회원 로그아웃 요청을 처리하는 LogoutService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그아웃 요청을 처리한다.
			 */
			service = new LogoutService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/joinForm.mvc")) {
			
			/* 회원 가입이 클릭된 경우의 처리
			 * 회원 가입 폼 요청을 처리하는 JoinFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그아웃 요청을 처리한다.
			 */
			service = new JoinFormService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/overlapIdCheck.mvc")) {
			
			/* 회원 가입 폼에서 회원 아이디 중복확인 버튼이 클릭된 경우의 처리
			 * 회원 아이디 중복확인 요청을 처리하는 OverlapIdCheckService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그아웃 요청을 처리한다.
			 */
			service = new OverlapIdCheckService();
			viewPage = service.requestProcess(request, response);
				
		} else if(command.equals("/joinResult.mvc")) {
			
			/* 회원 정보 입력 확인 화면에서 가입완료 버튼이 클릭된 경우의 처리
			 * 회원 가입 요청을 처리하는 JoinResultService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 가입 요청을 처리한다.
			 */
			service = new JoinResultService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/memberUpdateForm.mvc")) {
			
			/* 로그아웃이 클릭된 경우의 처리
			 * 회원 로그아웃 요청을 처리하는 MemberUpdateFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그아웃 요청을 처리한다.
			 */
			service = new MemberUpdateFormService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/memberUpdateResult.mvc")) {
			
			/* 로그아웃이 클릭된 경우의 처리
			 * 회원 로그아웃 요청을 처리하는 MemberUpdateResultService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그아웃 요청을 처리한다.
			 */
			service = new MemberUpdateResultService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/fileDownload.mvc")) {
			
			/* 게시글 상세보기에서 파일 다운로드가 클릭된 경우의 처리
			 * 파일 다운로드 요청을 처리하는 FileDownloadService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 파일 다운로드 요청을 처리한다.
			 */
			service = new FileDownloadService();
			viewPage = service.requestProcess(request, response);
			
		}				
		
		/* Redirect 정보와 View 페이지의 경로 정보를 저장하는 viewPage가
		 * null이 아니면 Redirect 여부를 판단하여 Redirect라면 Response 객체의
		 * sendRedirect()를 이용해 Redirect 시키고 Redirect가 아니라면
		 * RequestDispatcher를 이용해 View 페이지로 포워딩 시킨다.
		 **/
		if(viewPage != null) {
			
			/* 모델 클래스가 반환한 viewPage에 "redirect" 또는 "r" 접두어가 존재하면
			 * 아래의 viewPage.split(":")[0] 코드에서 "redirect" 또는 "r" 문자열이 반환되어
			 * 아래 if 문에서 Redirect 되고 "forward" 또는 "f" 접두어가 존재하면
			 * 아래 if 문에서 웹 템플릿을 적용하지 않은 뷰 페이지로 Forward 되고
			 * 그렇지 않으면 웹 템플릿을 적용한 뷰 페이지로 Forward 된다.
			 **/  
			String view = viewPage.split(":")[0];
			System.out.println("view : " + view);
			
			if(view.equals("r") || view.equals("redirect")) {
				
				 // redirect인 경우 지정한 URL로 Redirect 시킨다.
				response.sendRedirect(viewPage.split(":")[1]);
				
			} else if(view.equals("f") || view.equals("forward")) {
				
				/* 웹 템플릿을 적용하지 않는 Forward인 경우
				 * PREFIX와 SUFFIX를 적용하지 않고 지정한 view로 Forward 한다.
				 **/
				RequestDispatcher rd = 
						request.getRequestDispatcher(viewPage.split(":")[1]);	
				rd.forward(request, response);
				
			} else { 
				
				/* 웹 템플릿을 적용하는 경우 PREFIX와 SUFFIX를 적용해 Forward 한다.
				 * PREFIX는 view 정보 중 앞에서 중복되는 부분을 없애기 위해 사용
				 * SUFFIX는 view 정보 중 뒤에서 중복되는 부분을 없애기 위해 사용
				 **/
				RequestDispatcher rd = 
						request.getRequestDispatcher(PREFIX + view + SUFFIX);	
				rd.forward(request, response);
			}
		}
	}
}

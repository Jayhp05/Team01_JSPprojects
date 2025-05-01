package com.jspstudy.bbs.controller;

import java.io.File;
import java.io.IOException;

import com.jspstudy.bbs.service.BoardDetailService;
import com.jspstudy.bbs.service.BoardListService;
import com.jspstudy.bbs.service.BoardWriteService;
import com.jspstudy.bbs.service.CommandProcess;
import com.jspstudy.bbs.service.DeleteService;
import com.jspstudy.bbs.service.LoginFormService;
import com.jspstudy.bbs.service.LoginService;
import com.jspstudy.bbs.service.LogoutService;
import com.jspstudy.bbs.service.UpdateFormService;
import com.jspstudy.bbs.service.UpdateService;
import com.jspstudy.bbs.service.WriteFormService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* 게시판 및 로그인/로그아웃 요청을 if문을 사용해 처리하는 Controller */
@MultipartConfig(fileSizeThreshold = 1024 * 10, // 10KB 
				maxFileSize = 1024 * 1024 * 10, // 10MB
				maxRequestSize = 1024 * 1024 * 10 * 10) // 100MB
@WebServlet(name="boardController", urlPatterns="*.mvc")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String PREFIX = "/WEB-INF/index.jsp?body=";
	private final String SUFFIX = ".jsp";	
	
	public void init() throws ServletException {
		
		ServletContext sc = getServletContext();
		String uploadDir = sc.getInitParameter("uploadDir");
		String realPath = sc.getRealPath(uploadDir);
		File parentFile = new File(realPath);
		
		if(! (parentFile.exists() && parentFile.isDirectory())) {
			parentFile.mkdir();
		}
				
		sc.setAttribute("uploadDir", uploadDir);
		sc.setAttribute("parentFile", parentFile);
		System.out.println("init - " + parentFile);
	}

	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doProcess(request, response);
	}

	public void doProcess(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
 
		String requestURI = request.getRequestURI();
		
		String contextPath = request.getContextPath();
		System.out.println("uri : " + requestURI + ", ctxPath : " + contextPath);

		String command = requestURI.substring(contextPath.length());
		System.out.println("command : " + command);
		
		// 모든 모델 클래스가 상속받는 슈퍼 인터페이스
		CommandProcess service = null;
		
		/* 뷰 페이지 정보를 저장할 변수
		 **/
		String viewPage = null;
		
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
			 **/
			service = new BoardWriteService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/updateForm.mvc")) {
			
			/* 게시글 내용보기에서 수정하기 버튼이 클릭된 경우의 처리 
			 * 게시글 수정 폼 요청을 처리하는  UpdateFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 게시글 수정 폼 요청을 처리한다.
			 **/
			service = new UpdateFormService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/updateProcess.mvc")) {
			
			/* 게시글 수정 폼에서 수정하기 버튼이 클릭된 경우의 처리 
			 * 게시글 수정 요청을 처리하는 UpdateService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 게시글을 DB에서 수정한다.
			 **/	
			service = new UpdateService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/deleteProcess.mvc")) {
			
			/* 게시글 내용보기에서 수정하기 버튼이 클릭된 경우의 처리 
			 * 게시글 수정 폼 요청을 처리하는  UpdateFormService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 게시글 수정 폼 요청을 처리한다.
			 **/
			service = new DeleteService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/loginForm.mvc")) {

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
			 **/
			service = new LoginService();
			viewPage = service.requestProcess(request, response);
			
		} else if(command.equals("/logout.mvc")) {
			
			/* 로그아웃이 클릭된 경우의 처리
			 * 회원 로그아웃 요청을 처리하는 LogoutService 클래스의
			 * 인스턴스를 생성한 후 Request와 Response 객체를 매개변수로
			 * requestProcess()를 호출하여 회원 로그아웃 요청을 처리한다.
			 **/
			service = new LogoutService();
			viewPage = service.requestProcess(request, response);
			
		}

		if(viewPage != null) {

			String view = viewPage.split(":")[0];
			System.out.println("view : " + view);
			
			if(view.equals("r") || view.equals("redirect")) {
				response.sendRedirect(viewPage.split(":")[1]);
				
			} else {

				RequestDispatcher rd = 
						request.getRequestDispatcher(PREFIX + view + SUFFIX);	
				rd.forward(request, response);
			}
		}	
	}
}

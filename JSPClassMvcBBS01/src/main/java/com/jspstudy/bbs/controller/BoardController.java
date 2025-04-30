package com.jspstudy.bbs.controller;

import java.io.File;
import java.io.IOException;

import com.jspstudy.bbs.service.BoardDetailService;
import com.jspstudy.bbs.service.BoardListService;
import com.jspstudy.bbs.service.BoardWriteFormService;
import com.jspstudy.bbs.service.BoardWriteService;
import com.jspstudy.bbs.service.DeleteService;
import com.jspstudy.bbs.service.UpdateFormService;
import com.jspstudy.bbs.service.UpdateService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig(fileSizeThreshold = 1024 * 10, // 10KB 
				maxFileSize = 1024 * 1024 * 10, // 10MB
				maxRequestSize = 1024 * 1024 * 10 * 10) // 100MB
@WebServlet(name = "boardController", urlPatterns = "*.mvc", initParams = @WebInitParam(name = "uploadDir", value = "upload"))
public class BoardController extends HttpServlet {
	
	private final String PREFIX = "/WEB-INF/";
	private final String SUFFIX = ".jsp";
	
	@Override
	public void init() throws ServletException {
		
			// web.xml에 지정한 웹 어플리케이션 초기화 파라미터를 읽는다.
		String uploadDir = getServletContext().getInitParameter("uploadDir");
			
			/* 웹 어플리케이션 초기화 파라미터에서 읽어온 파일이 저장될 폴더의 
			 * 로컬 경로를 구하여 그 경로와 파일명으로 File 객체를 생성한다.
			 **/
		String realPath = getServletContext().getRealPath(uploadDir);		
		
		File parentFile = new File(realPath);
			
		if(! (parentFile.exists() && parentFile.isDirectory())) {
			parentFile.mkdir();
		}
		
		getServletContext().setAttribute("uploadDir", uploadDir);
		getServletContext().setAttribute("parentFile", parentFile);
		
		System.out.println("init - " + parentFile);		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		doProcess(req, resp);
	}	
	
//	GET, POST 방식의 모든 요청 처리를 이 메서드가 함
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		명령을 구분 : http://localhost:8080/JSPClassMvcBBS01/boardList.mvc
		String requestURI = req.getRequestURI();
		String viewPage = null;
		
//		/JSPClassMvcBBS01
		String contextPath = req.getContextPath();
		System.out.println("URI : " + requestURI + ", contextPath : " + contextPath);
		
		String command = requestURI.substring(contextPath.length());
		System.out.println("command : " + command);
		
//		명령을 처리 - Service 클래스 이용과 동시에 Service 클래스는 dao 이용
//		어떤 서비스 클래스가 실행될지 결정
		if(command.equals(".mvc") || command.equals("boardList.mvc")) {
//			게시글 리스트 요청을 처리하는 서비스 클래스 실행
			BoardListService service = new BoardListService();
			viewPage = service.requestProcess(req, resp);
		}
		else if(command.equals("boardDetail.mvc")) {
//			게시글 상세보기 요청을 처리하는 서비스 클래스 실행
			BoardDetailService service = new BoardDetailService();
			viewPage = service.requestProcess(req, resp);
		}
		else if(command.equals("/writeForm.mvc")) {
			BoardWriteFormService service = new BoardWriteFormService();
			viewPage = service.requestProcess(req, resp);
		}
		else if(command.equals("/writeProcess.mvc")) {
			BoardWriteService service = new BoardWriteService();
			viewPage = service.requestProcess(req, resp);
		}
		else if(command.equals("/updateForm.mvc")) {
			UpdateFormService service = new UpdateFormService();
			viewPage = service.requestProcess(req, resp);
		}
		else if(command.equals("/updateProcess.mvc")) {
			UpdateService service = new UpdateService();
			viewPage = service.requestProcess(req, resp);
		}
		else if(command.equals("/deleteProcess.mvc")) {
			DeleteService service = new DeleteService();
			viewPage = service.requestProcess(req, resp);
		}
		
//		여기까지 코드가 흘러 왔다는 것은 해당 요청을 처리한 후
//		포워드 or 리다이렉트를 할 지 결정해서 응답을 생성해야 함.
//		클래스 isRedirect, page =? "boardList.jsp", "boardDetail.jsp"
//		"r:boardList.mvc", "redirect:boardList.mvc",
		
		if(viewPage != null) {
//			"boardList", "r:boardList.mvc", "redirect:boardList.mvc",
			String view = viewPage.split(":")[0];
			System.out.println("view : " + view);
			
			if(view.equals("r") || view.equals("redirect")) {
				
				resp.sendRedirect(viewPage.split(":")[1]);
			}
			else {
				RequestDispatcher rd = req.getRequestDispatcher(PREFIX + view + SUFFIX);
				rd.forward(req, resp);
			}
		}		
		
	}
	
}

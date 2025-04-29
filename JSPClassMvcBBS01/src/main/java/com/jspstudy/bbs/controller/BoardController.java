package com.jspstudy.bbs.controller;

import java.io.IOException;

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
	
	@Override
	public void init() throws ServletException {

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
	
//	모든 요청 처리를 이 메서드가 함
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
}

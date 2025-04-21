package com.jspstudy.ch05;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/test01")
public class Test01 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		session, application
		ServletContext application = req.getServletContext();
		HttpSession session = req.getSession();
		System.out.println(application.getServerInfo());
		
		System.out.println(application.getAttribute("test01"));
	}

}

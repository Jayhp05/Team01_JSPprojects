package com.jspstudy.ch04.exam.formdata;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/formData02")
public class FormData02 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String call = request.getParameter("call");
		String cell1 = request.getParameter("call1");
		String cell2 = request.getParameter("call2");
		String[] hopeJobs = request.getParameterValues("hopeJob");
		String[] stars = request.getParameterValues("star[]");

		String fullPhone = call + "-" + cell1 + "-" + cell2;
		
		StringBuilder jobList = new StringBuilder();
		if (hopeJobs != null) {
			for (String job : hopeJobs) {
				jobList.append(job).append(" ");
			}
		}

		request.setAttribute("name", name);
		request.setAttribute("gender", gender);
		request.setAttribute("phone", fullPhone);
		request.setAttribute("hopeJobs", jobList.toString().trim());
		request.setAttribute("stars", stars);

		RequestDispatcher rd = request.getRequestDispatcher("/view/formDataView02.jsp");
		rd.forward(request, response);
	}
}

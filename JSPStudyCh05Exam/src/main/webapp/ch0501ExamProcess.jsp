<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    String color = request.getParameter("color");
    String food = request.getParameter("food");
    String[] animals = request.getParameterValues("animal");
    String[] hobbies = request.getParameterValues("hobby");

    request.setAttribute("name", name);
    request.setAttribute("color", color);
    request.setAttribute("food", food);
    request.setAttribute("animals", animals);
    request.setAttribute("hobbies", hobbies);

    RequestDispatcher rd = request.getRequestDispatcher("ch0501ExamResult.jsp");
    rd.forward(request, response);
%>

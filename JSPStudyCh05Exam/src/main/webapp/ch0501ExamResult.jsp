<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String name = (String) request.getAttribute("name");
    String color = (String) request.getAttribute("color");
    String food = (String) request.getAttribute("food");
    String[] animals = (String[]) request.getAttribute("animals");
    String[] hobbies = (String[]) request.getAttribute("hobbies");

    StringBuilder animalStr = new StringBuilder();
    if (animals != null) {
        for (int i = 0; i < animals.length; i++) {
            animalStr.append(animals[i]);
            if (i < animals.length - 1) animalStr.append(", ");
        }
    }

    StringBuilder hobbyStr = new StringBuilder();
    if (hobbies != null) {
        for (int i = 0; i < hobbies.length; i++) {
            hobbyStr.append(hobbies[i]);
            if (i < hobbies.length - 1) hobbyStr.append(", ");
        }
    }
%>

<h2>선호도 테스트 결과</h2>
<b><%= name %></b>님의 선호도 테스트 결과<br/><br/>

<%= name %>님은 <%= color %>을 좋아하고, <%= food %>를 좋아하며,<br/>
좋아하는 동물은 <%= animalStr.toString() %>이고,<br/>
<%= hobbyStr.toString() %>의 취미를 가지고 계십니다.

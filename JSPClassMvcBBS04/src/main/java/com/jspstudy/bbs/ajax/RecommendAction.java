package com.jspstudy.bbs.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jspstudy.bbs.dao.BoardDao;

// 추천과 땡큐 요청을 처리하는 모델 클래스
public class RecommendAction implements AjaxProcess {
	
	@Override
	public void ajaxProcess(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		String recommend = request.getParameter("recommend");
		String no = request.getParameter("no");
				
		HashMap<String, Integer> map = null;
		BoardDao dao = new BoardDao();
		map = dao.getRecommend(Integer.parseInt(no), recommend);
		
		// Gson 생성자를 이용해 Gson 객체를 생성한다.
		Gson gson = new Gson();
		
		/* dao에서 받은 Map 객체를 json 형식으로 직렬화 한다.
		 * dao에서 Map에 데이터를 저장할 때 아래와 같이 저장 하였다.
		 * 
		 * map.put("recommend", rs.getInt(1));
		 *	map.put("thank", rs.getInt(2));
		 *
		 * 이 데이터를 json 형식으로 직렬화 하면 아래와 같다.
		 * 
		 * {recommend: 10, thank: 5}
		 **/
		String result = gson.toJson(map);
							
		System.out.println("RecommendAction - result : " + result);
		
		/* 응답 데이터를 스트림을 통해 클라이언트에게 전송하기 위해
		 * 응답 객체(response)로 스트림을 연결한다.
		 * 
		 * AjaxController는 Ajax 요청만 받는 컨트롤러로 이 컨트롤러에서
		 * 현재 클래스의 ajaxProcess() 메서드를 호출하면 요청을 처리하고
		 * 컨트롤러로 돌아갈 때 응답 객체에 연결된 스트림을 통해 result에
		 * 저장된 데이터가 웹 브라우저의 XMLHttpRequest 객체로 전달된다.
		 **/
		response.setContentType("text/html; charset=utf-8");		
		PrintWriter out = response.getWriter();
		out.println(result);
	}
}

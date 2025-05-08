package com.jspstudy.bbs.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jspstudy.bbs.dao.BoardDao;
import com.jspstudy.bbs.vo.Reply;

// 댓글 쓰기 요청을 처리할 모델 클래스
public class ReplyWriteAction implements AjaxProcess {

	@Override
	public void ajaxProcess(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {		
		
		// 댓글 쓰기 요청에서 클라이언트가 보내온 데이터를 request 객체롤 부터 받는다.  
		String bbsNo = request.getParameter("bbsNo");
		String replyContent = request.getParameter("replyContent");
		String replyWriter = request.getParameter("replyWriter");		
		 
		Reply reply = new Reply(
				Integer.parseInt(bbsNo), replyContent, replyWriter);
		
		// BoardDao 객체를 구해 댓글을 DB에 추가한다.
		BoardDao dao = new BoardDao();
		dao.addReply(reply);
		
		/* 새롭게 추가된 댓글을 포함해 새로운 댓글 리스트를 화면에 출력하기 위해
		 * BoardDao로부터 댓글 리스트를 ArrayList<Reply>로 받는다.
		 **/
		ArrayList<Reply> replyList = dao.getReplyList(Integer.parseInt(bbsNo));
		
		// Gson 생성자를 이용해 Gson 객체를 생성한다.
		Gson gson = new Gson();
		
		/* dao에서 받은 ArrayList 객체를 json 형식으로 직렬화 한다.
		 * dao에서 ArrayList에 Reply를 저장해 반환했기 때문에 이 데이터를
		 * json 형식으로 직렬화 하면 자바스크립트의 객체 배열 형태로 저장된다.
		 * 
		 * [{no: 1, bbsNo: 123, reply: '안녕하세요', writer: 'admin' 
		 * 			regDate: '2016-01-12:12:53:11'},
		 * {no: 2, bbsNo: 71, reply: '고맙습니다.', writer: 'midas' 
		 * 			regDate: '2016-02-01:19:27:51'}, ... ]
		 **/
		String result = gson.toJson(replyList);							
		System.out.println("ReplyWriteAction - result : " + result);
		
		/* 응답 데이터를 스트림을 통해 클라이언트에게 전송하기 위해
		 * 응답 객체(response)로 스트림을 연결한다.
		 * 
		 * AjaxController는 Ajax 요청만 받는 컨트롤러로 이 컨트롤러에서
		 * 현재 클래스의 ajaxProcess() 메서드를 호출하면 요청을 처리하고
		 * 컨트롤러로 돌아갈 때 응답 객체에 연결된 스트림을 통해 result에
		 * 저장된 데이터가 웹 브라우저의 XMLHttpRequest 객체로 전달된다.
		 **/
		//response.setContentType("text/html; charset=utf-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}
}

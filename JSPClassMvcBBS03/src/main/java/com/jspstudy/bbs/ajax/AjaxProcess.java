package com.jspstudy.bbs.ajax;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// ajax 요청을 처리하는 모든 모델 클래스들이 상속 받는 슈퍼 인터페이스
public interface AjaxProcess {
	
	public void ajaxProcess(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException;
}

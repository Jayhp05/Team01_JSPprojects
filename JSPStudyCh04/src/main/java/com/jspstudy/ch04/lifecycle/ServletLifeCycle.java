package com.jspstudy.ch04.lifecycle;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 서블릿의 라이프 사이클 - init() 메서드와 destroy() 메서드
/* Servlet 3.0 부터 애노테이션을 사용해 아래와 같이 서블릿을 등록할 수 있고
 * 서블릿 초기화 파라미터와 서블릿 매핑을 지정할 수 있다.
 * urlPatterns에 처리할 패턴이 여러 개일 경우 배열 {}로 지정할 수 있다.
 * 서블릿 초기화 파라미터가 여러 개일 경우에도 initParams에 배열 {}을 지정해
 * @WebInitParam()을 여러 개 지정할 수 있다.
 * 
 * web.xml에 servlet-mapping이 설정되어 있다면 애노테이션과 web.xml에
 * 지정한 서블릿 이름이 동일하기 때문에 web.xml에 지정한 url-pattern이 우선한다.
 * 현재 서블릿 클래스의 애노테이션으로 지정한 urlPatters는 적용되지 않는다. 
 * 하지만 서블릿 초기화 파라미터는 파라미터 이름이 서로 다르기 때문에 사용할 수 있다.
 **/
@WebServlet(name="servletLifeCycle", 
	//urlPatterns={"*.life"}, 
	initParams=@WebInitParam(
			name="ANNOTATION_PARAM", value="애노테이션 파라미터"))
public class ServletLifeCycle extends HttpServlet {

	/* 서블릿 컨테이너에서 서블릿 객체가 생성되고 초기화된 다음에 init() 메소드가 호출 된다.
	 * 이 메소드는 서블릿 객체가 초기화될 때 톰캣에 의해서 딱 한 번 호출되는 메소드 이다.
	 * 서블릿 객체가 만들어지고 최조 한 번 실행해야할 작업이 있다면 이 메서드를 이용한다. 
	 **/
	public void init() throws ServletException {
		System.out.println("init() 메소드 호출됨");
		
		/* web.xml에 서블릿을 등록할 때 servlet 태그 하위에 init-param 태그
		 * 안에 param-name에 지정한 "READ_FILE"이라는 파라미터 이름을 찾아서
		 * param-value 태그에 지정한 파라미터 값을 읽어온다.
		 **/
		String readFile = getInitParameter("READ_FILE");
				
		String testParam = getInitParameter("TEST_PARAM");
		String annotationParam = getInitParameter("ANNOTATION_PARAM");
		
		System.out.println("readFile : " + readFile);
		System.out.println("testParam : " + testParam);
		System.out.println("annotationParam : " + annotationParam);
		System.out.println("servletName : " + getServletName());
	}	
	
	// GET 방식 요청이 들어올 때 마다 실행되는 메서드
	@Override
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {		
		System.out.println("*** doGet() 메서드 실행됨 ***");
	}
	
	// POST 방식 요청이 들어올 때 마다 실행되는 메서드
	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {		
		System.out.println("### doPost() 메서드 실행됨 ###");
	}	
	
	/* 서블릿 객체가 소멸되기 직전에 톰캣은 딱 한 번 destroy() 메서드를 호출한다.
	 * 서블릿이 소멸될 때 마지막으로 해야 할 작업이 있다면 이 메서드를 이용한다.
	 * 주로 열려있는 스트림을 닫거나, 자원을 해제하는 코드 등을 기술한다.
	 * 
	 * 톰캣이 종료되거나 기존 클래스가 수정되면 톰캣은 자신이 관리하고 있는 서블릿을
	 * 소멸시키며 서블릿에 구현되어 있는 destroy() 메서드를 마지막으로 호출한다. 
	 **/
	public void destroy() {
		System.out.println("destory() 메소드 호출됨");		
	}
}

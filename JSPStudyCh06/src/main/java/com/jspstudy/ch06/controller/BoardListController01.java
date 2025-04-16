package com.jspstudy.ch06.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.jspstudy.ch06.vo.Board;

@WebServlet("/boardList01")
public class BoardListController01 extends HttpServlet {

	// get 방식의 요청을 처리하는 메소드
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		// 오라클 사용자 정보와 접속 드라이버 이름 및 접속 URL 등을 변수에 저장
		String user = "hr";
		String pass = "hr";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url ="jdbc:oracle:thin:@localhost:1521:xe";
		
		// 데이터베이스 작업에 필요한 객체 타입으로 변수를 선언
		// Connection 객체는 DB에 연결해 작업을 수행할 수 있도록 지원하는 객체
		Connection conn = null;
		
		// Statement, PreparedStatement 객체는 DB에 쿼리를 발행하는 객체
		PreparedStatement pstmt = null;
		
		// ResultSet 객체는 DB에 SELECT 쿼리를 발행한 결과를 저장하는 객체
		ResultSet rs = null;		
		
		// 여러 개의 게시 글을 ArrayList에 저장하기 위한 객체
		ArrayList<Board> boardList = null;

		try {
			/* 1. 접속하려는 DBMS의 드라이버를 로딩 한다.
			 * Oracle 드라이버를 다운로드 하여 WEB-INF/lib 폴더에 추가해야 한다.
			 **/
			Class.forName(driver);
				
			// 2. 데이터베이스 연결 - DB 커넥션을 생성한다.
			conn = DriverManager.getConnection(url, user, pass);	
			
			/* 3. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 **/
			pstmt = conn.prepareStatement("SELECT * FROM jspbbs ORDER BY no DESC");
			
			// 4. 쿼리를 실행하여 SELCET한 결과를 ResultSet 객체로 받는다.
			rs = pstmt.executeQuery();
			
			// 게시 글 리스트를 저장할 ArrayList 객체 생성 
			boardList = new ArrayList<Board>();
			
			/* 5. 쿼리 실행 결과를 바탕으로 while문을 이용해 데이터를 출력한다.
			 *	ResultSet의 next() 메서드는 다음 행의 데이터가 존재하면 true를 반환하므로
			 * while 문을 이용해 첫 번째 행부터 마지막 행까지 이동하면서 데이터를 추출할 수 있다.
			 *
			 * 반복문 안에서 ResultSet 객체의 getXXX() 메서드를 이용해 데이터를 
			 *	읽어올 수 있다. ResultSet 객체는 자바의 모든 타입에 대응되는 getXXX()
			 *	메서드를 제공하고 있으며 아래와 같이 컬럼의 위치(첫 번째, 두 번째...) 값을 
			 *	정수로 지정하거나 컬럼 이름을 지정해 데이터를 읽어 올 수 있다.
			 **/	 
			while(rs.next()) {
				Board b = new Board();
				b.setNo(rs.getInt(1));
				b.setTitle(rs.getString("title"));
				b.setWriter(rs.getString(3));
				b.setRegDate(rs.getTimestamp(5));
				b.setReadCount(rs.getInt("read_count"));
				
				boardList.add(b);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {			
			try {
				/* 6. 사용한 ResultSet과 PreparedStatement 객체를 종료 한다.
				 * 객체를 생성한 역순으로 닫으면 된다.
				 **/	
				if(rs != null) rs.close();
				if(pstmt != null) rs.close();
				
				// 7. DBMS 연결을 종료한다.
				if(conn != null) rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 요청을 처리한 결과 데이터를 HttpServletRequest의 속성에 저장한다.
		request.setAttribute("bList", boardList);
		
		/* view 페이지로 제어를 이동해 요청에 대한 결과를 출력하기 위해
		 * HttpServletRequest 객체로부터 RequestDispatcher 객체를 구하고
		 * /WEB-INF/board/boardList01.jsp 페이지로 포워딩 한다. 
		 **/
		RequestDispatcher rd = 
				request.getRequestDispatcher("/WEB-INF/board/boardList01.jsp");
		rd.forward(request, response);
	}
}

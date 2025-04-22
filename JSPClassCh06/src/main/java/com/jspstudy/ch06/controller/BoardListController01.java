package com.jspstudy.ch06.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jspstudy.ch06.dao.BoardDao01;
import com.jspstudy.ch06.vo.Board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/boardList01")
public class BoardListController01 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String user = "hr";
		String pass = "hr";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> bList = null;
		
		try {
		
			/* 1. 접속드라이버 로딩 */
			Class.forName(driver);
			
			/* 2. DB에 연결 */
			conn = DriverManager.getConnection(url, user, pass);
			
			/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
			/* PreparedStatement */
			pstmt = conn.prepareStatement("SELECT * FROM jspbbs ORDER BY no DESC");
			
			/* 4. 쿼리를 발행하여 SELECT한 결과를 ResultSet 객체로 받는다. */
			rs = pstmt.executeQuery();
			
			/* 여러 개의 Board 객체를 담을 ArrayList를 사용 */
			bList = new ArrayList<>(); // 타입을 추론하여 뒤에 있는 배열의 타입은 따로 작성안해도 됨.
			
			/* 5. 퀴리를 실행한 결과를 while문 반복하면서 Board 객체에 담고 list에 담는다. */
			while(rs.next()) {
				Board b = new Board();
				 
				b.setNo(rs.getInt("no")); //  이름으로 가져옴 (이름이나 위치값으로 가져올 수 있음.)
				b.setTitle(rs.getString(2)); // 위치값으로 가져오는 방법
				b.setWriter(rs.getString("writer"));
				b.setRegDate(rs.getTimestamp("reg_date"));
				b.setReadCount(rs.getInt("read_count"));
				 
				bList.add(b);
			}
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			/* 6. DB 작업에 사용한 객체는 처음 가져온 역순으로 닫는다. */
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} 
			catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		// DB에 가서 게시글 리스트(모델)를 읽어왔음
		// 뷰에 가서 모델을 출력
		req.setAttribute("bList", bList);
		
		// 뷰로 제어를 이동함.
		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/board/boardList01.jsp");
		rd.forward(req, resp);
		
	} // end doGet()
	
} // end 

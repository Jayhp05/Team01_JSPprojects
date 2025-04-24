package com.jspstudy.ch06.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.*;
import javax.sql.DataSource;

import com.jspstudy.ch06.vo.Board;

public class BoardDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private static DataSource ds;
	
	public BoardDao() {
//		1. DBCP 자원을 찾는다.
		try {
			Context initContext = new InitialContext();
			
			Context envContext = (Context) initContext.lookup("java:/comp/env"); // 다운캐스팅 적용
			ds = (DataSource) envContext.lookup("jdbc/bbsDBPool");
			
		} catch (NamingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
//	하나 하나의 기능은 메서드
//	게시글 리스트를 DB 테이블에서 읽어와서 반환하는 메서드
	public ArrayList<Board> boardList() {
		
		String sqlBoardList = "SELECT * FROM jspbbs ORDER BY no DESC";
		ArrayList<Board> bList = null;
		
		try {
			
			/* 2. DB에 연결 */
			conn = ds.getConnection();
			
			/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
			/* PreparedStatement */
			pstmt = conn.prepareStatement(sqlBoardList);
			
			/* 4. 쿼리를 발행하여 SELECT한 결과를 ResultSet 객체로 받는다. */
			rs = pstmt.executeQuery();
			
			/* 여러 개의 Board 객체를 담을 ArrayList를 사용 */
			bList = new ArrayList<>(); // 타입을 추론하여 뒤에 있는 배열의 타입은 따로 작성안해도 됨.
			
			/* 5. 퀴리를 실행한 결과를 while문 반복하면서 Board 객체에 담고 list에 담는다. */
			while(rs.next()) {
				Board b = new Board();
				 
				b.setNo(rs.getInt("no")); //  이름으로 가져옴 (이름이나 위치값으로 가져올 수 있음.)
				b.setTitle(rs.getString("title")); // 위치값으로 가져오는 방법
				b.setWriter(rs.getString("writer"));
				b.setContent(rs.getString("content"));
				b.setRegDate(rs.getTimestamp("reg_date"));
				b.setReadCount(rs.getInt("read_count"));
				b.setPass(rs.getString("pass"));
				b.setFile1(rs.getString("file1"));
				 
				bList.add(b);
			}
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
		
		return bList;
	}
	
//	게시글 상세보기
//	게시글 번호에 해당하는 하나의 게시글을 DB 테이블에서 읽어와 반환하는 메서드
	public Board getBoard(int no) {
		
		String sqlBoardList = "SELECT * FROM jspbbs WHERE no=?";
		Board board = null;
		
		try {
			
			/* 2. DB에 연결 */
			conn = ds.getConnection();
			
			/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
			/* PreparedStatement */
			pstmt = conn.prepareStatement(sqlBoardList);
			pstmt.setInt(1, no);
			
			/* 4. 쿼리를 발행하여 SELECT한 결과를 ResultSet 객체로 받는다. */
			rs = pstmt.executeQuery();
			
			/* 5. 퀴리를 실행한 결과를 while문 반복하면서 Board 객체에 담고 list에 담는다. */
			if(rs.next()) {
				board = new Board();
				 
				board.setNo(rs.getInt("no")); //  이름으로 가져옴 (이름이나 위치값으로 가져올 수 있음.)
				board.setTitle(rs.getString("title")); // 위치값으로 가져오는 방법
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getTimestamp("reg_date"));
				board.setReadCount(rs.getInt("read_count"));
				board.setPass(rs.getString("pass"));
				board.setFile1(rs.getString("file1"));
				 
			}
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
		
		return board;
	}
	
//	게시글 하나를 받아서 DB에 저장하는 메서드
	public void insertBoard(Board board) {
		
		String sqlInsert = "INSERT INTO jspbbs(no, title, writer, content,"
				 + " reg_date, read_count, pass, file1) "
				 + " VALUES(jspbbs_seq.NEXTVAL, ?, ?, ?, SYSDATE, 0, ?, ?)";
		
		try {
			
			/* 2. DB에 연결 */
			conn = ds.getConnection();
			
			/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
			/* PreparedStatement */
			pstmt = conn.prepareStatement(sqlInsert);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getPass());
			pstmt.setString(5, board.getFile1());
			
			 pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			/* 6. DB 작업에 사용한 객체는 처음 가져온 역순으로 닫는다. */
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} 
			catch (SQLException se) {
				// TODO: handle exception
			}
			
		}
		
	}
	
//	게시글 수정, 삭제 시 게시글 비밀번호가 맞는지 체크하는 메서드
	public boolean isPassCheck(int no, String pass) {
		
		boolean isPass = false;
		String sqlPass = "SELECT pass FROM jspbbs WHERE no=?";
		
		try {
			
			/* 2. DB에 연결 */
			conn = ds.getConnection();
			
			/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
			/* PreparedStatement */
			pstmt = conn.prepareStatement(sqlPass);
			pstmt.setInt(1, no);
			
			/* 4. 쿼리를 발행하여 SELECT한 결과를 ResultSet 객체로 받는다. */
			rs = pstmt.executeQuery();
			
			/* 5. 퀴리를 실행한 결과를 while문 반복하면서 Board 객체에 담고 list에 담는다. */
			if(rs.next()) {
				 isPass = rs.getString(1).equals(pass); 
				 
			}
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
		
		return isPass;
	}	// end isPassCheck();

//	게시글의 내용을 수정하는 메서드
	public void updateBoard(Board board) {
		
		String sqlUpdate = "UPDATE jspbbs set title=?, writer=?, content=?,"
				 + " reg_date=SYSDATE, file1=? WHERE no=?";
		
		try {
			
			/* 2. DB에 연결 */
			conn = ds.getConnection();
			
			/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
			/* PreparedStatement */
			pstmt = conn.prepareStatement(sqlUpdate);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFile1());
			pstmt.setInt(5, board.getNo());
			
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			/* 6. DB 작업에 사용한 객체는 처음 가져온 역순으로 닫는다. */
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} 
			catch (SQLException se) {
				// TODO: handle exception
			}
			
		}
		
	}
	
//	no에 해당하는 게시글을 DB에서 수정하는 메서드
	public void deleteBoard(int no) {
		
		String sqlDelete = "DELETE FROM jspbbs WHERE no=?"; 
		
		try {
			
			/* 2. DB에 연결 */
			conn = ds.getConnection();
			
			/* 3. DB에 SQL 쿼리를 발행하는 객체를 활성화된 connection으로 부터 구한다. */
			/* PreparedStatement */
			pstmt = conn.prepareStatement(sqlDelete);

			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			/* 6. DB 작업에 사용한 객체는 처음 가져온 역순으로 닫는다. */
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} 
			catch (SQLException se) {
				// TODO: handle exception
			}
			
		}
		
	}// end deleteBoard(int no);
	
}
package com.jspstudy.ch06.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.jspstudy.ch06.vo.Board;

// JNDI 방식의 DBCP를 활용한 DAO(Data Access Object) 클래스
public class BoardDao {
	
	// 데이터베이스 작업에 필요한 객체 타입으로 변수를 선언	
	// Connection 객체는 DB에 연결해 작업을 수행할 수 있도록 지원하는 객체
	private Connection conn;
	
	// Statement, PreparedStatement 객체는 DB에 쿼리를 발행하는 객체
	private PreparedStatement pstmt;
	
	// ResultSet 객체는 DB에 SELECT 쿼리를 발행한 결과를 저장하는 객체
	private ResultSet rs;
	
	/* DataSource 객체는 데이터 원본과 연결할 수 있도록 지원하는 객체
	 * JNDI 방식으로 DBCP를 찾아 DBCP에서 Connection 객체를 대여하는 객체 
	 **/
	private static DataSource ds;
	
	/* 기본 생성자가 호출될 때 마다 ConnectionPool에 접근해  Connection 객체를
	 * 얻어 올 수 있는 DataSource 객체를 생성한다.
	 **/
	public BoardDao() {	 
		try {			
			/* 1. 자바 네이밍 서비스를 사용하기 위해 
			 * javax.naming 패키지의 InitialContext 객체를 생성한다.
			 **/ 
			Context initContext = new InitialContext();
			
			/* 2. InitialContext 객체를 이용해 디렉토리 서비스에서 필요한 객체를
			 * 찾기 위해 기본 네임스페이스를 인자로 지정해 Context 객체를 얻는다.
			 *
			 * 디렉토리 서비스에서 필요한 객체를 찾기 위한 일종의 URL 개념으로 
			 * 디렉토리 방식을 사용하므로 java:comp/env와 같이 지정한다.
			 **/
			Context envContext = (Context) initContext.lookup("java:/comp/env");

			/* 3. "jdbc/bbsDBPool"이라는 이름을 가진 DBCP에 접근하기 위한
			 * DataSource 객체를 얻는다.
			 *
			 * context.xml 파일에서 지정한 수의 커넥션을 생성해 커넥션 풀에 저장한다.
			 * "java:/comp/env"는 JNDI에서 기본적으로 사용하는 네임스페이스 이고 
			 * "jdbc/bbsDBPool"은 DBCP 이름으로 임의로 지정하여 사용할 수 있다.
			 * 
			 * BoardDao 클래스가 사용되면 클래스 정보가 메모리에 로딩되면서
			 * new 연산자에 의해서 BoardDao 클래스의 인스턴스가 생성된다.
			 * 그리고 이 생성자가 호출되면서 DBCP를 찾는 코드가 실행되고 DBCP에 
			 * 접근해 Connection 객체를 대여할 수 있는 DataSource 객체를 구한다.
			 **/
			ds = (DataSource) envContext.lookup("jdbc/bbsDBPool");
			
		} catch(NamingException e) {			
			System.out.println("BoardDao() : NamingException");
			e.printStackTrace();			
		} 
	}


	// DB에 등록된 전체 게시 글을 읽어와 ArrayList로 반환하는 메서드
	public ArrayList<Board> boardList() {
		
		String sqlBoardList = "SELECT * FROM jspbbs ORDER BY no DESC";
		ArrayList<Board> boardList = null;
		
		try {			
			// 4. DataSource 객체를 이용해 커넥션을 대여한다.
			conn = ds.getConnection();
			
			/* 5. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 *
			 * PreparedStatement는 SQL 명령을 캐싱하여(저장하여) 반복적으로 사용하기
			 * 때문에 prepareStatement()를 호출할 때 SQL 쿼리 문을 지정해야 한다.
			 **/
			pstmt = conn.prepareStatement(sqlBoardList);
			
			/* 6. PreparedStatement를 이용해 DB에 SELECT 쿼리를 발행하고 
			 * 그 결과로 ResultSet을 얻는다.
			 *
			 * executeQuery()는 실제 DBMS에 SELECT 쿼리를 발행하는 메소드로
			 * DB에서 검색된 데이터를 가상의 테이블 형태인 ResultSet 객체로 반환한다.
			 **/
			rs = pstmt.executeQuery();
			
			// 게시 글 리스트를 저장할 ArrayList 객체 생성
			boardList = new ArrayList<Board>();
			
			/* 7. 쿼리 실행 결과를 바탕으로 while문 안에서 하나의 게시 글을 저장할 
			 * Board 객체를 생성하고 이 객체에 하나의 게시 글 정보를 저장하고
			 * Board 객체를 ArrayList에 저장한다.
			 *
			 * ResultSet 객체는 DB로부터 읽어온 데이터에 접근하기 위해 테이블의
			 * 행을 가리키는 cursor를 제공한다. 맨 처음 ResultSet 객체를 반환
			 * 받으면 cursor는 첫 번째 행 바로 이전을 가리키고 있다. ResultSet의
			 * cursor가 맨 마지막 행에 도달하면 while문을 빠져 나온다.
 			 *
			 * ResultSet에는 다양한 데이터 타입에 대응하는 getter 메소드를
			 * 지원하고 있으며 SELECT 문장에서 지정한 컬럼의 index 또는
			 * 컬럼명으로 테이블의 필드 값을 가져올 수 있도록 getXxx() 메소드가
			 * 오버로딩 되어 있어 index와 컬럼명 둘 다 사용이 가능하다.
			 * 여기에 지정하는 index는 배열에서 사용되는 index의 개념이 아니라
			 * 첫 번째 컬럼, 두 번째 컬럼과 같이 위치의 개념으로 1부터 시작된다.
			 **/
			while(rs.next()) {
				
				/* 반복문을 돌 때마다 Board 객체를 생성해 DB로부터 읽어온 한 행의
				 * 데이터를 읽어 Board 객체에 저장하고 다시 ArrayList에 담는다.  
				 **/	
				Board b = new Board();
				
				/* ResultSet 객체의 getXXX() 메서드에 컬럼 위치에 대한 index 값을 
				 * 1부터 지정할 수도 있고 컬럼 이름을 지정해 데이터를 읽어 올 수 있다.
				 **/
				b.setNo(rs.getInt("no"));
				b.setTitle(rs.getString("title"));
				b.setWriter(rs.getString("writer"));
				b.setContent(rs.getString("content"));				
				b.setRegDate(rs.getTimestamp("reg_date"));
				b.setReadCount(rs.getInt("read_count"));
				b.setPass(rs.getString("pass"));
				b.setFile1(rs.getString("file1"));
				
				boardList.add(b);
			}			
		} catch(SQLException e) {
			System.out.println("BoardDao - boardList() - SQLException");
			e.printStackTrace();
			
		} finally {
			try {
				// 8. 사용한 ResultSet과 PreparedStatement를 종료한다.
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				
				// 9. 커넥션 풀로 Connection 객체를 반납한다.
				if(conn != null) conn.close();
			} catch(SQLException e) {}
		}
		
		// 10. 데이터베이스로부터 읽어온 게시 글 리스트를 반환한다.
		return boardList;
		
	} // end boardList();

	
	/* 게시 글 상세보기 요청 시 호출되는 메서드
	 * no에 해당하는 게시 글 을 DB에서 읽어와 Board 객체로 반환하는 메서드 
	 **/
	public Board getBoard(int no) {
		
		String sqlBoard = "SELECT * FROM jspbbs WHERE no=?";
		Board board = null;
		
		try {
			// 4. DataSource 객체를 이용해 커넥션을 대여한다.
			conn = ds.getConnection();
			
			/* 5. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 *	Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 *
			 * PreparedStatement는 SQL 명령을 캐싱하여(저장하여) 반복적으로 사용하기
			 * 때문에 prepareStatement()를 호출할 때 SQL 쿼리 문을 지정해야 한다.
			 * PreparedStatement 객체는 반복적으로 사용되는 SQL 명령 외에 
			 * SQL 문장에 포함되어 변경되는 데이터를 Placeholder(?)로 지정할 수 있다. 
			 * SQL 문장의 조건식에 사용되는 데이터나 실제 테이블에 입력되는 데이터는
			 * SQL 명령이 동일하나 상황에 따라 조건식에 사용되는 데이터가 변경될 수
			 * 있고 또한 테이블에 새롭게 추가되는 데이터가 다르게 입력된다. 이렇게
			 * 변경되는 데이터가 SQL 문장에서 기술되어야 할 위치에 Placeholder(?)를
			 * 지정하고 실제 DB에 SQL 쿼리 문을 전송하기 전에 PreparedStatement
			 * 객체의 setXxx()를 이용해 데이터를 변경해 가며 질의 할 수 있다.
			 * 여기서 주의할 점은 setXxx()에 지정하는 index의 개념이 배열에서
			 * 사용되는 index의 개념이 아니라 첫 번째 Placeholder(?), 두 번째 
			 * Placeholder(?)와 같이 위치의 개념으로 시작 번호가 1부터 시작된다.
			 **/
			pstmt = conn.prepareStatement(sqlBoard);
			
			/* 6. PreparedStatement 객체에 SELECT 쿼리의 Placeholder(?)와
			 * 데이터를 맵핑 한다.
			 **/
			pstmt.setInt(1,  no);
			
			// 7. 데이터베이스에 SELECT 쿼리를 발행하고 ResultSet 객체로 받는다.
			rs = pstmt.executeQuery();
			
			/* 8. Board 객체를 생성하고 ResultSet으로부터 데이터를 읽어
			 * Board 객체의 각 프로퍼티에 값을 설정한다.
			 * no가 Primary Key 이므로 no에 해당하는 게시 글을 DB에서 읽으면
			 * 없거나 또는 하나의 게시 글 정보를 얻을 수 있으므로 if문을 사용했다. 
			 * 
			 * ResultSet 객체는 DB로부터 읽어온 데이터에 접근하기 위해 테이블의
			 * 행을 가리키는 cursor를 제공한다. 맨 처음 ResultSet 객체를 반환
			 * 받으면 cursor는 첫 번째 행 바로 이전을 가리키고 있다. ResultSet의
			 * cursor가 맨 마지막 행에 도달하면 while 문을 빠져 나온다.
			 * 여기서는 결과가 많아야 한 행이므로 한 번 실행되고 if문을 빠져 나온다.
			 * 
			 * ResultSet에는 다양한 데이터 타입에 대응하는 getter 메소드를
			 * 지원하고 있으며 SELECT 문장에서 지정한 컬럼의 index 또는
			 * 컬럼명으로 테이블의 필드 값을 가져올 수 있도록 getXxx() 메소드가
			 * 오버로딩 되어 있어 index와 컬럼명 둘 다 사용이 가능하다.
			 * 여기에 지정하는 index는 배열에서 사용되는 index의 개념이 아니라
			 * 첫 번째 컬럼, 두 번째 컬럼과 같이 위치의 개념으로 1부터 시작된다.
			 **/
			if(rs.next()) {
				board = new Board();
				
				/* ResultSet 객체의 getXXX() 메서드에 컬럼 위치에 대한 index 값을 
				 * 1부터 지정할 수도 있고 컬럼 이름을 지정해 데이터를 읽어 올 수 있다.
				 **/
				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));				
				board.setRegDate(rs.getTimestamp("reg_date"));
				board.setReadCount(rs.getInt("read_count"));
				board.setPass(rs.getString("pass"));
				board.setFile1(rs.getString("file1"));
			}			
		} catch(SQLException e) {
			System.out.println("BoardDao - getBoard() : SQLException");
			e.printStackTrace();
			
		} finally {
			try {
				// 9. 사용한 ResultSet과 PreparedStatement 객체를 닫는다.
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				
				// 10. 커넥션 풀로 Connection 객체를 반납한다.
				if(conn != null) conn.close();
			} catch(SQLException e) {}
		}
		
		// 11. 데이터베이스로부터 읽어온 no에 해당하는 게시 글 정보를 반환한다.
		return board;
		
	} // end getBoard(int no);
	
	
	/* 게시 글쓰기 요청시 호출되는 메서드
	 * 게시 글을 작성하고 등록하기 버튼을 클릭하면 게시 글을 DB에 추가하는 메서드 
	 **/
	public void insertBoard(Board board) {

		// 아래에서 file1은 아직 사용하지 않고 파일 업로드를 구현할 때 사용할 것임		 
		String sqlInsert = "INSERT INTO jspbbs(no, title, writer, content,"
				+ " reg_date, read_count, pass, file1) "
				+ " VALUES(jspbbs_seq.NEXTVAL, ?, ?, ?, SYSDATE, 0, ?, ?)";
		
		try {
			// 4. DataSource 객체를 이용해 커넥션을 대여한다.
			conn = ds.getConnection();
			
			/* 5. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
		 	 *
			 * PreparedStatement는 SQL 명령을 캐싱하여(저장하여) 반복적으로 사용하기
			 * 때문에 prepareStatement()를 호출할 때 SQL 쿼리 문을 지정해야 한다.
			 * PreparedStatement 객체는 반복적으로 사용되는 SQL 명령 외에 
			 * SQL 문장에 포함되어 변경되는 데이터를 Placeholder(?)로 지정할 수 있다. 
			 * SQL 문장의 조건식에 사용되는 데이터나 실제 테이블에 입력되는 데이터는
			 * SQL 명령이 동일하나 상황에 따라 조건식에 사용되는 데이터가 변경될 수
			 * 있고 또한 테이블에 새롭게 추가되는 데이터가 다르게 입력된다. 이렇게
			 * 변경되는 데이터가 SQL 문장에서 기술되어야 할 위치에 Placeholder(?)를
			 * 지정하고 실제 DB에 SQL 쿼리 문을 전송하기 전에 PreparedStatement
			 * 객체의 setXxx()를 이용해 데이터를 변경해 가며 질의 할 수 있다.
		 	 * 여기서 주의할 점은 setXxx()에 지정하는 index의 개념이 배열에서
			 * 사용되는 index의 개념이 아니라 첫 번째 Placeholder(?), 두 번째 
			 * Placeholder(?)와 같이 위치의 개념으로 시작 번호가 1부터 시작된다.
			 **/	
			pstmt = conn.prepareStatement(sqlInsert);
			
			/* 6. PreparedStatement 객체의 Placeholder(?)에 대응하는 
			 * 값을 Board 객체의 데이터를 사용해 순서에 맞게 설정하고 있다. 
			 **/
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());			
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getPass());
			pstmt.setString(5, board.getFile1());
			
			/* 7. 데이터베이스에 INSERT 쿼리를 발행하여 게시 글 정보를 테이블에 추가한다.
			 *	
			 * executeUpdate()는 DBMS에 INSERT, UPDATE, DELETE 쿼리를
			 * 발행하는 메소드로 추가, 수정, 삭제된 레코드의 개수를 반환한다.
			 **/
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("BoardDao - insertBoard() : SQLException");
			e.printStackTrace();
			
		} finally {
			try {
				// 8. 사용한 PreparedStatement 객체를 닫는다.				
				if(pstmt != null) pstmt.close();
				
				// 9. 커넥션 풀로 Connection 객체를 반납한다.
				if(conn != null) conn.close();				
			} catch(SQLException se) {}
		}
		
	} // end indertBoard(Board board);
	
	
	/* 게시 글 수정, 게시 글 삭제 시 비밀번호 입력을 체크하는 메서드
	 **/
	public boolean isPassCheck(int no, String pass) {
		boolean isPass = false;
		String sqlPass = "SELECT pass FROM jspbbs WHERE no=?";
		try {
			// 4. DataSource 객체를 이용해 커넥션을 대여한다.
			conn = ds.getConnection();
			
			/* 5. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 *
			 * PreparedStatement는 SQL 명령을 캐싱하여(저장하여) 반복적으로 사용하기
			 * 때문에 prepareStatement()를 호출할 때 SQL 쿼리 문을 지정해야 한다.
			 * PreparedStatement 객체는 반복적으로 사용되는 SQL 명령 외에 
			 * SQL 문장에 포함되어 변경되는 데이터를 Placeholder(?)로 지정할 수 있다. 
			 * SQL 문장의 조건식에 사용되는 데이터나 실제 테이블에 입력되는 데이터는
			 * SQL 명령이 동일하나 상황에 따라 조건식에 사용되는 데이터가 변경될 수
			 * 있고 또한 테이블에 새롭게 추가되는 데이터가 다르게 입력된다. 이렇게
			 * 변경되는 데이터가 SQL 문장에서 기술되어야 할 위치에 Placeholder(?)를
			 * 지정하고 실제 DB에 SQL 쿼리 문을 전송하기 전에 PreparedStatement
			 * 객체의 setXxx()를 이용해 데이터를 변경해 가며 질의 할 수 있다.
			 * 여기서 주의할 점은 setXxx()에 지정하는 index의 개념이 배열에서
			 * 사용되는 index의 개념이 아니라 첫 번째 Placeholder(?), 두 번째 
			 * Placeholder(?)와 같이 위치의 개념으로 시작 번호가 1부터 시작된다.
			 **/
			pstmt = conn.prepareStatement(sqlPass);
			
			/* 6. PreparedStatement 객체의 Placeholder(?)에 대응하는 
			 * 값을 매개변수로 받은 데이터를 사용해 순서에 맞게 설정하고 있다. 
			 **/
			pstmt.setInt(1, no);
			
			// 7. 데이터베이스에 SELECT 쿼리를 발행하고 ResultSet 객체로 받는다.
			rs = pstmt.executeQuery();
			
			/* 8. no가 Primary Key 이므로 no에 해당하는 게시 글을 DB에서 읽으면
			 * 없거나 또는 하나의 게시 글 정보를 얻을 수 있으므로 if문을 사용했다. 
			 * 
			 * ResultSet 객체는 DB로부터 읽어온 데이터에 접근하기 위해 테이블의
			 * 행을 가리키는 cursor를 제공한다. 맨 처음 ResultSet 객체를 반환
			 * 받으면 cursor는 첫 번째 행 바로 이전을 가리키고 있다. ResultSet의
			 * cursor가 맨 마지막 행에 도달하면 while문을 빠져 나온다.
			 * 여기서는 결과가 많아야 한 행이므로 한 번 실행되고 if문을 빠져 나온다.
			 * 
			 * ResultSet에는 다양한 데이터 타입에 대응하는 getter 메소드를
			 * 지원하고 있으며 SELECT 문장에서 지정한 컬럼의 index 또는
			 * 컬럼명으로 테이블의 필드 값을 가져올 수 있도록 getXxx() 메소드가
			 * 오버로딩 되어 있어 index와 컬럼명 둘 다 사용이 가능하다.
			 * 여기에 지정하는 index는 배열에서 사용되는 index의 개념이 아니라
			 * 첫 번째 컬럼, 두 번째 컬럼과 같이 위치의 개념으로 1부터 시작된다.
			 **/
			if(rs.next()) {
				isPass = rs.getString(1).equals(pass); 
			}
		} catch(SQLException e) {
			System.out.println("BoardDao - isPassCheck() : SQLException");
			e.printStackTrace();
			
		} finally {
			try {
				// 9. 사용한 ResultSet과 PreparedStatement 객체를 닫는다.
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				
				// 10. 커넥션 풀로 Connection 객체를 반납한다.
				if(conn != null) conn.close();
			} catch(SQLException e) {}
		} 
		
		// 11. 메서드 실행 결과로 게시 글 비밀번호가 맞는지 여부를 반환한다. 
		return isPass;
		
	} // end isPassCheck();
	
	
	/* 게시 글 수정 요청시 호출되는 메서드
	 * 게시 글 수정 폼에서 수정하기 버튼이 클릭되면 게시 글을 DB에 수정하는 메서드 
	 **/
	public void updateBoard(Board board) {
		
		String sqlUpdate = "UPDATE jspbbs set title=?, writer=?, content=?,"
				+ " reg_date=SYSDATE, file1=? WHERE no=?";	
		
		try {
			// 4. DataSource 객체를 이용해 커넥션을 대여한다.
			conn = ds.getConnection();
			
			/* 5. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 *
			 * PreparedStatement는 SQL 명령을 캐싱하여(저장하여) 반복적으로 사용하기
			 * 때문에 prepareStatement()를 호출할 때 SQL 쿼리 문을 지정해야 한다.
			 * PreparedStatement 객체는 반복적으로 사용되는 SQL 명령 외에 
			 * SQL 문장에 포함되어 변경되는 데이터를 Placeholder(?)로 지정할 수 있다. 
			 * SQL 문장의 조건식에 사용되는 데이터나 실제 테이블에 입력되는 데이터는
			 * SQL 명령이 동일하나 상황에 따라 조건식에 사용되는 데이터가 변경될 수
			 * 있고 또한 테이블에 새롭게 추가되는 데이터가 다르게 입력된다. 이렇게
			 * 변경되는 데이터가 SQL 문장에서 기술되어야 할 위치에 Placeholder(?)를
			 * 지정하고 실제 DB에 SQL 쿼리 문을 전송하기 전에 PreparedStatement
			 * 객체의 setXxx()를 이용해 데이터를 변경해 가며 질의 할 수 있다.
			 * 여기서 주의할 점은 setXxx()에 지정하는 index의 개념이 배열에서
			 * 사용되는 index의 개념이 아니라 첫 번째 Placeholder(?), 두 번째 
			 * Placeholder(?)와 같이 위치의 개념으로 시작 번호가 1부터 시작된다.
			 **/
			pstmt = conn.prepareStatement(sqlUpdate);
			
			/* 6. PreparedStatement 객체의 Placeholder(?)에 대응하는 
			 * 값을 매개변수로 받은 데이터를 사용해 순서에 맞게 설정하고 있다. 
			 **/
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());			
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getFile1());
			pstmt.setInt(5, board.getNo());			
			
			/* 7. 데이터베이스에 INSERT 쿼리를 발행하여 게시 글 정보를 테이블에 추가한다.
			 *	
			 * executeUpdate()는 DBMS에 INSERT, UPDATE, DELETE 쿼리를
			 * 발행하는 메소드로 추가, 수정, 삭제된 레코드의 개수를 반환한다.
			 **/
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("BoardDao - updateBoard() : SQLException");
			e.printStackTrace();
			
		} finally {			
			try {
				// 8. 사용한 PreparedStatement 객체를 닫는다.				
				if(pstmt != null) pstmt.close();
				
				// 9. 커넥션 풀로 Connection 객체를 반납한다.
				if(conn != null) conn.close();				
			} catch(SQLException se) {}
		}
	} // end updateBoard(Board board);
	
	
	/* 게시 글 삭제 요청 시 호출되는 메서드 
	 * no에 해당 하는 게시 글을 DB에서 삭제하는 메서드 
	 **/
	public void deleteBoard(int no) {
		
		String sqlDelete = "DELETE FROM jspbbs WHERE no=?"; 
		try {			
			// 4. DataSource 객체를 이용해 커넥션을 대여한다.
			conn = ds.getConnection();
			
			/* 5. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 *
			 * PreparedStatement는 SQL 명령을 캐싱하여(저장하여) 반복적으로 사용하기
			 * 때문에 prepareStatement()를 호출할 때 SQL 쿼리 문을 지정해야 한다.
			 * PreparedStatement 객체는 반복적으로 사용되는 SQL 명령 외에 
			 * SQL 문장에 포함되어 변경되는 데이터를 Placeholder(?)로 지정할 수 있다. 
			 * SQL 문장의 조건식에 사용되는 데이터나 실제 테이블에 입력되는 데이터는
			 * SQL 명령이 동일하나 상황에 따라 조건식에 사용되는 데이터가 변경될 수
			 * 있고 또한 테이블에 새롭게 추가되는 데이터가 다르게 입력된다. 이렇게
			 * 변경되는 데이터가 SQL 문장에서 기술되어야 할 위치에 Placeholder(?)를
			 * 지정하고 실제 DB에 SQL 쿼리 문을 전송하기 전에 PreparedStatement
			 * 객체의 setXxx()를 이용해 데이터를 변경해 가며 질의 할 수 있다.
			 * 여기서 주의할 점은 setXxx()에 지정하는 index의 개념이 배열에서
			 * 사용되는 index의 개념이 아니라 첫 번째 Placeholder(?), 두 번째 
			 * Placeholder(?)와 같이 위치의 개념으로 시작 번호가 1부터 시작된다.
			 **/
			pstmt = conn.prepareStatement(sqlDelete);
			
			/* 6. PreparedStatement 객체의 Placeholder(?)에 대응하는 
			 * 값을 매개변수로 받은 데이터를 사용해 순서에 맞게 설정하고 있다. 
			 **/
			pstmt.setInt(1, no);
			
			/* 7. 데이터베이스에 INSERT 쿼리를 발행하여 게시 글 정보를 테이블에 추가한다.
			 *	
			 * executeUpdate()는 DBMS에 INSERT, UPDATE, DELETE 쿼리를
			 * 발행하는 메소드로 추가, 수정, 삭제된 레코드의 개수를 반환한다.
			 **/
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {			
			try {
				// 8. 사용한 PreparedStatement 객체를 닫는다.				
				if(pstmt != null) pstmt.close();
				
				// 9. 커넥션 풀로 Connection 객체를 반납한다.
				if(conn != null) conn.close();				
			} catch(SQLException se) {}
		}
	} // end deleteBoard(int no);
}







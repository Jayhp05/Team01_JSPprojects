package com.jspstudy.ch06.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jspstudy.ch06.vo.Board;

// JDBC를 활용한 DAO(Data Access Object) 클래스
public class BoardDao01 {
	
	// 오라클 접속에 필요한 정보를 static 상수로 정의
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL ="jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "hr";
	private static final String PASS = "hr";
	
	// 데이터베이스 작업에 필요한 객체 타입으로 변수를 선언
	// Connection 객체는 DB에 연결해 작업을 수행할 수 있도록 지원하는 객체
	Connection conn;
	
	// Statement, PreparedStatement 객체는 DB에 쿼리를 발행하는 객체
	PreparedStatement pstmt;
	
	// ResultSet 객체는 DB에 SELECT 쿼리를 발행한 결과를 저장하는 객체
	ResultSet rs;
	
	/* 기본 생성자가 호출될 때 마다 접속 드라이버를 로딩하고  
	 * DriverManager를 이용해 Connection 객체를 생성한다.
	 **/
	public BoardDao01() {
		
		try {			
			/* 1. DBMS의 접속 드라이버를 로딩 한다.
			 * Oracle 드라이버를 다운로드 하여 WEB-INF/lib 폴더에 추가해야 한다.
			 * 
			 * 아래와 같이 forName() 메서드에 로딩할 접속 드라이버의 클래스 이름을
			 * 지정하면 지정한 JDBC 드라이버를 읽어 DriverManager에 등록한다.
			 **/
			Class.forName(DRIVER);
			
		} catch (ClassNotFoundException e) {
			System.out.println("BoardDao01() : ClassNotFoundException");
			e.printStackTrace();
			
		}
	}

	// DB에 등록된 전체 게시 글을 읽어와 ArrayList로 반환하는 메서드
	public ArrayList<Board> boardList() {
		
		String sqlBoardList = "SELECT * FROM jspbbs ORDER BY no DESC";
		ArrayList<Board> boardList = null;
		try {			
			/* 2. 데이터베이스 연결 - DB 커넥션을 생성한다.
			 * getConnection() 메서드는 인수로 지정한 접속 정보를 바탕으로 
			 * DriverManager에 등록된 접속 드라이버를 이용해 데이터베이스에
			 * 접속하고 데이터베이스와 연결된 Connection 객체를 반환한다.
			 **/
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			/* 3. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 *
			 * PreparedStatement는 SQL 명령을 캐싱하여(저장하여) 반복적으로 사용하기
			 * 때문에 prepareStatement()를 호출할 때 SQL 쿼리 문을 지정해야 한다.			 
			 **/
			pstmt = conn.prepareStatement(sqlBoardList);
			
			/* 4. PreparedStatement를 이용해 DB에 SELECT 쿼리를 발행하고 
			 * 그 결과로 ResultSet을 얻는다.
			 *
			 * executeQuery()는 실제 DBMS에 SELECT 쿼리를 발행하는 메소드로
			 * DB에서 검색된 데이터를 가상의 테이블 형태인 ResultSet 객체로 반환한다.
			 **/
			rs = pstmt.executeQuery();
			
			// 게시 글 리스트를 저장할 ArrayList 객체 생성
			boardList = new ArrayList<Board>();
			
			/* 5. 쿼리 실행 결과를 바탕으로 while문 안에서 하나의 게시 글을 저장할 
			 * Board 객체를 생성하고 이 객체에 하나의 게시 글 정보를 저장하고
			 * Board 객체를 ArrayList에 저장한다.
			 *
			 * ResultSet 객체는 DB로부터 읽어온 데이터에 접근하기 위해 테이블의
			 * 행을 가리키는 cursor를 제공한다. 맨 처음 ResultSet 객체를 반환
			 * 받으면 cursor는 첫 번째 행 바로 이전을 가리키고 있다. ResultSet의
			 * cursor가 맨 마지막 행에 도달하면 while문을 빠져 나온다.
 			 *
			 * ResultSet에는 자바의 다양한 데이터 타입에 대응하는 getter 메소드를
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
			System.out.println("BoardDao01 - boardList() - SQLException");
			e.printStackTrace();
			
		} finally {
			try {
				// 6. 사용한 ResultSet과 PreparedStatement를 종료한다.
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				
				// 7. Connection 객체를 닫아 DBMS 연결을 종료한다.
				if(conn != null) conn.close();
			} catch(SQLException e) {}
		}
		
		// 8. 데이터베이스로부터 읽어온 게시 글 리스트를 반환한다.
		return boardList;
		
	} // end boardList();
}







package com.jspstudy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jspstudy.bbs.vo.Board;

// DB 작업을 전담하는 DAO(Data Access Object) 클래스
public class BoardDao {
	
	// 데이터베이스 작업에 필요한 객체 타입으로 변수를 선언	
	// Connection 객체는 DB에 연결해 작업을 수행할 수 있도록 지원하는 객체
	private Connection conn;
	
	// Statement, PreparedStatement 객체는 DB에 쿼리를 발행하는 객체
	private PreparedStatement pstmt;
	
	// ResultSet 객체는 DB에 SELECT 쿼리를 발행한 결과를 저장하는 객체
	private ResultSet rs;
	
	/* 검색어에 해당하는 게시글 수를 계산하기 위해 호출되는 메서드
	 * DB 테이블에 등록된 모든 게시글의 수를 반환하는 메서드
	 **/
	public int getBoardCount(String type, String keyword) {
		System.out.println(type + " - " + keyword);
		
		/* 이 부분에서 우리는 SQL 쿼리를 작성하는데 주의를 기울여야 한다.
		 * 검색 옵션에 따라서 검색하는 컬럼이 다르기 때문에 파라미터로 받은 type을 
		 * 아래와 같이 쿼리의 파라미터(?)로 지정해 검색 옵션의 변경에 따라 동적으로
		 * 다른 쿼리가 발행되게 하고 싶지만 이 방법은 제대로 동작하지 못한다. 
		 * 
		 * "SELECT COUNT(*) FROM jspbbs WHERE ? LIKE '%' || ? || '%'"
		 * 
		 * 
		 * PreparedStatement는 기준이 되는 SQL 쿼리를 캐싱하기 때문에
		 * 이 객체를 Connection 객체로부터 받아 올 때 기준 쿼리를 인수로 지정하는데
		 * 이 때 WHERE에 검색 대상이 되는 컬럼명을 파라미터로(?) 지정하게 되면
		 * 쿼리가 캐싱될 때 검색 대상이 되는 컬럼이 지정되지 못하게 되므로 이 쿼리의
		 * 검색 결과는 항상 0이 된다.
		 * 
		 * 기준 쿼리에서 사용되는 컬럼은 PreparedStatement 객체를 생성할 때
		 * 정해져 있어야하기 때문에 아래와 같이 type을 지정하면 된다.
		 **/		
		String sqlCount = "SELECT COUNT(*) FROM jspbbs WHERE " 	
					+ type + " LIKE '%' || ? || '%'";
		//String sqlCount = 
		//	"SELECT COUNT(*) FROM jspbbs WHERE " + type + " LIKE ?";
		int count = 0;
		
		try{			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sqlCount);
			pstmt.setString(1,  keyword);			
			//pstmt.setString(1,  "%" + keyword + "%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}			
		} catch(Exception e) {			
			e.printStackTrace();
		} finally {			
			DBManager.close(conn, pstmt, rs);
		}
		return count;
	}
	
	
	/* 제목, 작성자, 내용에서 검색어가 포함된 게시글 검색 시 호출되는 메서드
	 * 요청한 페이지에 해당하는 검색 결과를 DB에서 읽어와 반환하는 메서드
	 **/
	public ArrayList<Board> searchList(
			String type, String keyword, int startRow, int endRow) {
		
		/* 검색어가 포함된 게시글 리스트를 추출하기 위한 쿼리
		 * 테이블에서 현재 페이지에 해당하는 게시글을 검색할 때 ROWNUM을 사용했다.
		 * ROWNUM은 쿼리의 결과로 검색되는 행들의 순서 값을 가진 의사컬럼으로
		 * 1부터 시작한다. 최신 게시글을 먼저 보여주기 위해 ORDER BY DESC를 
		 * 지정하고 요청된 페이지에 보여줄 게시글의 시작 행과 마지막 행을 지정한다.
		 *  
		 * 아래의 쿼리로 질의하게 되면 게시글 전체를 글 번호에 해당하는 no를 기준으로
		 * 내림차순 정렬하여 검색하고 WHERE 절에 지정한 첫 번째 Placeholder(?)에
		 * 해당하는 시작 행부터 두 번째 Placeholder(?)에 해당하는 마지막 행까지의
		 * 게시글을 추출할 수 있다.
		 **/		
		String sqlSearchList = "SELECT * FROM (SELECT ROWNUM num, no, title,"
			    + " writer, content, reg_date, read_count, pass, file1 FROM"
				+ " (SELECT * FROM jspbbs WHERE " + type + " LIKE ?"
				+ " ORDER BY no DESC)) WHERE num >= ? AND num <= ?";
		
		ArrayList<Board> boardList = null;
		
		try{			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sqlSearchList);			
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				boardList = new ArrayList<Board>();
				
				do {					
					Board board = new Board();
					board.setNo(rs.getInt("no"));
					board.setTitle(rs.getString("title"));
					board.setContent(rs.getString("content"));
					board.setWriter(rs.getString("writer"));							
					board.setRegDate(rs.getTimestamp("reg_date"));
					board.setReadCount(rs.getInt("read_count"));
					board.setPass(rs.getString("pass"));
					board.setFile1(rs.getString("file1"));
					
					boardList.add(board);
					
				} while(rs.next());
			}
		} catch(Exception e) {			
			e.printStackTrace();
			
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return boardList;
	}
	
	
	/* 전체 게시글 수를 계산하기 위해 호출되는 메서드 - paging 처리에 사용
	 * DB 테이블에 등록된 모든 게시글의 수를 반환하는 메서드
	 **/
	public int getBoardCount() {
		
		String sqlCount = "SELECT COUNT(*) FROM jspbbs";
		int count = 0;
		
		try{			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sqlCount);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				count = rs.getInt(1);
			}			
		} catch(Exception e) {			
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return count;
	}


	/* 한 페이지에 보여 질 게시글 리스트 요청시 호출되는 메소드
	 * 요청한 페이지에 해당하는 게시글 리스트를 DB에서 읽어와 반환하는 메소드
	 **/
	public ArrayList<Board> boardList(int startRow, int endRow) {
		
		/* 요청한 페이지에 해당하는 게시글 리스트를 추출하기 위한 쿼리
		 * 테이블에서 현재 페이지에 해당하는 게시글을 검색할 때 ROWNUM을 사용했다.
		 * ROWNUM은 쿼리의 결과로 검색되는 행들의 순서 값을 가진 의사컬럼으로
		 * 1부터 시작한다. 최신 게시글을 먼저 보여주기 위해 ORDER BY DESC를 
		 * 지정하고 요청된 페이지에 보여줄 게시글의 시작 행과 마지막 행을 지정한다.
		 *  
		 * 아래의 쿼리로 질의하게 되면 게시글 전체를 글 번호에 해당하는 no를 기준으로
		 * 내림차순 정렬하여 검색하고 WHERE 절에 지정한 첫 번째 Placeholder(?)에
		 * 해당하는 시작 행 부터 두 번째 Placeholder(?)에 해당하는 마지막 행까지의
		 * 게시글을 추출할 수 있다.
		 **/		
		String sqlBoardList = "SELECT * FROM (SELECT ROWNUM num,"
				+ " no, title, writer, content, reg_date, read_count, pass, file1 FROM"
				+ " (SELECT * FROM jspbbs ORDER BY no DESC)) "
				+ " WHERE num >= ? AND num <= ?";
		
		ArrayList<Board> boardList = null;
		
		try{			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sqlBoardList);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				boardList = new ArrayList<Board>();
				
				do {					
					Board board = new Board();
					board.setNo(rs.getInt("no"));
					board.setTitle(rs.getString("title"));
					board.setContent(rs.getString("content"));
					board.setWriter(rs.getString("writer"));							
					board.setRegDate(rs.getTimestamp("reg_date"));
					board.setReadCount(rs.getInt("read_count"));
					board.setPass(rs.getString("pass"));
					board.setFile1(rs.getString("file1"));
					
					boardList.add(board);
					
				} while(rs.next());
			}
		} catch(Exception e) {			
			e.printStackTrace();
			
		} finally {			
			DBManager.close(conn, pstmt, rs);
		}
		return boardList;
	}

	
	/* 게시글 상세보기 요청 시 호출되는 메서드
	 * no에 해당하는 게시글 을 DB에서 읽어와 Board 객체로 반환하는 메서드 
	 *
	 * 이 메서드는 no와 함께 게시글 읽은 횟수의 증가 여부를 boolean 형으로
	 * 두 번째 파라미터로 받아서 이 값이 true 면 게시글 읽을 횟수를 하나 증가하고
	 * false 면 증가하지 않는 코드가 추가 되었다. 아래와 같이 하나의 논리적인 작업
	 * 안에서 DB에 쿼리가 여러 번 발행되게 되면 SELECT 쿼리인 경우에는 큰 문제가
	 * 없겠지만 하나의 논리적인 작업 단위에서 추가, 수정, 삭제 작업을 여러 번 해야 할  
	 * 경우에 이 과정에서 중간에 에러가 발생되면 앞의 쿼리는 DB에 적용되고 뒤에 쿼리는
	 * 오류로 인해서 제대로 DB에 적용되지 못하는 문제가 발생한다. 이렇게 하나의 작업
	 * 안에서 쿼리가 여러 번 발행될 때 모두 DB에 반영되거나 또는 중간에 에러가 발생하면
	 * 현재 작업 이전의 상태로 되돌려서 문제가 발생되지 않도록 해야 하는데 여러 번의
	 * 쿼리 발행을 하나의 논리적인 작업 단위로 묶어서 전체를 적용하여 commit 하거나
	 * 또는 중간에 문제가 생겨서 이전 작업 상태로 되돌려 rollback 해서 DB에서 문제가
	 * 발생되지 않도록 처리하는 것을 트랜잭션(Transaction) 처리라고 한다.    
	 **/
	public Board getBoard(int no, boolean state) {
		String boardSql = "SELECT * FROM jspbbs WHERE no=?";
		String countSql = "UPDATE jspbbs set read_count = read_count + 1 "
				+ "WHERE no = ?";
		Board board = null;
		
		try{			
			// 1. DBManager을 이용해 DBCP로부터 Connection을 대여한다.
			conn = DBManager.getConnection();
			
			// 활성화된 Connection에 트랜잭션을 시작한다.
			DBManager.setAutoCommit(conn, false);
			
			// 게시글 조회 요청일 때 state는 true로 게시글 조회 수를 1증가 시킨다.
			if(state) {				
				/* 2. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
				 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
				 **/
				pstmt = conn.prepareStatement(countSql);
				
				/* 3. PreparedStatement 객체의 Placeholder(?)에 대응하는
				 * 값을 순서에 맞게 지정하고 있다. 
				 **/
				pstmt.setInt(1, no);	
				
				/* 4. 데이터베이스에 UPDATE 쿼리를 발행해 조회수를 1증가 시킨다.
				 *	
				 * executeUpdate()는 DBMS에 INSERT, UPDATE, DELETE 쿼리를
				 * 발행하는 메소드로 추가, 수정, 삭제된 레코드의 개수를 반환 한다.
				 **/				
				pstmt.executeUpdate();				
			}
			
			/* 2. DBMS에 SQL 쿼리를 발생하기 위해 활성화된 
			 * Connection 객체로부터 PreparedStatement 객체를 얻는다.
			 **/
			pstmt = conn.prepareStatement(boardSql);
			
			/* 3. PreparedStatement 객체의 Placeholder(?)에 대응하는
			 * 값을 순서에 맞게 지정하고 있다. 
			 **/
			pstmt.setInt(1, no);
			
			/* 4. PreparedStatement를 이용해 SELECT 쿼리를 발행한다.
			 *
			 * executeQuery()는 실제 DBMS에 SELECT 쿼리를 발행하는 메소드로
			 * DB에서 검색된 데이터를 가상의 테이블 형태인 ResultSet 객체로 반환 한다.
			 **/
			rs = pstmt.executeQuery();
			
			/* 5. 쿼리 실행 결과를 바탕으로 요청한 게시글 정보를 구한다.
			 *
			 * ResultSet 객체는 DB로부터 읽어온 데이터에 접근하기 위해 테이블의
			 * 행을 가리키는 cursor를 제공한다. 맨 처음 ResultSet 객체를 반환
			 * 받으면 cursor는 첫 번째 행 바로 이전을 가리키고 있다. 
			 *
			 * 테이블의 PRIMARY KEY인 no에 해당하는 게시글을 SELECT 해서
			 * ResultSet에는 게시글 하나의 정보만 존재하기 때문에 if 문을 사용했다.
			 **/			
			if(rs.next()) {
				board = new Board();				
				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getTimestamp("reg_date"));
				board.setReadCount(rs.getInt("read_count"));
				board.setPass(rs.getString("pass"));
				board.setFile1(rs.getString("file1"));}
			
			// 모든 작업이 완료되면 커밋하여 트랜잭션을 종료한다.
			DBManager.commit(conn);
			
		} catch(Exception e) {
			// DB 작업이 하나라도 에러가 발생하면 롤백하고 트랜잭션을 종료한다.
			DBManager.rollback(conn);
			
			System.out.println("BoardDao - getBoard(no, state)");
			e.printStackTrace();			
		} finally {			
			// 6. DBManager를 이용해 Connection을 DBCP에 반납한다.
			DBManager.close(conn, pstmt, rs);
		}		
		// 요청한 하나의 게시글을 반환 한다.
		return board;
	}
	
	
	/* 게시글 쓰기 요청시 호출되는 메서드
	 * 게시글을 작성하고 등록하기 버튼을 클릭하면 게시글을 DB에 추가하는 메서드 
	 **/
	public void insertBoard(Board board) {
		
		String sqlInsert = "INSERT INTO jspbbs(no, title, writer, content,"
				+ " reg_date, read_count, pass, file1) "
				+ " VALUES(jspbbs_seq.NEXTVAL, ?, ?, ?, SYSDATE, 0, ?, ?)";
		
		try {			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sqlInsert);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());			
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getPass());
			pstmt.setString(5, board.getFile1());
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {			
			DBManager.close(conn, pstmt, rs);
		}
	}


	/* 게시글 수정, 게시글 삭제 시 비밀번호 입력을 체크하는 메서드
	 **/
	public boolean isPassCheck(int no, String pass) {
		boolean isPass = false;
		String sqlPass = "SELECT pass FROM jspbbs WHERE no=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sqlPass);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isPass = rs.getString(1).equals(pass); 
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		} 
		return isPass;
	}
	
	
	/* 게시글 수정 요청시 호출되는 메서드
	 * 게시글을 수정하고 수정하기 버튼을 클릭하면 게시글을 DB에 수정하는 메서드 
	 **/
	public void updateBoard(Board board) {
		
		String fileUpdate = board.getFile1() != null ? ", file1=?" : "";
		String sqlUpdate = "UPDATE jspbbs set title=?, writer=?, content=?,"				
				+ " reg_date=SYSDATE" + fileUpdate + " WHERE no=?";
		
		try {			
			conn = DBManager.getConnection();			
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());			
			pstmt.setString(3, board.getContent());

			// 파일이 업로되었을 때와 그렇지 않을 때를 구분해서 처리
			if(board.getFile1() != null) {
				pstmt.setString(4, board.getFile1());
				pstmt.setInt(5, board.getNo());
			} else {
				pstmt.setInt(4,  board.getNo());
			}
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {			
			DBManager.close(conn, pstmt);
		}
	}
	
	
	/* 게시글 삭제 요청 시 호출되는 메서드 
	 * no에 해당 하는 게시글을 DB에서 삭제하는 메서드 
	 **/
	public void deleteBoard(int no) {
		
		String sqlDelete = "DELETE FROM jspbbs WHERE no=?"; 
		try {			
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sqlDelete);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {			
			DBManager.close(conn, pstmt, rs);
		}
	}
}







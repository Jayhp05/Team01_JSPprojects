package com.jspstudy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jspstudy.bbs.vo.Member;


//회원 테이블에 접근하여 요청을 처리하는 DAO(Data Access Object) 클래스
public class MemberDao {
	
	// 데이터베이스 작업에 필요한 객체 타입으로 변수를 선언	
	// Connection 객체는 DB에 연결해 작업을 수행할 수 있도록 지원하는 객체
	private Connection conn;
	
	// Statement, PreparedStatement 객체는 DB에 쿼리를 발행하는 객체
	private PreparedStatement pstmt;
	
	// ResultSet 객체는 DB에 SELECT 쿼리를 발행한 결과를 저장하는 객체
	private ResultSet rs;
	
	// 회원 로그인을 처리하는 메소드
	public int checkMember(String id, String pass) {
		
		String loginSql = "SELECT pass FROM member WHERE id = ?";
		// -1 아이디 없음, 0 비밀번호 틀림, 1 로그인 성공
		// 1, 2, 3
		int result = -1;
		String password = "";
		
		try {
			// DBCP로부터 Connection을 대여한다.
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(loginSql);
			
			// loginSql 쿼리의 플레이스홀더(?)에 대응하는 데이터를 설정한다.
			pstmt.setString(1, id);
			
			// DB에 쿼리를 전송하여 결과를 ResultSet으로 받는다.
			rs = pstmt.executeQuery();
			
			/* ResultSet에 데이터가 존재하지 않으면 가입된 회원이 아니므로 -1을 반환
			 * 회원 id는 Primary Key로 중복되지 않기 때문에 회원 테이블에서 SELECT한
			 * 결과가 단일 행으로 반환 되므로 if문을 사용해 rs.next()를 호출했다.
			 **/
			if(rs.next()) {
				// ResultSet에 데이터가 존재하면 ID에 대한 비밀번호를 읽어 온다.
				password = rs.getString("pass");
			} else {
				return result;
			}
			
			/* 로그인 요청시 입력한 비밀번호와 회원 테이블에서 SELECT한 결과로
			 * 읽어온 비밀번호가 일치하면 1을 반환 하고 일치하지 않으면 0을 반환 한다.
			 **/
			if(password.equals(pass)) {
				result = 1;				
			} else {
				result = 0;
			}			
		} catch(Exception e) {				
			e.printStackTrace();			
		} finally {		
			DBManager.close(conn, pstmt, rs);
		}		
		return result;
	}
	
	// 회원 가입을 처리하는 메소드
	public void joinMember(Member member) {
		
		String joinSql = 
					"INSERT INTO member VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			// DBManager을 이용해 DBCP로부터 Connection을 대여한다.
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(joinSql);
			
			// joinSql 쿼리의 플레이스홀더(?)에 대응하는 데이터를 설정한다.
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPass());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getMobile());
			pstmt.setString(6, member.getZipcode());
			pstmt.setString(7, member.getAddress1());
			pstmt.setString(8, member.getAddress2());
			pstmt.setString(9, member.getPhone());
			pstmt.setBoolean(10, member.isEmailGet());			
			
			// DB에 쿼리를 전송하여 회원 가입을 완료한다.
			pstmt.executeUpdate();
			
		} catch(Exception e) {			
			e.printStackTrace();
			
		} finally {
			
			// DBManager를 이용해 DB 작업에 사용된 자원을 해제 한다.
			DBManager.close(conn, pstmt);
		}
	}
	
	// 중복 회원을 체크하는 메소드
	public boolean overlapIdCheck(String id) {
		
		String overlapSql = "SELECT id FROM member Where id = ?";	
		boolean result = false;
		
		try{
			// DBManager을 이용해 DBCP로부터 Connection을 대여한다.
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(overlapSql);
			
			// overlapSql 쿼리의 플레이스홀더(?)에 대응하는 데이터를 설정한다.
			pstmt.setString(1, id);
			
			// DB에 쿼리를 전송하여 결과를 ResultSet으로 받는다.
			rs = pstmt.executeQuery();
			
			/* 회원 가입 폼에서 입력된 id를 회원 테이블에서 SELECT 하여 ResultSet에
			 * 데이터가 존재하면 이미 가입된 회원 아이디이므로 true를 반환 한다.
			 **/
			if(rs.next()) {
				result = true;
			}			
			System.out.println("overlapIdCheck(String id)");
			
		} catch(Exception e) {			
			e.printStackTrace();
			
		} finally{
			
			// DBManager를 이용해 DB 작업에 사용된 자원을 해제 한다.
			DBManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	// 회원정보 수정 폼에 출력할 회원의 정보를 반환하는 메서드
	public Member getMember(String id) {
		String selectMember = "SELECT * FROM member WHERE id = ?";
		Member member = null;		
		
		try {
			// DBManager을 이용해 DBCP로부터 Connection을 대여한다.
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(selectMember);
			
			// loginSql 쿼리의 플레이스홀더(?)에 대응하는 데이터를 설정한다.
			pstmt.setString(1, id);
			
			// DB에 쿼리를 전송하여 결과를 ResultSet으로 받는다.
			rs = pstmt.executeQuery();
			
			/* 회원 id는 Primary Key로 중복되지 않기 때문에 회원 테이블에서 SELECT한
			 * 결과가 단일 행으로 반환 되므로 if문을 사용해 rs.next()를 호출했다.
			 * id에 해당하는 회원이 없으면 null이 반환된다.
			 **/
			if(rs.next()) {				
				member = new Member();
				member.setId(rs.getString("id"));
				member.setName(rs.getString("name"));
				member.setPass(rs.getString("pass"));
				member.setEmail(rs.getString("email"));
				member.setMobile(rs.getString("mobile"));
				member.setZipcode(rs.getString("zipcode"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setPhone(rs.getString("phone"));
				member.setEmailGet(rs.getBoolean("email_get"));
				member.setRegDate(rs.getTimestamp("reg_date"));
			}
		} catch(Exception e) {				
			e.printStackTrace();
			
		} finally {			
			// DBManager를 이용해 DB 작업에 사용된 자원을 해제 한다.
			DBManager.close(conn, pstmt, rs);
		}		
		return member;
	}
	
	// 회원 정보 수정을 처리하는 메소드
	public void updateMember(Member member) {
		
		String joinSql = "UPDATE member SET name=?, pass=?, email=?, mobile=?,"
				+ " zipcode=?, address1=?, address2=?, phone=?, email_get=?,"
				+ " reg_date=SYSDATE WHERE id=?";
		
		try {
			// DBManager을 이용해 DBCP로부터 Connection을 대여한다.
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(joinSql);
			
			// joinSql 쿼리의 플레이스홀더(?)에 대응하는 데이터를 설정한다.			
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPass());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getMobile());
			pstmt.setString(5, member.getZipcode());
			pstmt.setString(6, member.getAddress1());
			pstmt.setString(7, member.getAddress2());
			pstmt.setString(8, member.getPhone());
			pstmt.setBoolean(9, member.isEmailGet());			
			pstmt.setString(10, member.getId());
			
			// DB에 쿼리를 전송하여 회원 가입을 완료한다.
			pstmt.executeUpdate();
			
		} catch(Exception e) {			
			e.printStackTrace();
			
		} finally {
			
			// DBManager를 이용해 DB 작업에 사용된 자원을 해제 한다.
			DBManager.close(conn, pstmt);
		}
	}
}

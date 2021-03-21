package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBClose;
import db.DBConnection;
import dto.MemberDto;

public class MemberDao {
	
	private static MemberDao dao = null;
	
	private MemberDao() {
		DBConnection.initConnection();
	}
	
	public static MemberDao getInstance() {
		if(dao == null) {
			dao = new MemberDao();
		}
		return dao;
	}
	
	// 회원가입을 위한 클래스
	public boolean addMember(MemberDto dto) {
		String sql = " INSERT INTO MEMBER(ID, PWD, NAME, EMAIL, AUTH) "
				+ " VALUES(?, ?, ?, ?, 3) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 addMember success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/3 addMember success");
			
			// ? 값을 지정해준다.
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			
			count = psmt.executeUpdate();
			psmt.executeUpdate();
			System.out.println("3/3 addMember success");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}
	
	// 아이디의 중복확인을 위한 클래스
	 public boolean getId(String id) {
		 String sql = " SELECT ID "
					+ " FROM MEMBER "
					+ " WHERE ID = '" + id + "' ";
		 
		 /* 다른 방식
		 String sql = " SELECT COUNT(*) "
					+ " FROM MEMBER "
					+ " WHERE ID = '" + id + "' ";
		 */
		 
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		// boolena findid = false; // 다른 방식
		
		System.out.println(sql);
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 addMember success");
			
			psmt = conn.prepareStatement(sql);
			// psmt.setString(1, id.trim());
			System.out.println("2/3 addMember success");
			
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				count++;
			}
			
		} catch (SQLException e) {
			System.out.println("getId fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return count>0?true:false;
	 }
	 
	 // 로그인을 위한 메소드
	 public boolean checkmember(String id, String pwd) {
		 String sql = " SELECT ID "
					+ " FROM MEMBER "
					+ " WHERE ID = '" + id + "' "
					+ " AND PWD = '" + pwd + "' ";
		// System.out.println(sql);
		 
		 /* 다른 방식
		 String sql = " SELECT COUNT(*) "
					+ " FROM MEMBER "
					+ " WHERE ID = '" + id + "' ";
		 */
		 
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		// boolena findid = false; // 다른 방식
		
		System.out.println(sql);
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 addMember success");
			
			psmt = conn.prepareStatement(sql);
			// psmt.setString(1, id.trim());
			System.out.println("2/3 addMember success");
			
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				count++;
			}
			
		} catch (SQLException e) {
			System.out.println("getId fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return count>0?true:false;
	}
}
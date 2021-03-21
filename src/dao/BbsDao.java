package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

public class BbsDao {
	private static BbsDao dao = new BbsDao();
	
	private BbsDao() {
	}
	
	public static BbsDao getInstance() {
		return dao;
	}
	
	public List<BbsDto> getBbsList() {
		
		String sql = " SELECT SEQ, ID, REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, WDATE, "
				+ " DEL, READCOUNT "
				+ " FROM BBS "
				+ " ORDER BY REF DESC, STEP ASC "; // 최신글을 가장 앞으로 보내는 방법
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsList");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/4 getBbsList");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsList");
			
			while(rs.next()) {
				BbsDto dto = new BbsDto(rs.getInt(1), rs.getString(2), 
										rs.getInt(3), rs.getInt(4), rs.getInt(5),
										rs.getString(6), rs.getString(7), rs.getString(8),
										rs.getInt(9), rs.getInt(10));
				
				list.add(dto);
				
			}
			System.out.println("4/4 getBbsList");
		} catch (SQLException e) {
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
	
	public BbsDto getBbs(int seq) {
		String sql = " SELECT * "
				+ " FROM BBS "
				+ " WHERE SEQ=?";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		BbsDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbs success");
		
			psmt = conn.prepareStatement(sql);
			System.out.println("2/4 getBbs success");
			
			psmt.setInt(1, seq);
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbs success");
			
			if(rs.next()) {				
				dto = new BbsDto(rs.getInt(1), 
								rs.getString(2), 
								rs.getInt(3), 
								rs.getInt(4), 
								rs.getInt(5), 
								rs.getString(6), 
								rs.getString(7), 
								rs.getString(8), 
								rs.getInt(9), 
								rs.getInt(10));
			}
			System.out.println("4/4 getBbs success");
			
		} catch (Exception e) {
			System.out.println("getBbs fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);			
		}
		return dto;
	}
	
	// 게시물 추가
	public boolean addBbslist(BbsDto dto) {
		String sql = " INSERT INTO BBS( SEQ, ID, REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, WDATE, "
				+ " DEL, READCOUNT) "
				+ " VALUES(SEQ_BBS.NEXTVAL, ?, (SELECT NVL(MAX(REF), 0)+1 FROM BBS), 0, 0, ?, ?, SYSDATE, 0, 0) ";
												// seq와 같이 증가된다.
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 addBbslist");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/3 addBbslist");
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());

			count = psmt.executeUpdate();
			System.out.println("3/3 addBbslist");
		} catch (SQLException e) {
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return count>0?true:false;
	}
	
	
	
	public void readcount(int seq) {
		String sql = " UPDATE BBS "
				+ " SET READCOUNT=READCOUNT+1 "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			
			conn = DBConnection.getConnection();
			System.out.println("1/3 readcount");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/3 readcount");
			
			psmt.executeUpdate();
			System.out.println("3/3 readcount");
			
		} catch (SQLException e) {
			System.out.println("readcount fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
	}
	
	public boolean updateBbs(int seq, String title, String content) {
		String sql = " UPDATE BBS SET "
				+ " TITLE=?, CONTENT=? "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 S updateBbs");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, seq);
			
			System.out.println("2/3 S updateBbs");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 S updateBbs");
			
		} catch (Exception e) {			
			e.printStackTrace();
		} finally{
			DBClose.close(conn, psmt, null);			
		}		
		
		return count>0?true:false;
	}
	
	public boolean removeBbs(int seq) {
		
		String sql = " UPDATE BBS "
					+ " SET DEL=1 "
					+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 S deleteBbs");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/3 S deleteBbs");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 S deleteBbs");
			
		} catch (Exception e) {		
			System.out.println("fail deleteBbs");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);			
		}
		
		return count>0?true:false;
	}
	
	// 댓글 시스템을 위한 클래스
	//					   부모글 번호	새로운 답글
	public boolean answer(int seq, BbsDto bbs) {
		// updete
		String sql1 = " UPDATE BBS "
				+ " SET STEP=STEP+1 "
				+ " WHERE REF=(SELECT REF FROM BBS WHERE SEQ=? ) "
				+ " 	AND STEP>(SELECT STEP FROM BBS WHERE SEQ=? ) "; // 부모글에 step보다 큰 댓글들만
		
		
		// insert
		String sql2 = " INSERT INTO BBS "
				+ " (SEQ, ID, "
				+ " REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, WDATE, DEL, READCOUNT) "
				+ " VALUES(SEQ_BBS.NEXTVAL, ?, "
				+ " 		(SELECT REF FROM BBS WHERE SEQ=?), " // REF
				+ "			(SELECT STEP FROM BBS WHERE SEQ=?) + 1, " // STEP
				+ "			(SELECT DEPTH FROM BBS WHERE SEQ=?) + 1, " // DEPTH 
				+ "			?, ?, SYSDATE, 0, 0) ";  
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동커밋 해제
			System.out.println("1/6 success answer");
			
			// update
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			System.out.println("2/6 success answer");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 success answer");
			
			// psmt를 초기화한후 insert를 해줘야한다
			psmt.clearParameters(); // psmt 초기화
			
			// insert
			psmt = conn.prepareStatement(sql2);
			psmt.setString(3, bbs.getId());
			psmt.setInt(4, seq);
			psmt.setInt(5, seq);
			psmt.setInt(6, seq);
			psmt.setString(7, bbs.getTitle());
			psmt.setString(8, bbs.getContent());
			System.out.println("4/6 success answer");
			
			count = psmt.executeUpdate();
			System.out.println("5/6 success answer");
			
			conn.commit(); // 이때 commit이 된다.
			System.out.println("6/6 success answer");
			
		} catch (SQLException e) {
			System.out.println("success fail");
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} finally {
			 try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 
			 DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}
	
	public List<BbsDto> getBbsPagingBbs(String choice, String search, int page) {
		String sql = " SELECT SEQ, ID, REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, WDATE, DEL, READCOUNT "
				+ " FROM ";
		
		sql += " (SELECT ROW_NUMBER()OVER(ORDER BY REF DESC, STEP ASC) AS RNUM, " + 
				"	SEQ, ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT " + 
				"	FROM BBS ";
		
		String sWord = "";
		if(choice.equals("title")) {
			sWord = " WHERE TITLE LIKE '%" + search + "%' ";
		}else if(choice.equals("content")) {
			sWord = " WHERE CONTENT LIKE '%" + search + "%' ";
		}else if(choice.equals("writer")) {
			sWord = " WHERE ID='" + search + "'";
		} 
		sql = sql + sWord;
		
		sql = sql + " ORDER BY REF DESC, STEP ASC) ";
		
		sql = sql + " WHERE RNUM >= ? AND RNUM <= ? ";
		
		int start, end;
		start = 1 + 10 * page; 
		end = 10 + 10 * page;  
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		BbsDto setDto = new BbsDto();
		List<BbsDto> list = new ArrayList<BbsDto>();
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String date = rs.getString("wdate");
				String title = rs.getString("title");
				String content = rs.getString("content");
				
				setDto = new BbsDto(rs.getInt(1), id, rs.getInt(3), rs.getInt(4), rs.getInt(5), title, content, date, rs.getInt(9), rs.getInt(10));
				list.add(setDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
	
	// 올렸던 글의 총수를 반환받기 위한 메소드
	public int getAllBbs(String choice, String search) {
		String sql = " SELECT COUNT(*) FROM BBS ";
		
		String sWord = "";
		if(choice.equals("title")) {
			sWord = " WHERE TITLE LIKE '%" + search + "%' ";
		}else if(choice.equals("content")) {
			sWord = " WHERE CONTENT LIKE '%" + search + "%' ";
		}else if(choice.equals("writer")) {
			sWord = " WHERE ID='" + search + "'";
		}
		
		sql = sql + sWord;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int len = 0; //return 해줄 글의 총수
		
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 getAllBbs success");
			
			psmt= conn.prepareStatement(sql);
			System.out.println("2/3 getAllBbs success");
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				len = rs.getInt(1);
			}
			System.out.println("3/3 getAllBbs success");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return len;
	}
}

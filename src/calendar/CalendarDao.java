package calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;

public class CalendarDao {

	private static CalendarDao dao = new CalendarDao();
	
	private CalendarDao() {
	}
	
	public static CalendarDao getInstance() {
		return dao;
	}
	
	public List<CalendarDto> getCalendalList(String id, String yyyyMM){
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RDATE, WDATE " + 
				 	 " FROM " + 
					 "	(SELECT ROW_NUMBER()OVER(PARTITION BY SUBSTR(RDATE, 1, 8)ORDER BY RDATE ASC) AS RNUM, "+ 
					 "			SEQ, ID, TITLE, CONTENT, RDATE, WDATE " + 
					 " FROM CALENDAR " + 
					 " WHERE ID=? AND SUBSTR(RDATE, 1, 6)=?) " + 
					 " WHERE RNUM BETWEEN 1 AND 5 ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<CalendarDto> list = new ArrayList<CalendarDto>();
		
		try {
			
			conn = DBConnection.getConnection();
			System.out.println("1/4 getCalendalList success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, yyyyMM);
			System.out.println("2/4 getCalendalList success");
			
			rs = psmt.executeQuery(); // 데이터값을 받는 코드
			System.out.println("3/4 getCalendalList success");
			
			while(rs.next()) {
				int i = 1;
				CalendarDto dto = new CalendarDto(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i));
				
				list.add(dto);
			}
			System.out.println("4/4 getCalendalList success");
			
		} catch (SQLException e) {
			System.out.println("getCalendalList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
	
	// 일정 추가
	public boolean addCalendar(CalendarDto cal) {
		String sql = " INSERT INTO CALENDAR(SEQ, ID, TITLE, CONTENT, RDATE, WDATE) "
				+ " VALUES(SEQ_CAL.NEXTVAL, ?, ?, ?, ?, SYSDATE) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
	
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 addCalendar success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, cal.getId());
			psmt.setString(2, cal.getTitle());
			psmt.setString(3, cal.getContent());
			psmt.setString(4, cal.getRdate());
			System.out.println("2/3 addCalendar success");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 addCalendar success");
			
			
		} catch (SQLException e) {
			System.out.println("addCalendar fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}
	
	public CalendarDto getCalendar(int seq) {
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RDATE, WDATE "
				+ " FROM CALENDAR "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		CalendarDto dto = new CalendarDto();
		
	
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getCalendar success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/4 getCalendar success");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getCalendar success");
			
			if(rs.next()) {
				dto = new CalendarDto(seq, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
			}
			System.out.println("4/4 getCalendar success");
		} catch (SQLException e) {
			System.out.println("getCalendar fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return dto;
	}
	
	public boolean updateCalendar(CalendarDto dto) {
		String sql = " UPDATE CALENDAR SET "
				+ " TITLE=?, CONTENT=?, RDATE=? "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 updateCalendar success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/3 updateCalendar success");
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getRdate());
			psmt.setInt(4, dto.getSeq());
			
			count = psmt.executeUpdate();
			System.out.println("3/3 updateCalendar success");
		} catch (SQLException e) {
			System.out.println("updateCalendar fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return count>0?true:false;
	}
	
	public boolean deleteCalendar(int seq) {
		String sql = " DELETE "
				+ " FROM CALENDAR "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 deleteCalendar success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/3 deleteCalendar success");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 deleteCalendar success");
		} catch (SQLException e) {
			System.out.println("deleteCalendar fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	
	public List<CalendarDto> getChoiceCal(String yyyyMMDD){
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RDATE, WDATE " + 
				" FROM CALENDAR " + 
				" WHERE SUBSTR(RDATE, 1, 8)=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<CalendarDto> list = new ArrayList<CalendarDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/4 getChoiceCal success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, yyyyMMDD);
			System.out.println("2/4 getChoiceCal success");
			
			rs = psmt.executeQuery();
			System.out.println("3/4 getChoiceCal success");
			
			while(rs.next()) {
				CalendarDto dto = new CalendarDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				
				list.add(dto);
			}
			System.out.println("4/4 getChoiceCal success");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}
}






















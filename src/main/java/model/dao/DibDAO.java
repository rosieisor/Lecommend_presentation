package model.dao;

import java.sql.ResultSet;
import model.dto.LectureDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DibDAO {

	private JDBCUtil jdbcUtil = null;
	
	public DibDAO() {
		this.jdbcUtil = new JDBCUtil();
	}

	//찜생성
	public int create(String stuId, String lecId) throws SQLException {
		String sql = "INSERT INTO Dib "
					+ "VALUES (DIB_SEQ.NEXT_VAL, ?, ?)";
		
		/*CREATE SEQUENCE DIB_SEQ
	    START WITH 1
	    INCREMENT BY 1;
	    */ //id 자동생성 sequence
		
		Object[] param = new Object[] {stuId, lecId};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil 에 insert문과 매개 변수 설정
						
		try {				
			int result = jdbcUtil.executeUpdate();	// insert 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;			
	}
	
	//찜해제(삭제)
	public int delete(String stuId, String lecId) throws SQLException {
		String sql = "DELETE FROM DIB WHERE stuID = ? AND lecID = ?";	
		
		Object[] param = new Object[] {stuId, lecId};
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}		
		return 0;
	}
	
	//한 강의의 찜 총합(찜할때 보이기)
	public int numOfLecDibs(String lecId) throws SQLException {
		String sql = "SELECT COUNT(dibID) AS numOfLecDibs "
    			+ "FROM DIB "
    			+ "WHERE lecID = ?";
		
		Object[] param = new Object[] {lecId};
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();	
			int dibs = rs.getInt("numOfLecDibs");
			return dibs;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}		
		return 0;
	}	
	
	//한 학생의 찜 갯수(마이페이지에서 확인)
	public int numOfStuDibs(String stuId) throws SQLException {
		String sql = "SELECT COUNT(dibID) AS numOfStuDibs "
	    		+ "FROM DIB "
	    		+ "WHERE stuID = ?";
		
		Object[] param = new Object[] {stuId};
		jdbcUtil.setSqlAndParameters(sql, param);
			
		try {
			ResultSet rs = jdbcUtil.executeQuery();	
			int dibs = rs.getInt("numOfStuDibs");
			return dibs;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	
		}		
		return 0;			
	}
	
	//한 학생의 찜 목록(마이페이지에서 확인)
	public List<LectureDTO> listOfDibs(String stuId) throws SQLException {
		String sql = "SELECT lecID, title, professor, week, lecTime, cno "
				+ "FROM Lecture, Dibs "
				+ "WHERE Dibs.stuID = ? AND Dibs.lecID = Lecture.lecID";
		
		Object[] param = new Object[] {stuId};
		jdbcUtil.setSqlAndParameters(sql, param);
		
		List<LectureDTO> dibList = new ArrayList<LectureDTO>(); 	
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while (rs.next()) {
				LectureDTO lec = new LectureDTO(
						rs.getString("lecID"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cNo")
						);
				dibList.add(lec); 
			}
			return dibList;			
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	
		}		
		return null;	
			
	}
	
}
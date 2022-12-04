package model.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.JDBCUtil;
import model.dto.LectureDTO;
import model.dto.StudentDTO;

/**
 * Student 테이블에 사용자 정보를 추가, 수정, 삭제, 검색 수행 
 */
public class StudentDAO {
	private JDBCUtil jdbcUtil = null;
	
	public StudentDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}
		
	/**
	 * [C] Student 테이블에 새로운 사용자 생성.
	 */
	public int create(StudentDTO user) throws SQLException {
		String sql = "INSERT INTO Student VALUES (?, ?, ?)";		
		Object[] param = new Object[] { user.getStuID(), user.getStuPW(), user.getMajor() };				
		jdbcUtil.setSqlAndParameters(sql, param);	
						
		try {				
			int result = jdbcUtil.executeUpdate();	
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	
		}		
		return 0;			
	}

	/**
	 * [U] 기존의 Student 정보를 수정.
	 */
	public int update(StudentDTO user) throws SQLException {
		String sql = "UPDATE Student "
					+ "SET stupw=?, major=? "
					+ "WHERE stuid=?";
		Object[] param = new Object[] { user.getStuPW(), user.getMajor(), user.getStuID() };				
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

	/**
	 * [D] 사용자 ID에 해당하는 사용자를 삭제.
	 */
	public int remove(String userId) throws SQLException {
		String sql = "DELETE FROM Student WHERE stuid=?";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	

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

	/**
	 * [R] 주어진 사용자 ID에 해당하는 사용자 정보를 데이터베이스에서 찾아 StudentDTO 도메인 클래스에 저장하여 반환.
	 */
	public StudentDTO findUser(String userId) throws SQLException {
        String sql = "SELECT stuid, stupw, major "
        			+ "FROM Student "
        			+ "WHERE userid=? ";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	

		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			if (rs.next()) {					
				StudentDTO user = new StudentDTO(		
					userId,
					rs.getString("stupw"),
					rs.getString("major"));
				return user;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		return null;
	}

	/**
	 * Student가 찜한 Lecture를 검색한 후 현재 페이지와 페이지당 출력할 Lecture 수를 이용하여 List에 저장하여 반환.
	 */
	public List<LectureDTO> findStudentDibList(int currentPage, int countPerPage, String stuid ) throws SQLException {
		String sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
				+ "from dib d join lecture l on d.lecid = l.lecid "
				+ "where stuid = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { stuid} ,					// JDBCUtil에 query문 설정
				ResultSet.TYPE_SCROLL_INSENSITIVE,				// cursor scroll 가능
				ResultSet.CONCUR_READ_ONLY);						
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();					
			int start = ((currentPage-1) * countPerPage) + 1;	
			if ((start >= 0) && rs.absolute(start)) {			
				List<LectureDTO> lectureList = new ArrayList<LectureDTO>();	
				do {
					LectureDTO lecture = new LectureDTO(
							rs.getString("l.lecid"), 
							rs.getString("l.title"),
							rs.getString("l.professor"), 
							rs.getString("l.loc"), 
							rs.getString("l.week"),
							rs.getString("l.lectime"),
							rs.getInt("l.cno"));
					lectureList.add(lecture);						
				} while ((rs.next()) && (--countPerPage > 0));		
				return lectureList;							
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		return null;
	}
		
	/**
	 * [R] Student가 찜한 Lecture 정보를 List에 저장 및 반환
	 */
	public List<LectureDTO> findStudentDibList(String stuid) throws SQLException {
		String sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
				+ "from dib d join lecture l on d.lecid = l.lecid "
				+ "where stuid = ?";                      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {stuid});	
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			List<LectureDTO> lectureList = new ArrayList<LectureDTO>();
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("l.lecid"), 
						rs.getString("l.title"),
						rs.getString("l.professor"), 
						rs.getString("l.loc"), 
						rs.getString("l.week"),
						rs.getString("l.lectime"),
						rs.getInt("l.cno"));
				lectureList.add(lecture);			
			}		
			return lectureList;					
				
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		return null;
	}
	
	/**
	 * [R] Student가 찜한 강의 수를 반환
	 */
	public int getNumberOfUsersInDib(String userId) {
		String sql = "SELECT COUNT(stuid) as cnt FROM DIB "
     				+ "WHERE stuid = ?";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			if (rs.next()) {
				return rs.getInt(1);
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		return 0;
	}

	
	/**
	 * [R] 주어진 사용자 ID에 해당하는 사용자가 존재하는지 검사 
	 */
	public boolean existingUser(String userId) throws SQLException {
		String sql = "SELECT count(*) FROM Student WHERE stuid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	

		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		return false;
	}

}
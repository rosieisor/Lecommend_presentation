package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.LectureDTO;

public class StatusDAO {
	
	private JDBCUtil jdbcUtil = null;

	public StatusDAO() {
		this.jdbcUtil = new JDBCUtil();
	}
	
	//수강여부 생성
	public int create(String stuId, String lecId) throws SQLException {
		String sql = "INSERT INTO Status "
					+ "VALUES (Status_SEQ.NEXT_VAL, ?, ?)";
		
		/*CREATE SEQUENCE Status_SEQ
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

	//수강여부 해제(삭제) : 잘못 선택했을 수도 있으므로 삭제함
	public int delete(String stuId, String lecId) throws SQLException {
		String sql = "DELETE FROM STATUS WHERE stuID = ? AND lecID = ?";	
		
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
		
	//수강여부 체크. 수강했다면 lecID 반환 미수강 상태면 0반환. 
	//검색된 강의들 중 수강여부를 체크할 수 있다. 또는 인기강의들의 수강여부를 체크할 수 있다. 안쓸 수도 있지만 일단 넣음
	public int findRegistered(String stuId, String lecId) throws SQLException {
		String sql = "SELECT lecID FROM STATUS WHERE STUID = ? AND lecId = ?";
		
		Object[] param = new Object[] {stuId, lecId};				
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {				
			ResultSet rs = jdbcUtil.executeQuery();	
			int result = rs.getInt("lecID");
			
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
	
	//수강한 강의 목록. 학생이 수강한 강의를 알면되므로 매개변수로 stuID만 받는다.
	//마이페이지 UI를 하나 더 만들어 수강한 강의 목록 볼 수 있게 한다.
	public List<LectureDTO> listOfRegistered(String stuId) throws SQLException {
		String sql = "SELECT lecID, title, professor, week, lecTime, loc "
				+ "FROM Lecture, Status "
				+ "WHERE Status.stuID = ? AND Status.lecID = Lecture.lecID";
		
		Object[] param = new Object[] {stuId};
		jdbcUtil.setSqlAndParameters(sql, param);
		
		List<LectureDTO> registeredList = new ArrayList<LectureDTO>(); 	
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
						rs.getInt("cno")
						);
				registeredList.add(lec); 
			}
			return registeredList;			
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

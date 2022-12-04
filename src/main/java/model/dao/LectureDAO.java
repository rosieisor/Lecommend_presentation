package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dto.LectureDTO;


public class LectureDAO {
	private JDBCUtil jdbcUtil = null;

	public LectureDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	/**
	 * [C] Lecture 테이블에 새로운 lecture 생성
	 */
	public int create(LectureDTO lec) throws SQLException {
		String sql = "INSERT INTO LECTURE "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		Object[] param = new Object[] { lec.getLecID(), lec.getTitle(), lec.getProfessor(),
				lec.getLoc(), lec.getWeek(), lec.getLecTime(), lec.getcNo() };				
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
	 * [C] Lecture 테이블에 새로운 lecture 생성
	 */
	public int createDetail(LectureDTO lec) throws SQLException {
		String sql = "INSERT INTO OPTIONALINFO "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Object[] param = new Object[] { lec.getLecID(), lec.getOccupancy(), lec.getCredit(),
				lec.getOnOff(), lec.getLoc(), lec.getWeek(), lec.getLecTime(), lec.getLecType(),
				lec.getInterest(), lec.getExamType() };				
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
	 * [U] 기존의 Lecture 정보를 수정
	 */
	public int update(LectureDTO lec) throws SQLException {
		String sql = "UPDATE LECTURE "
					+ "SET lecId = ?, title = ?, professor = ?, loc = ?, week = ?, lecTime = ?, cNo = ? "
					+ "WHERE lecId = ?";
		Object[] param = new Object[] {lec.getLecID(), lec.getTitle(), lec.getProfessor(), 
				lec.getLoc(), lec.getWeek(), lec.getLecTime(), lec.getLecID()};				
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
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}
	
	/**
	 * [U] 기존의 Lecture 부가 정보를 수정
	 */
	public int updateDetail(LectureDTO lec) throws SQLException {
		String sql = "UPDATE optionalInfo "
					+ "SET lecId = ?, occupancy = ?, credit = ?, onOff = ?, loc = ?, week = ?, lecTime = ?, "
					+ "lectype = ?, interest = ?, examType = ? "
					+ "WHERE lecId = ?";
		Object[] param = new Object[] {lec.getLecID(), lec.getOccupancy(), lec.getCredit(), 
				lec.getOnOff(), lec.getLoc(), lec.getWeek(), lec.getLecTime(), lec.getLecType(), lec.getInterest(), lec.getExamType(), lec.getLecID()};				
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
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}
	
	/**
	 * [D] 주어진 Lecture ID에 해당하는 Lecture 정보를 삭제.
	 */
	public int delete(String lecId) throws SQLException {
		String sql = "DELETE FROM Lecture "
					+ "WHERE lecId = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { lecId });
		try {				
			int result = jdbcUtil.executeUpdate();	
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}
	
	/**
	 * [D] 주어진 Lecture ID에 해당하는 Lecture 부가 정보를 삭제.
	 */
	public int deleteDetail(String lecId) throws SQLException {
		String sql = "DELETE FROM optionalInfo "
					+ "WHERE lecId = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { lecId });
		try {				
			int result = jdbcUtil.executeUpdate();	
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}
	
	/**
	 * [R] 전체 Lecture 정보를 검색하여 List에 저장 및 반환
	 */
	public List<LectureDTO> findAllLectures() throws SQLException {
		String sql = "select lecid, title, professor, loc, week, lectime, cno "
				+ "from lecture";
		
		jdbcUtil.setSqlAndParameters(sql, null);
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> lectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cno"));
				lectureList.add(lecture); 
			}
			return lectureList;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		} return null;		
	}
	
	/**
	 * [R] 주어진 Lecture ID에 해당하는 Lecture 정보를 데이터베이스에서 찾아 Lecture 도메인 클래스에 저장하여 반환.
	 */
	public LectureDTO findLecture(String lecId) throws SQLException {
		String sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
				+ "from lecture "
				+ "where lecid = ?";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] { lecId });
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			LectureDTO lecture = new LectureDTO(); 
			while (rs.next()) {
				lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cno"));
			}
			return lecture;
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

	/**
	 * [R] 찜 수가 많은 Lecture 5개를 List에 저장 및 반환
	 */
	public List<LectureDTO> findLecturesTop5() throws SQLException {
		String sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
				+ "	from lecture l join (select lecid, count(lecid) "
				+ "	from dib group by lecid) sub on l.lecid = sub.lecid"
				+ "	where rownum < 6";
		
		jdbcUtil.setSqlAndParameters(sql, null);
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> lectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cno"));
				lectureList.add(lecture); 
			}
			return lectureList;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return null;
	}
	
	/**
	 * [R] Student가 속한 학과에서 찜 수가 많은 Lecture 5개를 List에 저장 및 반환
	 */
	public List<LectureDTO> findLecturesTop5InSameDept(String major) throws SQLException {
		String sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
				+ "from lecture l join (select d.lecid, count(s.major) "
				+ "from dib d join student s on d.stuid = s.stuid "
				+ "where s.major = ? "
				+ "group by d.lecid) sub on l.lecid = sub.lecid "
				+ "where rownum < 6";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] { major });
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> lectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cno"));
				lectureList.add(lecture); 
			}
			return lectureList;
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

	/**
	 * [R] Student가 찜한 강의를 찜한 다른 사용자들의 찜 List 중 가장 많이 찜한 Lecture를 List에 저장 및 반환
	 */
	public String[] findLecturesOtherStudentDib(String stuId, String lecId) throws SQLException {
		String sql = "select dib2.lecid "
				+ "from (select * from dib where stuid ! = ? and lecid = ?) dib1, dib dib2 "
				+ "where dib1.stuid = dib2.stuid and dib1.lecid != dib2.lecid "
				+ "group by dib2.lecid";
		Object[] param = new Object[] { stuId, lecId };
		jdbcUtil.setSqlAndParameters(sql, param);
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			String[] lecIdList = new String[5];
			int i = 0;
			while (rs.next()) {
				lecIdList[i] = rs.getString("dib2.lecid");
				i++;
			}
			return lecIdList;
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

	/**
	 * [R] Student가 찜한 강의 개수를 반환
	 */
	public int findCountOfStuentDibs(String stuId) throws SQLException {
		String sql = "select count(stuid) as count "
				+ "from dib "
				+ "where stuid = ?";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] { stuId });
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			int cnt = 0;
			while (rs.next()) {
				cnt = rs.getInt("count");
			}
			return cnt;
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
	 * [R] 키워드로 검색된 Lecture를 List에 저장 및 반환 // 수강했던 강의 포함해서 검색
	 */
	public List<LectureDTO> findLectureByKeyword(String loc, String week, String lecTime, int occupancy, int credit, String onOff, String lecType, String interest, String examType) throws SQLException {
		String sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
				+ "from lecture l join optionalinfo o on l.lecid = o.lecid "
				+ "where o.loc LIKE '" + loc + "%' OR "
				+ "o.week = ? OR "
				+ "o.lecTime LIKE '%" + lecTime + "%' OR "
				+ "o.occupancy between ? and ? OR "
				+ "o.credit = ? OR "
				+ "o.onOff = ? OR "
				+ "o.lecType = ? OR "
				+ "o.interest = ? OR "
				+ "o.examType = ?";
		
		Object[] param = new Object[] { week, occupancy-20, occupancy+20, credit, onOff, lecType, interest, examType};				
		
		jdbcUtil.setSqlAndParameters(sql, param);
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> lectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cno"));
				lectureList.add(lecture); 
			}
			return lectureList;
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
	
	/**
	* [R] 수강했던 강의를 제외하고 키워드로 검색된 Lecture를 List에 저장 및 반환
	 */
	public List<LectureDTO> findLectureByKeywordAndStatus(String stuID, String loc, String week, String lecTime, int occupancy, int credit, String onOff, String lecType, String interest, String examType) throws SQLException {
		String sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
				+ "from lecture l, dib d "
				+ "where (d.stuid = ? and l.lecid != d.lecid) AND loc = ? OR "
				+ "week = ? OR "
				+ "lecTime = ? OR "
				+ "occupancy = ? OR "
				+ "credit = ? OR "
				+ "onOff = ? OR "
				+ "lecType = ? OR "
				+ "interest = ? OR "
				+ "examType = ?";
		
		Object[] param = new Object[] { stuID, loc, week, lecTime, occupancy, credit, onOff, lecType, interest, examType};				
		jdbcUtil.setSqlAndParameters(sql, param);
		try {				
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> lectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cno"));
				lectureList.add(lecture); 
			}
			return lectureList;
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
	
	/**
	 * [R] 주어진 Lecture ID에 해당하는 Lecture가 존재하는지 검사 
	 */
	public boolean existingLecture(String lecId) throws SQLException {
		String sql = "SELECT count(*) FROM Lecture WHERE lecid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {lecId});	

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
	
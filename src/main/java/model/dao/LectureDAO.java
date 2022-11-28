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
	 * 찜한 횟수가 가장 높은 강의 5개 검색
	 */
	public List<LectureDTO> findHotLecture() throws SQLException {
		String sql = "select * "
				+ "from lecture l join (select lecid, count(lecid) "
				+ "from dib group by lecid) sub on l.lecid = sub.lecid "
				+ "where rownum <= 5";
		
		jdbcUtil.setSqlAndParameters(sql, null);
		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> LectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lec = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"),
						rs.getInt("cno"));
				LectureList.add(lec); 
			}
			return LectureList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}
	
	/**
	 * Student가 속한 학과에서 찜한 횟수가 가장 높은 강의 5개 검색
	 */
	public List<LectureDTO> findHotLectureInSamedept(String major) throws SQLException {
		String sql = "select * "
				+ "from lecture l join (select d.lecid, count(s.major) "
				+ "from dib d join student s on d.stuid = s.stuid "
				+ "where s.major = ? " 
				+ "group by d.lecid) sub on l.lecid = sub.lecid "
				+ "where rownum <= 5";

		jdbcUtil.setSqlAndParameters(sql, new Object[] { major });
		
		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> LectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lec = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"));
				LectureList.add(lec); 
			}
			return LectureList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	/**
	 * Student가 찜한 강의를 찜한 다른 사용자들의 찜 리스트 중 가장 많이 찜한 강의 검색
	 */
	public List<LectureDTO> findOtherStudentDibLecture(String stuid) throws SQLException {
		String sql = "select * "
				+ "from lecture l join (select d2.lecid, count(d2.lecid) "
				+ "from dib d1, dib d2 "
				+ "where d1.stuid != d2.stuid and d1.stuid = ? "
				+ "and d1.lecid != d2.lecid "
				+ "group by d2.lecid) sub on l.lecid = sub.lecid "
				+ "where rownum <= 10";

		jdbcUtil.setSqlAndParameters(sql, new Object[] { stuid });
		
		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> LectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lec = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"));
				LectureList.add(lec); 
			}
			return LectureList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}
	
	/**
	 * Student가 찜한 강의 개수를 반환 
	 */
	public int findCountOfStuendDibs (String stuid) throws SQLException{
		int count = 0;
		String sql = "select count(stuid) as count "
					+ "from dib "
					+ "where stuid = ?";
			
		jdbcUtil.setSqlAndParameters(sql, new Object[] { stuid });			
		
		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			while (rs.next()) {
				count = rs.getInt("count"); 
			}
			return count;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); 
		}
		return 0;
	}
	
	/**
	 * 수강 여부과 키워드 검색에 따른 강의 리스트 반환 /  매개변수가 0이면 과거 들었던 강의 포함 O, 1이면 과거 들었던 강의 포함 X
	 */
	public List<LectureDTO> searchLectureWhetherToStatusWithKeyword(int status_result, LectureDTO lec, String stuid) throws SQLException {
		
		String sql;
		
		if (status_result == 0) { // 과거 수강한 강의 포함한 강의 리스트 반환
			sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
					+ "from lecture  l "
					+ "left outer join (select * from dib where stuid = ?) d "
					+ "on l.lecid = d.lecid "
					+ "where (l.loc = ? OR "
					+ "l.week = ? OR "
					+ "l.lectime = ? OR "
					+ "l.occupancy = ? OR "
					+ "l.credit = ? OR "
					+ "l.onoff = ? OR "
					+ "l.lectype = ? OR "
					+ "l.interest = ? OR "
					+ "l.examtype = ?)";		
		} else { // 과거 수강한 강의 포함하지 않고 강의 리스트 반환
			sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
					+ "from lecture  l "
					+ "left outer join (select * from dib where stuid = ?) d "
					+ "on l.lecid = d.lecid "
					+ "where d.dibid IS null "
					+ "and (l.loc = ? OR "
					+ "l.week = ? OR "
					+ "l.lectime = ? OR "
					+ "l.occupancy = ? OR "
					+ "l.credit = ? OR "
					+ "l.onoff = ? OR "
					+ "l.lectype = ? OR "
					+ "l.interest = ? OR "
					+ "l.examtype = ?)";
		}
		
		Object[] param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType() };	
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> LectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"));
				LectureList.add(lecture); 
			}
			return LectureList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}
		return null;
	}

	/**
	 * 수강 여부와 우선순위 키워드 검색에 따른 강의 리스트 반환 
	 */
	public List<LectureDTO> searchLectureWhetherToStatusWithKeywordAndPriority(int status_result, LectureDTO lec, String stuid, int priority) throws SQLException {
		
		String sql, subSql;
		Object[] param = null;
		
		if (status_result == 0) { // 과거 수강한 강의 포함한 강의 리스트 반환
			sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
					+ "from lecture  l "
					+ "left outer join (select * from dib where stuid = ?) d "
					+ "on l.lecid = d.lecid "
					+ "where (l.loc = ? OR "
					+ "l.week = ? OR "
					+ "l.lectime = ? OR "
					+ "l.occupancy = ? OR "
					+ "l.credit = ? OR "
					+ "l.onoff = ? OR "
					+ "l.lectype = ? OR "
					+ "l.interest = ? OR "
					+ "l.examtype = ?)";		
		} else { // 과거 수강한 강의 포함하지 않고 강의 리스트 반환
			sql = "select l.lecid, l.title, l.professor, l.loc, l.week, l.lectime, l.cno "
					+ "from lecture  l "
					+ "left outer join (select * from dib where stuid = ?) d "
					+ "on l.lecid = d.lecid "
					+ "where d.dibid IS null "
					+ "and (l.loc = ? OR "
					+ "l.week = ? OR "
					+ "l.lectime = ? OR "
					+ "l.occupancy = ? OR "
					+ "l.credit = ? OR "
					+ "l.onoff = ? OR "
					+ "l.lectype = ? OR "
					+ "l.interest = ? OR "
					+ "l.examtype = ?)";
		}
		
		
		switch(priority) {
		case 0: // 온오프
			subSql = " order by case when onoff IN ('?') Then 0 "
					+ "else 1 End, onoff";				
			sql = sql + subSql;				
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getOnOff() };				
			break;
		case 1: // 강의실
			subSql = " order by case when loc IN ('?') Then 0 "
					+ "else 1 End, loc";				
			sql = sql + subSql;				
			String loc = lec.getLoc();
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), loc.substring(0, 1) };
			break;
		case 2: // 강의 형식
			subSql = " order by case when lectype IN ('?') Then 0 "
					+ "else 1 End, lectype";				
			sql = sql + subSql;				
			
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getLecType() };
			break;
		case 3: // 관심사
			subSql = " order by case when interest IN ('?') Then 0 "
					+ "else 1 End, interest";				
			sql = sql + subSql;								
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getInterest() };			
			break;
		case 4: // 시간대
			subSql = " order by case when lectime IN ('?') Then 0 "
					+ "else 1 End, lectime";				
			sql = sql + subSql;				
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getLecTime() };
			break;
		case 5: // 시험 형식
			subSql = " order by case when exametype IN ('?') Then 0 "
					+ "else 1 End, exametype";				
			sql = sql + subSql;				
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getExamType() };
			break;
		case 6: // 요일
			subSql = " order by case when week IN ('?') Then 0 "
					+ "else 1 End, week";				
			sql = sql + subSql;				
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getWeek() };
			break;
		case 7: // 수용인원
			int start = lec.getOccupancy() - 20;
			int end = lec.getOccupancy() + 20;
			subSql = " order by case when between ? and ? Then 0 "
					+ "else 1 End, occupancy";				
			sql = sql + subSql;				
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getCredit(), start, end };
			break;
		case 8 : // 학점
			subSql = " order by case when credit = ? Then 0 "
					+ "else 1 End, credit";				
			sql = sql + subSql;				
			param = new Object[] { stuid, lec.getLoc(), lec.getWeek(), lec.getLecTime(),
						lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
						lec.getInterest(), lec.getExamType(), lec.getCredit() };
			break;
		}
	
		jdbcUtil.setSqlAndParameters(sql, param);
				
		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> LectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"));
				LectureList.add(lecture); 
			}
			return LectureList;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // resource 반환
		}
		return null;
	}

	
	
	
	
	/**
	 * 키워드에 따른 강의 검색하여 리스트 반환 (우선순위 사용 X)
	 
	public List<LectureDTO> searchLectureWithKeyword(LectureDTO lec) {
		String sql = "SELECT lecid, title, professor, loc, week, lectime, occupancy, credit, onoff, lectype, interest, examtype "
    			+ "FROM Lecture "
    			+ "WHERE loc = ? OR "
    			+ "week = ? + OR "
    			+ "lectime = ? + OR "
    			+ "occupancy = ? + OR "
    			+ "credit = ? + OR "
    			+ "onoff = ? + OR "
    			+ "lectype = ? + OR "
    			+ "interest = ? + OR "
    			+ "examtype = ? + OR "
    			+ "week = ?";

		Object[] param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
				lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
				lec.getInterest(), lec.getExamType() };
		
		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> LectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"));
				LectureList.add(lecture); 
			}
			return LectureList;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return null;
	}
	*/
	
	/**
	 * 키워드에 따른 강의 검색하여 리스트 반환 (우선순위 사용)
	 
	public List<LectureDTO> searchLectureWithKeywordAndPriority(LectureDTO lec, int priority) {
		String sql = "SELECT lecid, title, professor, loc, week, lectime, occupancy, credit, onoff, lectype, interest, examtype "
    			+ "FROM Lecture "
    			+ "WHERE loc = ? OR "
    			+ "week = ? + OR "
    			+ "lectime = ? + OR "
    			+ "occupancy = ? + OR "
    			+ "credit = ? + OR "
    			+ "onoff = ? + OR "
    			+ "lectype = ? + OR "
    			+ "interest = ? + OR "
    			+ "examtype = ? + OR "
    			+ "week = ?";

		String subSql;
		Object[] param = null;
		
		switch(priority) {
			case 0: // 온오프
				subSql = " order by case when onoff IN ('?') Then 0 "
						+ "else 1 End, onoff";				
				sql = sql + subSql;				
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), lec.getOnOff() };				
				break;
			case 1: // 강의실
				subSql = " order by case when loc IN ('?') Then 0 "
						+ "else 1 End, loc";				
				sql = sql + subSql;				
				
				String loc = lec.getLoc();
				
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), loc.substring(0, 1) };
				break;
			case 2: // 강의 형식
				subSql = " order by case when lectype IN ('?') Then 0 "
						+ "else 1 End, lectype";				
				sql = sql + subSql;				
				
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), lec.getLecType() };
				break;
			case 3: // 관심사
				subSql = " order by case when interest IN ('?') Then 0 "
						+ "else 1 End, interest";				
				sql = sql + subSql;								
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), lec.getInterest() };			
				break;
			case 4: // 시간대
				subSql = " order by case when lectime IN ('?') Then 0 "
						+ "else 1 End, lectime";				
				sql = sql + subSql;				
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), lec.getLecTime() };
				break;
			case 5: // 시험 형식
				subSql = " order by case when exametype IN ('?') Then 0 "
						+ "else 1 End, exametype";				
				sql = sql + subSql;				
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), lec.getExamType() };
				break;
			case 6: // 요일
				subSql = " order by case when week IN ('?') Then 0 "
						+ "else 1 End, week";				
				sql = sql + subSql;				
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), lec.getWeek() };
				break;
			case 7: // 수용인원
			
				
				break;
			case 8 : // 학점
				subSql = " order by case when credit = ? Then 0 "
						+ "else 1 End, credit";				
				sql = sql + subSql;				
				param = new Object[] { lec.getLoc(), lec.getWeek(), lec.getLecTime(),
							lec.getOccupancy(), lec.getCredit(), lec.getOnOff(), lec.getLecType(),
							lec.getInterest(), lec.getExamType(), lec.getCredit() };
				
				break;
		}
		
		
		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery(); 
			List<LectureDTO> LectureList = new ArrayList<LectureDTO>(); 
			while (rs.next()) {
				LectureDTO lecture = new LectureDTO(
						rs.getString("lecid"), 
						rs.getString("title"),
						rs.getString("professor"), 
						rs.getString("loc"), 
						rs.getString("week"),
						rs.getString("lectime"));
				LectureList.add(lecture); 
			}
			return LectureList;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return null;
	}
	*/
}
	
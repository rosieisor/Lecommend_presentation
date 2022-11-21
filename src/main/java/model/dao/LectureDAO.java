package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.dto.Lecture;
import model.dto.LectureDTO;
import model.dto.Like;

public class LectureDAO {
	private JDBCUtil jdbcUtil = null;
	
	// JDBC 객체 생성
	public LectureDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	// [C] 새로운 강의 생성
	public int create(LectureDTO lecture) throws SQLException {
		String sql = "INSERT INTO LECTURE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] param = new Object[] {
				lecture.getLectureID(), lecture.getTitle(), lecture.getClassroom(), lecture.getProfessor(), lecture.getOccupancy(), 
				lecture.getOnOff(), lecture.getRegistered(), lecture.getClassroomLocation(), lecture.getLectureForm(), lecture.getInterest(), 
				lecture.getClassTime(), lecture.getExamForm(), lecture.getWeek(), lecture.getLikes()
		};
		
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
	
	// [R] 해당 ID에 해당하는 강의를 도메인 클래스에 저장하여 반환
	public Lecture findLecture(int lectureID) throws SQLException {
		String sql ="SELECT * FROM LECTURE WHERE lectureID=?";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {lectureID});
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		
			if (rs.next()) {						
				Lecture lecture = new Lecture(lectureID, rs.getString("title"), rs.getString("classroom"), rs.getString("professor"),
												rs.getInt("occupancy"), rs.getInt("onOff"), rs.getInt("registered"), rs.getInt("classroomLocation"),rs.getInt("lectureForm"), 
												rs.getInt("interest"), rs.getInt("classTime"), rs.getInt("examForm"), rs.getInt("week"), rs.getInt("likes"));
				return lecture;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		
		return null;
	}
}

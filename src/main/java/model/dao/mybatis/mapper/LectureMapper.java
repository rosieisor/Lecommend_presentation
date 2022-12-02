package model.dao.mybatis.mapper;

import java.util.List;

import model.dto.LectureDTO;

public interface LectureMapper {
	
	public int insertLecture(LectureDTO lec);
	
	public int insertLectureDetail(LectureDTO lec);
	
	public int updateLecture(LectureDTO lec);
	
	public int updateLectureDetail(LectureDTO lec);
	
	public int deleteLecture(String lecID);
	
	public int deleteLectureDetail(String lecID);
	
	public List<LectureDTO> selectAllLectures();
	
	public LectureDTO selectLectureByLecId(String lecID);
	
	public List<LectureDTO> selectLecturesTop5();
	
	public List<LectureDTO> selectLecturesTop5InSameDept(String major);
	
	public String[] selectLecturesOtherStudentDib(String stuID, String lecId);
	
	public int selectCountOfStuentDibs(String stuID);
	
	public List<LectureDTO> selectLectureByKeyword(String loc, String week, String lecTime, int occupancy, int credit, String onOff, String lecType, String interest, String examType);
	
	public List<LectureDTO> selectLectureByKeywordAndStatus(String stuID, String loc, String week, String lecTime, int occupancy, int credit, String onOff, String lecType, String interest, String examType);
	
}


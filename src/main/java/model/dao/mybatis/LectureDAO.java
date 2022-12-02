package model.dao.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.dao.mybatis.mapper.LectureMapper;
import model.dto.LectureDTO;

public class LectureDAO {

	private SqlSessionFactory sqlSessionFactory;

	public LectureDAO() {
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * [C] Lecture 테이블에 새로운 lecture 생성
	 */
	public int create(LectureDTO lec) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(LectureMapper.class).insertLecture(lec);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	 * [C] Lecture 테이블에 lecture의 부가 정보 생성
	 */
	public int createDetail(LectureDTO lec) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(LectureMapper.class).insertLectureDetail(lec);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [U] 기존의 Lecture 정보를 수정
	 */
	public int update(LectureDTO lec) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(LectureMapper.class).updateLecture(lec);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	 * [U] 기존의 Lecture 부가 정보를 수정
	 */
	public int updateDetail(LectureDTO lec) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(LectureMapper.class).updateLectureDetail(lec);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [D] 주어진 Lecture ID에 해당하는 Lecture 정보를 삭제.
	 */
	public int remove(String lecID) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(LectureMapper.class).deleteLecture(lecID);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [D] 주어진 Lecture ID에 해당하는 Lecture 부가 정보를 삭제.
	 */
	public int removeDetail(String lecID) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(LectureMapper.class).deleteLectureDetail(lecID);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [R] 전체 Lecture 정보를 검색하여 List에 저장 및 반환
	 */
	public List<LectureDTO> findLectureList() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(LectureMapper.class).selectAllLectures();
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [R] 주어진 Lecture ID에 해당하는 Lecture 정보를 데이터베이스에서 찾아 Lecture 도메인 클래스에 저장하여 반환.
	 */
	public LectureDTO findLecture(String lecID) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(LectureMapper.class).selectLectureByLecId(lecID);
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [R] 찜 수가 많은 Lecture 5개를 List에 저장 및 반환
	 */
	public List<LectureDTO> findLecturesTop5() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(LectureMapper.class).selectLecturesTop5();
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [R] Student가 속한 학과에서 찜 수가 많은 Lecture 5개를 List에 저장 및 반환
	 */
	public List<LectureDTO> findLecturesTop5InSameDept(String major) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(LectureMapper.class).selectLecturesTop5InSameDept(major);
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [R] Student가 찜한 강의를 찜한 다른 사용자들의 찜 List 중 가장 많이 찜한 Lecture를 ArrayList에 저장 및 반환
	 */
	public String[] findLecturesOtherStudentDib(String stuID, String lecID) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(LectureMapper.class).selectLecturesOtherStudentDib(stuID, lecID);
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * [R] Student가 찜한 강의 개수를 반환
	 */
	public int findCountOfStuentDibs(String stuID) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int result = sqlSession.getMapper(LectureMapper.class).selectCountOfStuentDibs(stuID);
			if (result > 0) {
				sqlSession.commit();
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	 * [R] 키워드로 검색된 Lecture를 List에 저장 및 반환 // 수강했던 강의 포함해서 검색
	 */
	public List<LectureDTO> findLectureByKeyword(String loc, String week, String lecTime, int occupancy, int credit, String onOff, String lecType, String interest, String examType) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(LectureMapper.class).selectLectureByKeyword(loc, week, lecTime, occupancy, credit, onOff, lecType, interest, examType);
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	* [R] 수강했던 강의를 제외하고 키워드로 검색된 Lecture를 List에 저장 및 반환
	 */
	public List<LectureDTO> findLectureByKeywordAndStatus(String stuID, String loc, String week, String lecTime, int occupancy, int credit, String onOff, String lecType, String interest, String examType) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			return sqlSession.getMapper(LectureMapper.class).selectLectureByKeywordAndStatus(stuID, loc, week, lecTime, occupancy, credit, onOff, lecType, interest, examType);
		} finally {
			sqlSession.close();
		}
	}
	
	
	
}

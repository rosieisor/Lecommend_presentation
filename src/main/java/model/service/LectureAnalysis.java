package model.service;

import java.util.ArrayList;
import java.util.List;

import model.dao.LectureDAO;
import model.dto.LectureDTO;

public class LectureAnalysis {
	private LectureDAO lecdao;

	public LectureAnalysis() {
	}

	public LectureAnalysis(LectureDAO dao) {
		super();
		this.lecdao = dao;
	}
	

		public List<LectureDTO> recommendLectures(String stuId, String lecId) throws Exception {
			int i = 0;
			String[] dibList = lecdao.findLecturesOtherStudentDib(stuId, lecId);
			
			List<LectureDTO> recommendLecList = new ArrayList<LectureDTO>();
			while(i < 6) {
				LectureDTO lec = lecdao.findLecture(dibList[i]);
				recommendLecList.add(lec);
			}

			return recommendLecList;
		}
}

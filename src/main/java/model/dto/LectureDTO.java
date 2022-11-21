package model.dto;

public class LectureDTO {
	// 기본정보
	private String lecID;
	private String title;
	private String professor;
	private String loc;
	private String week;
	private String lecTime;
	
	// 검색에 필요한 정보
	private int occupancy;
	private int credit;
	private String onOff;
	private String lecType;
	private String interest;
	private String examType;
	
	// default 생성자
	public LectureDTO() {}

	// 기본정보만 초기화하는 생성자
	public LectureDTO(String lecID, String title, String professor, String loc, String week, String lecTime) {
		super();
		this.lecID = lecID;
		this.title = title;
		this.professor = professor;
		this.loc = loc;
		this.week = week;
		this.lecTime = lecTime;
	}

	// 옵셔널 정보까지 초기화하는 생성자
	public LectureDTO(String lecID, String title, String professor, String loc, String week, String lecTime,
			int occupancy, int credit, String onOff, String lecType, String interest, String examType) {
		super();
		this.lecID = lecID;
		this.title = title;
		this.professor = professor;
		this.loc = loc;
		this.week = week;
		this.lecTime = lecTime;
		this.occupancy = occupancy;
		this.credit = credit;
		this.onOff = onOff;
		this.lecType = lecType;
		this.interest = interest;
		this.examType = examType;
	}

	public String getLecID() {
		return lecID;
	}
	
	public void setLecID(String lecID) {
		this.lecID = lecID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getProfessor() {
		return professor;
	}
	
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	public String getWeek() {
		return week;
	}
	
	public void setWeek(String week) {
		this.week = week;
	}
	
	public String getLecTime() {
		return lecTime;
	}
	
	public void setLecTime(String lecTime) {
		this.lecTime = lecTime;
	}
	
	public int getOccupancy() {
		return occupancy;
	}
	
	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}
	
	public int getCredit() {
		return credit;
	}
	
	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	public String getOnOff() {
		return onOff;
	}
	
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}
	
	public String getLecType() {
		return lecType;
	}
	
	public void setLecType(String lecType) {
		this.lecType = lecType;
	}
	
	public String getInterest() {
		return interest;
	}
	
	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	public String getExamType() {
		return examType;
	}
	
	public void setExamType(String examType) {
		this.examType = examType;
	}

	@Override
	public String toString() {
		return "LectureDTO [lecID=" + lecID + ", title=" + title + ", professor=" + professor + ", loc=" + loc
				+ ", week=" + week + ", lecTime=" + lecTime + ", occupancy=" + occupancy + ", credit=" + credit
				+ ", onOff=" + onOff + ", lecType=" + lecType + ", interest=" + interest + ", examType=" + examType
				+ "]";
	}
	
}

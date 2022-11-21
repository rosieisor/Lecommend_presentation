package model.dto;

public class StudentDTO {
	private int stuID;
	private String stuPW;
	private String major;
	
	public StudentDTO() {}
	
	public StudentDTO(int stuID, String stuPW, String major) {
		super();
		this.stuID = stuID;
		this.stuPW = stuPW;
		this.major = major;
	}

	public int getStuID() {
		return stuID;
	}
	
	public void setStuID(int stuID) {
		this.stuID = stuID;
	}
	
	public String getStuPW() {
		return stuPW;
	}
	
	public void setStuPW(String stuPW) {
		this.stuPW = stuPW;
	}
	
	public String getMajor() {
		return major;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return "StudentDTO [stuID=" + stuID + ", stuPW=" + stuPW + ", major=" + major + "]";
	}
	
}

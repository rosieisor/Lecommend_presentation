package model.dto;

public class StudentDTO {
	private String stuID;
	private String stuPW;
	private String major;
	
	public StudentDTO() {}
	
	public StudentDTO(String stuID, String stuPW, String major) {
		super();
		this.stuID = stuID;
		this.stuPW = stuPW;
		this.major = major;
	}

	public String getStuID() {
		return stuID;
	}
	
	public void setStuID(String stuID) {
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
	
	/* 비밀번호 검사 */
	public boolean matchPassword(String password) {
		if (password == null) {
			return false;
		}
		return this.stuPW.equals(password);
	}
	
	public boolean isSameUser(String userid) {
		return this.stuID.equals(userid);
    }

	@Override
	public String toString() {
		return "StudentDTO [stuID=" + stuID + ", stuPW=" + stuPW + ", major=" + major + "]";
	}
	
}

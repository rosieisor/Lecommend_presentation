package model.dto;

public class StatusDTO {
	private int staID;
	private int stuID;
	private String lecID;
	
	public StatusDTO() {}
	
	public StatusDTO(int staID, int stuID, String lecID) {
		super();
		this.staID = staID;
		this.stuID = stuID;
		this.lecID = lecID;
	}

	public int getStaID() {
		return staID;
	}
	
	public void setStaID(int staID) {
		this.staID = staID;
	}
	
	public int getStuID() {
		return stuID;
	}
	
	public void setStuID(int stuID) {
		this.stuID = stuID;
	}
	
	public String getLecID() {
		return lecID;
	}
	
	public void setLecID(String lecID) {
		this.lecID = lecID;
	}

	@Override
	public String toString() {
		return "StatusDTO [staID=" + staID + ", stuID=" + stuID + ", lecID=" + lecID + "]";
	}
	
}

package model.dto;

public class DibDTO {
	private int dibID;
	private int stuID;
	private String lecID;

	public DibDTO() {}
	
	public DibDTO(int dibID, int stuID, String lecID) {
		super();
		this.dibID = dibID;
		this.stuID = stuID;
		this.lecID = lecID;
	}

	public int getDibID() {
		return dibID;
	}
	
	public void setDibID(int dibID) {
		this.dibID = dibID;
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
		return "DibDTO [dibID=" + dibID + ", stuID=" + stuID + ", lecID=" + lecID + "]";
	}
	
}

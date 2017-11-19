package edu.mum.mumsched.dto;

import java.util.List;

public class RegisterSections {
	
	List<Long> sectionIds;
	public List<Long> getSectionIds() {
		return sectionIds;
	}

	public void setSectionIds(List<Long> sectionIds) {
		this.sectionIds = sectionIds;
	}

	int studentId;
	
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

}

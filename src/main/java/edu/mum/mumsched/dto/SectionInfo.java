package edu.mum.mumsched.dto;

import java.io.Serializable;

public class SectionInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String courseName;
	
	private String courseCode;
	
	private String facultyName;
	
	private Integer capacity;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
}

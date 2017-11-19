package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.Course;

public interface CourseService {
	public void save(Course course);
	
	public List<Course> findAll();
	
	public Course getCourseById(Integer id);
	
	public void delete(Course course);
}

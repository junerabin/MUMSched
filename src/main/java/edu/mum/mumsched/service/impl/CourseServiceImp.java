package edu.mum.mumsched.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.mumsched.dao.CourseDao;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.service.CourseService;


@Service
@Transactional
public class CourseServiceImp implements CourseService  {
	
	@Autowired
	CourseDao courseDao;
	

	@Override
	public void save(Course course) {
		// TODO Auto-generated method stub
		courseDao.save(course);
	}

	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		
		List<Course> listCourse = new ArrayList<>();
		for (Course course : courseDao.findAll()) {
			listCourse.add(course);
		}
		return listCourse;
	}

	@Override
	public Course getCourseById(Integer id) {
		// TODO Auto-generated method stub
		return courseDao.findCourseById(id);
	}

	@Override
	public void delete(Course course) {
		// TODO Auto-generated method stub
		courseDao.delete(course);
	}

	
		   
}
 

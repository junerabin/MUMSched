package edu.mum.mumsched.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mum.mumsched.domain.Course;

@Repository
public interface CourseDao extends CrudRepository<Course, Integer> {

	@Query("select c from Course c where c.id= :id")
	public Course findCourseById(@Param("id") Integer courseId);

	// this query should return only one course when course code/name is unique
	// params courseCode and courseName must be for the same course
	@Query("from Course c where c.courseCode = :courseCode or c.courseName = :courseName")
	public Course findCourseByCodeOrName(@Param("courseCode") String courseCode,
			@Param("courseName") String courseName);
	
	@Query("from Course c where c.targetBlock is null")
	public List<Course> findCoursesNotAssignedToAnyBlock();

}

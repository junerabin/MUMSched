package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.domain.Student;

public interface StudentService {

	public Student getStudentByName(String name);
	
	public Student getStudentById(int id);
	
	public boolean checkPrereq(Student s, Course precourse);
	
	public void saveStudent(Student student);
	
	List<Student> findAllStudents();
	
	public void updateStudent(Student student);
	
	public void deleteStudent(Integer userid);
}

package edu.mum.mumsched.service;


import java.util.List;

import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.domain.Student_Updated;

public interface StudentService_Updated   {
	
	
	public Student_Updated getStudentByName(String name);
	
	public Student_Updated getStudentById(int id);
		
	public void saveStudent(Student_Updated student);
	
	List<Student_Updated> findAllStudents();
	
	public void updateStudent(Student_Updated student);
	
	public void deleteStudent(Integer userid);
}


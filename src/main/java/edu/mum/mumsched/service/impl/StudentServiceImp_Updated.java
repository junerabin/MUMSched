package edu.mum.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.mumsched.dao.StudentDao;
import edu.mum.mumsched.dao.StudentDao_Updated;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.domain.StudentSection;
import edu.mum.mumsched.domain.Student_Updated;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.service.StudentService;
import edu.mum.mumsched.service.StudentService_Updated;

@Service
public class StudentServiceImp_Updated implements StudentService_Updated{

	@Autowired
	StudentDao_Updated studentDAO;

	@Override
	public Student_Updated getStudentByName(String name) {
		// TODO Auto-generated method stub
		return studentDAO.findByUserName(name);
	}

	@Override
	public Student_Updated getStudentById(int id) {
		// TODO Auto-generated method stub
		return studentDAO.findById(id);
	}

	@Override
	public void saveStudent(Student_Updated student) {
		studentDAO.save(student);
		return ;
	}
	
	@Override
	public List<Student_Updated> findAllStudents() {
		return studentDAO.findAllStudents();
	}
	
	@Override
	public void updateStudent(Student_Updated student){
		studentDAO.save(student);
		return ;
	}
	
	@Override
	public void deleteStudent(Integer userid){
		studentDAO.delete(userid);
		return ;
	}


}

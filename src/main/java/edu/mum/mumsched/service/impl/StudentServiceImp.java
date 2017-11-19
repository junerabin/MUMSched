package edu.mum.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.mumsched.dao.StudentDao;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.domain.StudentSection;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.service.StudentService;

@Service
public class StudentServiceImp implements StudentService{

	@Autowired
	StudentDao studentDAO;

	@Override
	public Student getStudentByName(String name) {
		// TODO Auto-generated method stub
		return studentDAO.findByUserName(name);
	}

	@Override
	public boolean checkPrereq(Student s, Course precourse) {
		// TODO Auto-generated method stub		
		for(StudentSection ss:s.getStudentSections()){
			if (ss.getSection().getCourse().getId() == precourse.getId())
			{
				return true;
			}	
		}
		return false;
	}

	@Override
	public Student getStudentById(int id) {
		// TODO Auto-generated method stub
		return studentDAO.findById(id);
	}

	@Override
	public void saveStudent(Student student) {
		studentDAO.save(student);
		return ;
	}
	
	@Override
	public List<Student> findAllStudents() {
		return studentDAO.findAllStudents();
	}
	
	@Override
	public void updateStudent(Student student){
		studentDAO.save(student);
		return ;
	}
	
	@Override
	public void deleteStudent(Integer userid){
		studentDAO.delete(userid);
		return ;
	}


}

package edu.mum.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.mumsched.dao.FacultyDao;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.service.FacultyService;

@Service
public class FacultyServiceImp implements FacultyService  {
	
	@Autowired
	FacultyDao facultyDAO;
	
	public void saveFaculty(Faculty faculty){
		facultyDAO.save(faculty);
		return ;
	}

	public void deleteFaculty(Integer userid){
		facultyDAO.delete(userid);
		return ;
	}
	
	public void updateFaculty(Faculty faculty){
		facultyDAO.save(faculty);
		return ;
	}
	
	@Override
	public Faculty getFacultyByEmail(String email) {
		return facultyDAO.findFacultyByEmail(email);
	}

	@Override
	public Faculty getFacultyByUserid(Integer userId) {
		// TODO: please fix
		return facultyDAO.findFacultyByUserid(userId);
		//return null;
	}
	
	public List<Faculty> findAllFaculties() {
		return facultyDAO.findAllFaculties();
	}
	
	public boolean isUseridUnique(Integer userid) {
		Faculty faculty = getFacultyByUserid(userid);
		// TODO: please fix
		return ( faculty == null || ((userid != null) && (faculty.getId() == userid)));
		//return true;
	}
	
	public Faculty findFacultyByUsername(String username) {
		//Faculty faculty = facultyDAO.findFacultyByUsername(username);
		//return ( faculty == null || ((username != null) && (faculty.getUser().getUsername() == username)));
		
		return facultyDAO.findFacultyByUsername(username);
	}
}
 

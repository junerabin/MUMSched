package edu.mum.mumsched.service;


import java.util.List;

import edu.mum.mumsched.domain.Faculty;

public interface FacultyService   {
	
	
	public void saveFaculty(Faculty faculty);
	public void deleteFaculty(Integer userid);
	public void updateFaculty(Faculty faculty);
	List<Faculty> findAllFaculties();
	
	public Faculty getFacultyByUserid(Integer userid);
	public Faculty getFacultyByEmail(String email);
	
	boolean isUseridUnique(Integer userid);
	Faculty findFacultyByUsername(String username);
}


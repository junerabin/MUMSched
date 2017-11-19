package edu.mum.mumsched.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mum.mumsched.domain.Faculty;

@Repository
public interface FacultyDao  extends  CrudRepository<Faculty, Integer>  {
	
	@Query("select f from Faculty f where f.id= :id")
	public Faculty findFacultyByUserid(@Param("id") Integer id);
	
	@Query("select f from Faculty f where f.user.username= :username")
	public Faculty findFacultyByUsername(@Param("username") String facultyUsername);
	
	@Query("select f from Faculty f where f.email= :email")
	public Faculty findFacultyByEmail(@Param("email") String facultyEmail);
	
	@Query("select f from Faculty f")
	public List<Faculty> findAllFaculties();
	
}
 

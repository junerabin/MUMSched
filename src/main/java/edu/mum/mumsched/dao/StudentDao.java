package edu.mum.mumsched.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Student;

@Transactional
public interface StudentDao extends CrudRepository<Student, Integer>{
	@Query("select b from Student b where b.id = :id")
	public Student findById(@Param("id") int id);
	
	@Query("select b from Student b where b.user.username = :name")
	public Student findByUserName(@Param("name") String name);
	
	@Query("select b from Student b")
	public List<Student> findAllStudents();
}

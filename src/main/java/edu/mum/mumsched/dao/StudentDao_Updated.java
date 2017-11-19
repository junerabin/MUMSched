package edu.mum.mumsched.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.domain.Student_Updated;

@Transactional
public interface StudentDao_Updated extends CrudRepository<Student_Updated, Integer>{
	@Query("select b from Student_Updated b where b.id = :id")
	public Student_Updated findById(@Param("id") int id);
	
	@Query("select b from Student_Updated b where b.user.username = :name")
	public Student_Updated findByUserName(@Param("name") String name);
	
	@Query("select b from Student_Updated b")
	public List<Student_Updated> findAllStudents();
}

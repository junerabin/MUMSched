package edu.mum.mumsched.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mum.mumsched.domain.Section;
import edu.mum.mumsched.domain.StudentSection;

@Transactional
public interface StudentSectionDao extends CrudRepository<StudentSection, Long>{
	@Query("select s from StudentSection s where s.student.id = :id")
	public Iterable<StudentSection> findByStudentId(@Param("id") int id);
	@Query("select s from StudentSection s where s.student.id = :studentid and s.section.id=:sectionid")
	public StudentSection findByStudentSection(@Param("studentid") int studentid,@Param("sectionid") int sectionid);
	

}

package edu.mum.mumsched.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mum.mumsched.domain.Section;

@Transactional
public interface SectionDao extends CrudRepository<Section, Integer> {
	@Query("select s from Section s where s.id= :id")
	public Section findSectionById(@Param("id") Integer section_id);
	
	@Query("from Section s where s.block.id= :blockId")
	public List<Section> findSectionsByBlockId(@Param("blockId") Integer blockId);
}

package edu.mum.mumsched.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import edu.mum.mumsched.domain.Entry;

@Transactional
public interface EntryDao extends CrudRepository<Entry, Integer>{
	@Query("select c from Entry c where c.id= :id")
	public Entry findEntryById(@Param("id") Integer id);
}

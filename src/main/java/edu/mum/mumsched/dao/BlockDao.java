package edu.mum.mumsched.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.mum.mumsched.domain.Block;

@Transactional
public interface BlockDao extends CrudRepository<Block, Integer> {
	
	@Query("select c from Block c where c.id= :id")
	public Block findBlockById(@Param("id") Integer block_id);

//	@Query("select b from Block b where b.entry.id = :id")
//	public List<Block> getBlocksByEntry(@Param("id") int id);
}



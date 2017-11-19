package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.Block;

public interface BlockService {
	public void save(Block block);
	
	public List<Block> findAll();
	
	public Block getBlockById(Integer id);
	
	public void delete(Block block);
}

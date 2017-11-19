package edu.mum.mumsched.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.mumsched.dao.BlockDao;
import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.service.BlockService;

@Service
public class BlockServiceImp implements BlockService{

	@Autowired
	BlockDao blockDao;
	
	@Override
	public void save(Block block) {
		// TODO Auto-generated method stub
		blockDao.save(block);
		
	}

	@Override
	public List<Block> findAll() {
		// TODO Auto-generated method stub
		List<Block> listBlock = new ArrayList<>();
		for (Block block : blockDao.findAll()) {
			listBlock.add(block);
		}
		return listBlock;
	}

	@Override
	public Block getBlockById(Integer id) {
		// TODO Auto-generated method stub
		return blockDao.findBlockById(id);
	}

	@Override
	public void delete(Block block) {
		// TODO Auto-generated method stub
		blockDao.delete(block);
	}

}

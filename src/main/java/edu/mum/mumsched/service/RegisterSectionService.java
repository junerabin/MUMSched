package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.dto.RegisterSections;

public interface RegisterSectionService {
	
	List<Block> getBlocksByUserId(int id);

	int registerSections(RegisterSections registeredSections);
	
	String errorMsg();

	//boolean checkPrequisite(RegisterSections registeredSections);

}

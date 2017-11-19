package edu.mum.mumsched.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.mumsched.dao.EntryDao;
import edu.mum.mumsched.domain.Entry;
import edu.mum.mumsched.service.EntryService;

@Service
public class EntryServiceImp implements EntryService{
	
	@Autowired
	EntryDao entryDao ;
	
	@Override
	public void save(Entry entry) {
		// TODO Auto-generated method stub
		entryDao.save(entry);
	}

	@Override
	public List<Entry> findAll() {
		// TODO Auto-generated method stub
		List<Entry> listEntry = new ArrayList<>();
		for (Entry entry : entryDao.findAll()) {
			listEntry.add(entry);
		}
		return listEntry;
	}

	@Override
	public Entry getEntryById(Integer id) {
		// TODO Auto-generated method stub
		return entryDao.findEntryById(id);
	}

	@Override
	public void delete(Entry entry) {
		// TODO Auto-generated method stub
		entryDao.delete(entry);
	}

}

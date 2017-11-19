package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.Entry;

public interface EntryService {
	public void save(Entry entry);
	
	public List<Entry> findAll();
	
	public Entry getEntryById(Integer id);
	
	public void delete(Entry entry);
}

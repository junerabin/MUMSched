package edu.mum.mumsched.service;

import java.util.List;

import edu.mum.mumsched.domain.Section;

public interface SectionService {
	public void save(Section section);

	public List<Section> findAll();

	public Section getSectionById(Integer id);

	public void delete(Section section);
}

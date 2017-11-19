package edu.mum.mumsched.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.mumsched.dao.SectionDao;
import edu.mum.mumsched.domain.Section;
import edu.mum.mumsched.service.SectionService;

@Service
public class SectionServiceImp implements SectionService {

	@Autowired
	SectionDao sectionDao;

	@Override
	public void save(Section section) {
		// TODO Auto-generated method stub
		sectionDao.save(section);
	}

	@Override
	public List<Section> findAll() {
		// TODO Auto-generated method stub

		List<Section> listSection = new ArrayList<>();
		for (Section section : sectionDao.findAll()) {
			listSection.add(section);
		}
		return listSection;
	}

	@Override
	public Section getSectionById(Integer id) {
		// TODO Auto-generated method stub
		return sectionDao.findSectionById(id.intValue());
	}

	@Override
	public void delete(Section section) {
		// TODO Auto-generated method stub
		sectionDao.delete(section);
	}


}

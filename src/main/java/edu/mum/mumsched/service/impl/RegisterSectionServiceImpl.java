package edu.mum.mumsched.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.mumsched.dao.SectionDao;
import edu.mum.mumsched.dao.StudentDao;
import edu.mum.mumsched.dao.StudentSectionDao;
import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Section;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.domain.StudentSection;
import edu.mum.mumsched.dto.RegisterSections;
import edu.mum.mumsched.service.RegisterSectionService;
import edu.mum.mumsched.service.StudentService;

@Service
@Transactional
public class RegisterSectionServiceImpl implements RegisterSectionService {
	
	@Autowired 
	private StudentDao studentDAO;
	
	@Autowired 
	private SectionDao sectionDAO;
	
	@Autowired 
	private StudentSectionDao ssDAO;
	
	@Autowired
	private StudentService studentService;
	
	private String errorMessage;
	
	@Override
	public List<Block> getBlocksByUserId(int id) {
		// TODO Auto-generated method stub
		Student s = studentDAO.findById(id);
//		return blockDAO.getBlocksByEntry(s.getEntry().getId());
		// Tuyen fixed temporarily as entry-block is now many-to-many relationship
		List<Block> blockList = s.getEntry().getBlocks();
		removeRegisteredBlock(blockList, s);
		removeUnavailableSection(blockList);
		return blockList;
	}

	@Override
	public int registerSections(RegisterSections registeredSections) {
		// TODO Auto-generated method stub
		if (!checkPrerequisite(registeredSections))
			return -1;
		Student s = studentDAO.findById(registeredSections.getStudentId());
		for(Long sectionId:registeredSections.getSectionIds())
		{
			StudentSection ss = new StudentSection();
			Section sec = sectionDAO.findSectionById(sectionId.intValue());
			ss.setStudent(s);
			ss.setSection(sec);
			ss.setApproved((byte)0);
			ss.setGrade(-1);
			ssDAO.save(ss);
			//s.addStudentSection(ss);
			sec.addStudentSection(ss);
			sectionDAO.save(sec);			
		}
		studentDAO.save(s);	
		return 0;
	}

	private boolean checkPrerequisite(RegisterSections registeredSections){
		boolean ret = true;
		Student s = studentDAO.findById(registeredSections.getStudentId());
		for(Long sectionId:registeredSections.getSectionIds()){
			Section sec = sectionDAO.findSectionById(sectionId.intValue());
			for(Course preCourse: sec.getCourse().getCoursesPre()){
				ret = (studentService.checkPrereq(s, preCourse) || checkCurrentReg(registeredSections, sec, preCourse));
				if (!ret)
				{
					errorMessage = sec.getName() + " needs prerequisite " + preCourse.getCourseName();
					return false;
				}
			}			
		}
		return ret;
	}
	 
	private boolean checkCurrentReg(RegisterSections registeredSections, Section sec, Course preCourse){
		 for(Long sectionId:registeredSections.getSectionIds()){
			 if (sectionId == sec.getId())
				 continue;
			 Section currentSec = sectionDAO.findSectionById(sectionId.intValue());
			 if ((currentSec.getCourse().getId() == preCourse.getId()) 
					 && (currentSec.getBlock().getEndDate().compareTo(sec.getBlock().getStartDate()) < 0)){
				 return true;
			 }
		 }
		 return false;		 
	 }
	
	private void removeRegisteredBlock(List<Block> blockList, Student s){
		for(Iterator<Block> it = blockList.iterator();it.hasNext();){
			Block b = it.next();
			List<StudentSection> ssList = s.getStudentSections();
			for(StudentSection ss:ssList){
				if(b.getId() == ss.getSection().getBlock().getId())
					it.remove();
			}
		}
	}
	
	private void removeUnavailableSection(List<Block> blockList){
		for(Block b:blockList){
			for(Iterator<Section> it = b.getSections().iterator(); it.hasNext();){
				Section s = it.next();
				if (s.getCapacity() <= s.getStudentSections().size())
					it.remove();
			}
		}
	}

	@Override
	public String errorMsg() {
		// TODO Auto-generated method stub
		return errorMessage;
	}
}

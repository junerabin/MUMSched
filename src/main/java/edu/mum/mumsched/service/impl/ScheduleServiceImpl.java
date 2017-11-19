package edu.mum.mumsched.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import edu.mum.mumsched.ApplicationConstants;
import edu.mum.mumsched.dao.CourseDao;
import edu.mum.mumsched.dao.EntryDao;
import edu.mum.mumsched.dao.ScheduleDao;
import edu.mum.mumsched.dao.SectionDao;
import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Entry;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Schedule;
import edu.mum.mumsched.domain.Section;
import edu.mum.mumsched.dto.ScheduleInfo;
import edu.mum.mumsched.exceptions.BlockNotEnoughException;
import edu.mum.mumsched.exceptions.ScheduleAlreadyExistsException;
import edu.mum.mumsched.exceptions.ScheduleCanNotBeDeletedException;
import edu.mum.mumsched.service.ScheduleService;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	MessageSource messageSource;

	@Autowired
	ScheduleDao scheduleDao;

	@Autowired
	EntryDao entryDao;

	@Autowired
	CourseDao courseDao;

	@Autowired
	SectionDao sectionDao;

	public Map<Integer, String> targetBlockMap = new HashMap<>();

	@Override
	public void save(Schedule schedule) {
		scheduleDao.save(schedule);
	}

	@Override
	public List<Schedule> findAll() {
		Iterator<Schedule> scheduleIter = scheduleDao.findAll().iterator();
		List<Schedule> schedules = new ArrayList<>();
		while (scheduleIter.hasNext()) {
			schedules.add(scheduleIter.next());
		}
		return schedules;
	}

	public Schedule getScheduleById(Integer id) {
		return scheduleDao.findOne(id);
	}

	@Override
	public void deleteSchedule(Integer scheduleId) {
		Schedule schedule = scheduleDao.findOne(scheduleId);
		if ("APPROVED".equals(schedule.getStatus())) {
			throw new ScheduleCanNotBeDeletedException();
		}
		for (Block block : schedule.getEntry().getBlocks()) {
			for (Section section : block.getSections()) {
				if (CollectionUtils.isEmpty(section.getEntries())
						|| section.getEntries().contains(schedule.getEntry())) {
					section.getEntries().remove(schedule.getEntry());
					sectionDao.delete(section);
				}
			}
		}
		scheduleDao.delete(schedule);
	}

	@Override
	public Schedule generateSchedule(Integer entryId) {
		Entry entry = entryDao.findEntryById(entryId);

		// verify whether we have enough blocks created for the schedule
		if (!hasEnoughBlocks(entry)) {
			throw new BlockNotEnoughException();
		}

		// verify whether there is an APPROVED schedule created for this entryId
		if (!CollectionUtils.isEmpty(entry.getSchedules())) {
			throw new ScheduleAlreadyExistsException();
		}

		// verify whether MPP and FPP courses exist, if NOT go create them
		verifyAndCreateMPPAndFPPCoursesIfNotExist();

		// loop through all blocks and create sections
		initTargetBlockMap();
		Map<String, List<String>> courseMap = new HashMap<>();
		for (int i = 0; i < entry.getBlocks().size(); i++) {
			createSectionsPerBlock(entry, entry.getBlocks().get(i), i, courseMap);
		}

		Schedule schedule = new Schedule();
		schedule.setEntry(entry);
		schedule.setStatus("DRAFT");
		scheduleDao.save(schedule);

		return schedule;
	}

	@Override
	public void updateSchedule(ScheduleInfo scheduleInfo) {
		Schedule schedule = scheduleDao.findOne(scheduleInfo.getScheduleId());
		schedule.setStatus(scheduleInfo.getStatus());
		scheduleDao.save(schedule);
	}

	private boolean hasEnoughBlocks(Entry entry) {
		if (CollectionUtils.isEmpty(entry.getBlocks())) {
			return false;
		}
		if (entry.getBlocks().size() != ApplicationConstants.NUM_OF_NEEDED_BLOCKS) {
			return false;
		}
		return true;
	}

	private void verifyAndCreateMPPAndFPPCoursesIfNotExist() {
		verifyAndCreateCourseIfNotExist(ApplicationConstants.MPP_COURSE_CODE, ApplicationConstants.MPP_COURSE_NAME);
		verifyAndCreateCourseIfNotExist(ApplicationConstants.FPP_COURSE_CODE, ApplicationConstants.FPP_COURSE_NAME);
	}

	private void verifyAndCreateCourseIfNotExist(String courseCode, String courseName) {
		Course course = courseDao.findCourseByCodeOrName(courseCode, courseName);
		if (course == null) {
			course = new Course();
			course.setCourseCode(courseCode);
			course.setCourseName(courseName);
			courseDao.save(course);
		}
	}

	private void createSectionsPerBlock(Entry entry, Block block, int blockIndex, Map<String, List<String>> courseMap) {
		// first block is always for MPP and FPP sections
		if (blockIndex == 0) {
			createMPPAndFPPSections(entry, block);
		} else {
			createElectiveSections(entry, block, blockIndex, courseMap);
		}
	}

	// First block, only MPP & FPP courses
	private void createMPPAndFPPSections(Entry entry, Block block) {
		createSections(entry, block, entry.getTotalFpp(), ApplicationConstants.FPP_COURSE_CODE,
				ApplicationConstants.FPP_COURSE_NAME);
		createSections(entry, block, entry.getTotalMpp(), ApplicationConstants.MPP_COURSE_CODE,
				ApplicationConstants.MPP_COURSE_NAME);
	}

	private void createSections(Entry entry, Block block, int totalStudent, String courseCode, String courseName) {
		int numOfSections = (totalStudent + ApplicationConstants.STUDENT_CAPACITY_LIMIT -1) / ApplicationConstants.STUDENT_CAPACITY_LIMIT;
		System.out.println("sections"+numOfSections);
		Course course = courseDao.findCourseByCodeOrName(courseCode, courseName);
		List<Faculty> faculties = course.getFaculties();

		
		for (int i = 0; i < numOfSections; i++) {
			Section section = new Section(); // no need to add entry for MPP and
												// FPP sections
			block.addSection(section);
			List<Entry> entries = new ArrayList<>();
			entries.add(entry);
			section.setEntries(entries);
			section.setCourse(course);
			// if there isn't enough faculties, leave it as null which means
			// UNSTAFFED
			if (i < faculties.size()) {
				section.setFaculty(faculties.get(i));
			}
			section.setCapacity(ApplicationConstants.STUDENT_CAPACITY_LIMIT);
			sectionDao.save(section);
		}
	}

	private void initTargetBlockMap() {
		targetBlockMap.put(1, "A");
		targetBlockMap.put(2, "B");
		targetBlockMap.put(3, "C");
		targetBlockMap.put(4, "D");
		targetBlockMap.put(5, "E");
	}

	private void createElectiveSections(Entry entry, Block block, int blockIndex, Map<String, List<String>> courseMap) {
		int numOfSections = calculateNumOfSections(entry, blockIndex);
		// this block has enough sections (maybe there are sections from
		// previous entry)
		int currentNumOfSections = findNumOfExistingSectionsOfBlock(entry, block);
		if (currentNumOfSections >= numOfSections) {
			return;
		}
		numOfSections -= currentNumOfSections;

		// the list of courses that already are used to create section
		List<String> generatedCourses = new ArrayList<>();
		// the list of faculties that already are used to create section
		List<Integer> generatedFaculties = new ArrayList<>();

		// create sections when course having target block
		List<Course> courses = findCoursesByTargetBlock(blockIndex, courseMap);
		if (!CollectionUtils.isEmpty(courses)) {
			for (Course course : courses) {
				List<Faculty> faculties = findFacultiesByCourseAndTargetBlock(course, blockIndex);
				int numOfTargetSections = createElectiveTargetSections(entry, block, course, faculties, numOfSections,
						generatedFaculties);
				numOfSections -= numOfTargetSections;
				if (numOfSections == 0) {
					return;
				}
				generatedCourses.add(course.getCourseCode());
			}
		}

		// create sections when courses not having target blocks but faculty
		// prefers to teach the course in this block
		List<Course> notAssignedToAnyBlockCourses = findCoursesNotAssignedToAnyBlock();
		for (Course course : notAssignedToAnyBlockCourses) {
			List<Faculty> faculties = findFacultiesByCourseAndTargetBlock(course, blockIndex);
			if (!CollectionUtils.isEmpty(faculties)) {
				int numOfTargetSections = createElectiveTargetSections(entry, block, course, faculties, numOfSections,
						generatedFaculties);
				numOfSections -= numOfTargetSections;
				if (numOfSections == 0) {
					return;
				}
				generatedCourses.add(course.getCourseCode());
			}
		}

		// create sections for prerequisite courses if this is block A or B
		if ("A".equals(targetBlockMap.get(blockIndex)) || "B".equals(targetBlockMap.get(blockIndex))) {
			List<Course> prerequisiteCourses = findUngeneratedPrerequisiteCourses(generatedCourses);
			for (Course course : prerequisiteCourses) {
				List<Faculty> faculties = findFacultiesByCourseAndTargetBlock(course, blockIndex);
				int numOfTargetSections = createElectiveTargetSections(entry, block, course, faculties, numOfSections,
						generatedFaculties);
				numOfSections -= numOfTargetSections;
				if (numOfSections == 0) {
					return;
				}
				generatedCourses.add(course.getCourseCode());
			}
		}

		// create sections for courses having prerequisite if this is block
		// C, D or E
		if ("C".equals(targetBlockMap.get(blockIndex)) || "D".equals(targetBlockMap.get(blockIndex))
				|| "E".equals(targetBlockMap.get(blockIndex))) {
			List<Course> coursesHavingPrerequisite = findUngeneratedCoursesHavingPrerequisite(generatedCourses);
			for (Course course : coursesHavingPrerequisite) {
				List<Course> prerequisiteCourses = course.getCoursesPre();
				for (Course c : prerequisiteCourses) {
					if ((!CollectionUtils.isEmpty(courseMap.get("A")) && courseMap.get("A").contains(c.getCourseCode()))
							|| (!CollectionUtils.isEmpty(courseMap.get("B"))
									&& courseMap.get("B").contains(c.getCourseCode()))) {
						List<Faculty> faculties = findFacultiesByCourseAndTargetBlock(course, blockIndex);
						int numOfTargetSections = createElectiveTargetSections(entry, block, course, faculties,
								numOfSections, generatedFaculties);
						numOfSections -= numOfTargetSections;
						if (numOfSections == 0) {
							return;
						}
						generatedCourses.add(course.getCourseCode());
						break;
					}
				}
			}
		}

		// create sections when courses not having target blocks and no
		// faculty prefers to teach the course in this block
		for (Course course : notAssignedToAnyBlockCourses) {
			List<Faculty> faculties = findFacultiesByCourseAndTargetBlock(course, blockIndex);
			if (CollectionUtils.isEmpty(faculties)) {
				int numOfTargetSections = createElectiveTargetSections(entry, block, course, faculties, numOfSections,
						generatedFaculties);
				numOfSections -= numOfTargetSections;
				if (numOfSections == 0) {
					return;
				}
				generatedCourses.add(course.getCourseCode());
			}
		}

		// if still does not create enough sections, create more sections with
		// courses not having target blocks and assign faculty as UNSTAFFED
		int size = notAssignedToAnyBlockCourses.size();
		if (size > 0) {
			for (int i = 0; i < numOfSections; i++) {
				createElectiveTargetSections(entry, block, notAssignedToAnyBlockCourses.get(getRandomNumber(size)),
						null, numOfSections, generatedFaculties);
			}
		}

		// if still does not have enough sections created, we need to go update
		// course and faculty
	}

	private int getRandomNumber(int max) {
		return (int) (Math.random() * max);
	}

	private int calculateNumOfSections(Entry entry, int blockIndex) {
		int numOfSections = (entry.getTotalFpp() + entry.getTotalMpp()+ApplicationConstants.STUDENT_CAPACITY_LIMIT -1) / ApplicationConstants.STUDENT_CAPACITY_LIMIT;
		if (blockIndex == 5) { // last block include FPP & OPT students
			numOfSections = (int) Math.round((entry.getTotalFpp() + entry.getTotalMpp() * entry.getPercentOpt() * 0.01)
					/ ApplicationConstants.STUDENT_CAPACITY_LIMIT);
		}
		return numOfSections;
	}

	private List<Course> findCoursesByTargetBlock(int blockIndex, Map<String, List<String>> courseMap) {
		List<Course> courses = new ArrayList<>();
		Iterator<Course> coursesIterator = courseDao.findAll().iterator();
		String currentTargetBlock = targetBlockMap.get(blockIndex);
		while (coursesIterator.hasNext()) {
			Course course = coursesIterator.next();
			if (isNotMPPOrFPPCourse(course)) {
				String targetBlock = course.getTargetBlock();
				if (!StringUtils.isEmpty(targetBlock) && targetBlock.indexOf(currentTargetBlock) >= 0) {
					if (courseMap.get(currentTargetBlock) == null) {
						List<String> generatedCourses = new ArrayList<>();
						generatedCourses.add(course.getCourseCode());
						courseMap.put(currentTargetBlock, generatedCourses);
						courses.add(course);
					} else {
						if (!courseMap.get(currentTargetBlock).contains(course.getCourseCode())) {
							courseMap.get(currentTargetBlock).add(course.getCourseCode());
							courses.add(course);
						}
					}
				}
			}
		}

		return courses;

	}

	private List<Faculty> findFacultiesByCourseAndTargetBlock(Course course, int blockIndex) {
		List<Faculty> courseFaculties = course.getFaculties();
		List<Faculty> targetBlockFaculties = new ArrayList<>();
		String currentTargetBlock = targetBlockMap.get(blockIndex);
		if (!CollectionUtils.isEmpty(courseFaculties)) {
			for (Faculty faculty : courseFaculties) {
				String targetBlock = faculty.getTargetBlock();
				if (!StringUtils.isEmpty(targetBlock) && targetBlock.indexOf(currentTargetBlock) >= 0) {
					targetBlockFaculties.add(faculty);
				}
			}
		}
		return targetBlockFaculties;
	}

	/**
	 * 
	 * @param entry
	 * @param block
	 * @param course
	 * @param faculties
	 * @param numOfSections
	 *            number of total available sections
	 * @return the number of sections that we could create
	 */
	private int createElectiveTargetSections(Entry entry, Block block, Course course, List<Faculty> faculties,
			int numOfSections, List<Integer> generatedFaculties) {
		int numOfCreatedSections = 0;
		if (CollectionUtils.isEmpty(faculties)) {
			Section section = new Section();
			List<Entry> entries = new ArrayList<>();
			entries.add(entry);
			section.setEntries(entries);
			section.setBlock(block);
			section.setCourse(course);
			section.setCapacity(ApplicationConstants.STUDENT_CAPACITY_LIMIT);
			sectionDao.save(section);
			numOfCreatedSections++;
		} else {
			for (Faculty faculty : faculties) {
				if (numOfCreatedSections >= numOfSections) {
					return numOfCreatedSections;
				}
				if (!generatedFaculties.contains(faculty.getId())) {
					Section section = new Section(); // no need to add entry for
														// MPP
														// and
					List<Entry> entries = new ArrayList<>();
					entries.add(entry);
					section.setEntries(entries);
					section.setBlock(block);
					section.setCourse(course);
					section.setFaculty(faculty);
					section.setCapacity(ApplicationConstants.STUDENT_CAPACITY_LIMIT);
					sectionDao.save(section);

					generatedFaculties.add(faculty.getId());
					numOfCreatedSections++;
				}
			}
		}
		return numOfCreatedSections;
	}

	private int findNumOfExistingSectionsOfBlock(Entry entry, Block block) {
		int numOfSections = 0;
		List<Section> sections = sectionDao.findSectionsByBlockId(block.getId());
		if (!CollectionUtils.isEmpty(sections)) {
			if (!isNotMPPOrFPPCourse(sections.get(0).getCourse())) {
				return numOfSections;
			}
			numOfSections = sections.size();
			for (Section section : sections) {
				if (CollectionUtils.isEmpty(section.getEntries())) {
					List<Entry> entries = new ArrayList<>();
					entries.add(entry);
					section.setEntries(entries);
				} else {
					section.getEntries().add(entry);
				}
			}
		}
		return numOfSections;
	}

	private List<Course> findCoursesNotAssignedToAnyBlock() {
		List<Course> courses = new ArrayList<>();
		List<Course> notAssignedToAnyBlockCourses = courseDao.findCoursesNotAssignedToAnyBlock();
		for (Course course : notAssignedToAnyBlockCourses) {
			if (isNotMPPOrFPPCourse(course)) {
				courses.add(course);
			}
		}
		return courses;
	}

	private List<Course> findUngeneratedPrerequisiteCourses(List<String> generatedCourses) {
		List<Course> ungeneratedCoursesHavingPrerequisite = new ArrayList<>();
		Iterator<Course> allCourses = courseDao.findAll().iterator();
		while (allCourses.hasNext()) {
			Course course = allCourses.next();
			if (!CollectionUtils.isEmpty(course.getCoursesPre())) {
				for (Course c : course.getCoursesPre()) {
					if (isNotMPPOrFPPCourse(c)) {
						if (ungeneratedCoursesHavingPrerequisite.contains(c)) {
							continue;
						}
						if (!generatedCourses.contains(c.getCourseCode())) {
							ungeneratedCoursesHavingPrerequisite.add(c);
						}
					}
				}
			}
		}
		return ungeneratedCoursesHavingPrerequisite;
	}

	private List<Course> findUngeneratedCoursesHavingPrerequisite(List<String> generatedCourses) {
		List<Course> ungeneratedCoursesHavingPrerequisite = new ArrayList<>();
		Iterator<Course> allCourses = courseDao.findAll().iterator();
		while (allCourses.hasNext()) {
			Course course = allCourses.next();
			if (isNotMPPOrFPPCourse(course)) {
				if (!CollectionUtils.isEmpty(course.getCoursesPre())) {
					if (ungeneratedCoursesHavingPrerequisite.contains(course)) {
						continue;
					}
					if (!generatedCourses.contains(course.getCourseCode())) {
						ungeneratedCoursesHavingPrerequisite.add(course);
					}
				}
			}
		}
		return ungeneratedCoursesHavingPrerequisite;
	}

	private boolean isNotMPPOrFPPCourse(Course course) {
		return !ApplicationConstants.FPP_COURSE_CODE.equals(course.getCourseCode())
				&& !ApplicationConstants.FPP_COURSE_NAME.equals(course.getCourseName())
				&& !ApplicationConstants.MPP_COURSE_CODE.equals(course.getCourseCode())
				&& !ApplicationConstants.MPP_COURSE_NAME.equals(course.getCourseName());
	}
}

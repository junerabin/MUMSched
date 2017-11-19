package edu.mum.mumsched.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Schedule;
import edu.mum.mumsched.domain.Section;
import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.dto.BlockInfo;
import edu.mum.mumsched.dto.ScheduleInfo;
import edu.mum.mumsched.dto.SectionInfo;
import edu.mum.mumsched.exceptions.BlockNotEnoughException;
import edu.mum.mumsched.exceptions.ScheduleAlreadyExistsException;
import edu.mum.mumsched.exceptions.ScheduleCanNotBeDeletedException;
import edu.mum.mumsched.service.EntryService;
import edu.mum.mumsched.service.FacultyService;
import edu.mum.mumsched.service.ScheduleService;
import edu.mum.mumsched.service.StudentService;

@Controller
public class ScheduleController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	EntryService entryService;

	@Autowired
	StudentService studentService;

	@Autowired
	FacultyService facultyService;

	@RequestMapping(value = "/schedules", method = RequestMethod.GET)
	public String viewSchedules(Principal principal, Model model) {
		Faculty faculty = facultyService.findFacultyByUsername(principal.getName());
		if (faculty != null) {
			model.addAttribute("isFaculty", "true");
		}
		model.addAttribute("schedules", scheduleService.findAll());
		return "schedule/schedules";
	}

	@RequestMapping(value = "/viewScheduleDetail/{id}", method = RequestMethod.GET)
	public String viewScheduleDetail(@PathVariable("id") Integer scheduleId, Principal principal, Model model) {
		Schedule schedule = scheduleService.getScheduleById(scheduleId);
		model.addAttribute("scheduleInfo", buildScheduleInfo(schedule));
		Faculty faculty = facultyService.findFacultyByUsername(principal.getName());
		if (faculty != null) {
			model.addAttribute("isFaculty", "true");
		}
		return "schedule/updateSchedule";
	}

	@RequestMapping(value = "/updateSchedule", method = RequestMethod.POST)
	@ResponseBody
	public String updateSchedule(ScheduleInfo scheduleInfo) {
		scheduleService.updateSchedule(scheduleInfo);
		return "";
	}

	@RequestMapping(value = "/deleteSchedule/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteSchedule(@PathVariable("id") Integer scheduleId, Model model) {
		scheduleService.deleteSchedule(scheduleId);
		return "";
	}

	@RequestMapping(value = "/generateSchedule", method = RequestMethod.GET)
	public String navigateToScheduleGeneration(Model model) {
		ScheduleInfo scheduleInfo = new ScheduleInfo();
		scheduleInfo.setEntries(entryService.findAll());
		model.addAttribute("scheduleInfo", scheduleInfo);

		return "schedule/generateSchedule";
	}

	@RequestMapping(value = "/generateSchedule", method = RequestMethod.POST)
	public String generateSchedule(ScheduleInfo scheduleInfo, Model model) {
		Schedule schedule = scheduleService.generateSchedule(scheduleInfo.getEntryId());
		model.addAttribute("scheduleInfo", buildScheduleInfo(schedule));
		return "schedule/scheduleDetail";
	}

	@RequestMapping(value = "/student/viewSchedule", method = RequestMethod.GET)
	public String studentViewSchedule(Principal principal, Model model) {
		Student student = studentService.getStudentByName(principal.getName());
		model.addAttribute("scheduleInfo", buildScheduleInfo(student.getEntry().getSchedules().get(0)));
		return "schedule/scheduleDetail";
	}

	private ScheduleInfo buildScheduleInfo(Schedule schedule) {
		ScheduleInfo scheduleInfo = new ScheduleInfo();
		scheduleInfo.setScheduleId(schedule.getId());
		scheduleInfo.setEntryName(schedule.getEntry().getName());
		scheduleInfo.setStatus(schedule.getStatus());
		for (Block block : schedule.getEntry().getBlocks()) {
			BlockInfo blockInfo = new BlockInfo();
			blockInfo.setBlockName(block.getName());
			SimpleDateFormat sdf = new SimpleDateFormat("MMM d");
			blockInfo.setBlockPeriod(sdf.format(block.getStartDate()) + "-" + sdf.format(block.getEndDate()));
			for (Section section : block.getSections()) {
				Faculty loginFaculty = facultyService
						.findFacultyByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
				if (loginFaculty == null
						|| (section.getFaculty() != null && loginFaculty.getId() == section.getFaculty().getId())) {
					if (CollectionUtils.isEmpty(section.getEntries())
							|| section.getEntries().contains(schedule.getEntry())) {
						SectionInfo sectionInfo = new SectionInfo();
						sectionInfo.setCourseName(section.getCourse().getCourseName());
						sectionInfo.setCourseCode(section.getCourse().getCourseCode());
						Faculty faculty = section.getFaculty();
						if (faculty == null) {
							sectionInfo.setFacultyName("UNSTAFFED");
						} else {
							sectionInfo.setFacultyName(
									section.getFaculty().getFirstName() + " " + section.getFaculty().getLastName());
						}
						sectionInfo.setCapacity(section.getCapacity());
						blockInfo.getSectionInfos().add(sectionInfo);
					}
				}
			}
			scheduleInfo.getBlockInfos().add(blockInfo);
		}
		return scheduleInfo;
	}

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Does not have enough blocks")
	@ExceptionHandler(BlockNotEnoughException.class)
	public void blockNotEnought() {

	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Approved schedule can not be deleted")
	@ExceptionHandler(ScheduleCanNotBeDeletedException.class)
	public void cannotDeleteSchedule() {

	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "An existing schedule has been created for this entry")
	@ExceptionHandler(ScheduleAlreadyExistsException.class)
	public void scheduleAlreadyExists() {

	}
}
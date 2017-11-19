package edu.mum.mumsched.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.User;
import edu.mum.mumsched.service.CourseService;
import edu.mum.mumsched.service.FacultyService;
import edu.mum.mumsched.service.UserService;

@Controller
//@RequestMapping("/")
//@SessionAttributes("roles")
public class FacultyController {

	@Autowired
	FacultyService facultyService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value = { "/facultylist" }, method = RequestMethod.GET)
	public String listFaculties(ModelMap model) {

		List<Faculty> faculties = facultyService.findAllFaculties();
		model.addAttribute("faculties", faculties);
		return "faculty/facultylist";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	//Add and edit faculty
	@RequestMapping(value = { "/newupdatefaculty" }, method = RequestMethod.GET)
	public String newFaculty(ModelMap model) {
		model.addAttribute("availableCourses", courseService.findAll());
		
		List<User> availableUsers = new ArrayList<>();
		List<User> tmpUsers = new ArrayList<>();
		tmpUsers = userService.findAllUsers();
		for (User u : tmpUsers) {
		    if(u.getUsername().compareTo("faculty") == 0)
		    {
		    	availableUsers.add(u);
		    }
		}
		model.addAttribute("availableUsers", availableUsers);
		model.addAttribute("users", userService.findAllUsers());
		
		List<Course> courses = new ArrayList<Course>();
		Faculty faculty = new Faculty();
		faculty.setCourses(courses);
		model.addAttribute("faculty", faculty);
		model.addAttribute("edit", false);
		return "faculty/newupdatefaculty";
	}

	@RequestMapping(value = { "/newupdatefaculty" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveFaculty(@Valid Faculty faculty, BindingResult result,	ModelMap model) {
		if (result.hasErrors()) {
			return "faculty/newupdatefaculty";
		}
		facultyService.saveFaculty(faculty);
		return "faculty/successaddfaculty";
	}
	
	@RequestMapping(value = { "/delete-faculty-{id}" }, method = RequestMethod.GET)
	public String toDeleteFaculty(@PathVariable("id") Integer userid, Model model){
		Faculty faculty = facultyService.getFacultyByUserid(userid);
		model.addAttribute("faculty", faculty);
		return "faculty/confirmDeleteFaculty";
	}
	
	//edit faculty
	@RequestMapping(value = { "/edit-faculty-{userid}" }, method = RequestMethod.GET)
	public String editFaculty(@PathVariable Integer userid, ModelMap model) {
		List<User> availableUsers = new ArrayList<>();
		List<User> tmpUsers = new ArrayList<>();
		tmpUsers = userService.findAllUsers();
		for (User u : tmpUsers) {
		    if(u.getUsername().compareTo("faculty") == 0)
		    {
		    	availableUsers.add(u);
		    }
		}
		model.addAttribute("availableUsers", availableUsers);
		
		model.addAttribute("availableCourses", courseService.findAll());
		Faculty faculty = facultyService.getFacultyByUserid(userid);
		model.addAttribute("faculty", faculty);
		model.addAttribute("edit", true);
		return "faculty/newupdatefaculty";
	}
	
	@RequestMapping(value = { "/edit-faculty-{userid}" }, method = RequestMethod.POST)
	public String updateFaculty(@Valid Faculty faculty, BindingResult result, ModelMap model, @PathVariable Integer userid) {

		if (result.hasErrors()) {
			return "faculty/newupdatefaculty";
		}

		facultyService.updateFaculty(faculty);

		model.addAttribute("success", "Faculty " + faculty.getFirstName() + " "+ faculty.getLastName() + " updated successfully");
		return "faculty/successaddfaculty";
	}

	@RequestMapping(value = "/deleteFaculty")
	public String deleteFaculty(Faculty faculty, Model model){
		try{
			facultyService.deleteFaculty(faculty.getId());
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = { "/facultyprofile" }, method = RequestMethod.GET)
	public String viewFacultyProfile(ModelMap model) {
		
		return "faculty/facultyprofile";
	}
	
	@ModelAttribute("allCourses")
    public List<Course> initializeCourses() {
        return courseService.findAll();
    }
	@ModelAttribute("allUsers")
    public List<User> initializeUsers() {
        return userService.findAllUsers();
    }
}

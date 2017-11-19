package edu.mum.mumsched.controllers;

import java.util.List;

import javax.validation.Valid;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.service.CourseService;
import edu.mum.mumsched.service.FacultyService;

@Controller
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	FacultyService facultyService;
	
	@RequestMapping(value = "/createcourse", method = RequestMethod.GET)
	public String navigateToCreateCourse(Model model) {
		List<Faculty> listFaculty = new ArrayList<>();
		listFaculty = facultyService.findAllFaculties();
		model.addAttribute("listFaculty", listFaculty);
		model.addAttribute("course", new Course());
		model.addAttribute("listCourse", courseService.findAll());
		return "course/createCourse";
	}
	
	@RequestMapping(value = "/createcourse", method = RequestMethod.POST)
	@ResponseBody
	public String createCourse(@Valid @ModelAttribute("course") Course course, BindingResult result, ModelMap model){
		
		
		if (result.hasErrors()) {
			return "course/createCourse";
        }
		courseService.save(course);
		return "";
	}
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public String findAllCourse(Model model){
		model.addAttribute("course", new Course());
		model.addAttribute("listCourse",courseService.findAll());
		
		return "course/courses";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String navigateToEditCourse(@PathVariable("id") Integer id,Model model){
		
		
		
		List<Course> listCourse = new ArrayList<>();	
		listCourse = courseService.findAll();
		
		//remove prerequisite itself
		Course course = courseService.getCourseById(id);
		model.addAttribute("course", course);
		listCourse.remove(course);
		
		List<Faculty> listFaculty = new ArrayList<>();
		listFaculty = facultyService.findAllFaculties();
		model.addAttribute("listFaculty", listFaculty);
		model.addAttribute("listCourse",listCourse);
		return "course/editCourse";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editCourse(@Valid @ModelAttribute("course") Course course, BindingResult result, Model model){
		if (result.hasErrors()) {
			return "course/editCourse";
        }
		courseService.save(course);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String getDeleteCourse(@PathVariable("id") Integer id,Model model){
		Course course = courseService.getCourseById(id);
		model.addAttribute("course", course);
		
		return "course/confirmDelete";
	}
	
	@RequestMapping(value = "/delete")
	public String deleteCourse(Course course,Model model){

		courseService.delete(courseService.getCourseById(course.getId()));
		return "redirect:/";
	}
	
}

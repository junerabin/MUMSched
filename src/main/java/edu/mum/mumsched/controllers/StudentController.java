package edu.mum.mumsched.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.domain.Student_Updated;
import edu.mum.mumsched.domain.User;
import edu.mum.mumsched.service.StudentService;
import edu.mum.mumsched.service.StudentService_Updated;
import edu.mum.mumsched.service.UserService;

@Controller
public class StudentController {

	@Autowired
	StudentService_Updated studentService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = { "/studentlist" }, method = RequestMethod.GET)
	public String listStudents(ModelMap model) {

		List<Student_Updated> students = studentService.findAllStudents();
		model.addAttribute("students", students);
		return "student/studentlist";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	//Add and edit student
	@RequestMapping(value = { "/newupdatestudent" }, method = RequestMethod.GET)
	public String newStudent(ModelMap model) {
		
		List<User> availableUsers = new ArrayList<>();
		List<User> tmpUsers = new ArrayList<>();
		tmpUsers = userService.findAllUsers();
		for (User u : tmpUsers) {
		    if(u.getUsername().compareTo("student") == 0)
		    {
		    	availableUsers.add(u);
		    }
		}
		model.addAttribute("availableUsers", availableUsers);
		model.addAttribute("users", userService.findAllUsers());
		
		Student_Updated student = new Student_Updated();
		model.addAttribute("student", student);
		model.addAttribute("edit", false);
		return "student/newupdatestudent";
	}
	
	@RequestMapping(value = { "/newupdatestudent" }, method = RequestMethod.POST)
	@ResponseBody
	public String saveStudent(@Valid Student_Updated student, BindingResult result,	ModelMap model) {
		if (result.hasErrors()) {
			return "faculty/newupdatestudent";
		}
		studentService.saveStudent(student);
		return "student/successaddstudent";
	}
	
	@RequestMapping(value = { "/delete-student-{id}" }, method = RequestMethod.GET)
	public String toDeleteStudent(@PathVariable("id") Integer userid, Model model){
		Student_Updated student = studentService.getStudentById(userid);
		model.addAttribute("student", student);
		return "student/confirmDeleteStudent";
	}
	
	//edit student
	@RequestMapping(value = { "/edit-student-{userid}" }, method = RequestMethod.GET)
	public String editStudent(@PathVariable Integer userid, ModelMap model) {
		List<User> availableUsers = new ArrayList<>();
		List<User> tmpUsers = new ArrayList<>();
		tmpUsers = userService.findAllUsers();
		for (User u : tmpUsers) {
		    if(u.getUsername().compareTo("student") == 0)
		    {
		    	availableUsers.add(u);
		    }
		}
		model.addAttribute("availableUsers", availableUsers);
		
		Student_Updated student = studentService.getStudentById(userid);
		model.addAttribute("student", student);
		model.addAttribute("edit", true);
		return "student/newupdatestudent";
	}
	
	@RequestMapping(value = { "/edit-student-{userid}" }, method = RequestMethod.POST)
	public String updateStudent(@Valid Student_Updated student, BindingResult result, ModelMap model, @PathVariable Integer userid) {

		if (result.hasErrors()) {
			return "student/newupdatestudent";
		}

		studentService.updateStudent(student);

		model.addAttribute("success", "Student " + student.getFirstName() + " "+ student.getLastName() + " updated successfully");
		return "student/successaddstudent";
	}
	
	@RequestMapping(value = "/deleteStudent")
	public String deleteStudent(Student_Updated student, Model model){
		try{
			studentService.deleteStudent(student.getId());
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = { "/studentprofile" }, method = RequestMethod.GET)
	public String viewFacultyProfile(ModelMap model) {
		
		return "student/studentprofile";
	}
	
	@ModelAttribute("allUsers")
    public List<User> initializeUsers() {
        return userService.findAllUsers();
    }

}

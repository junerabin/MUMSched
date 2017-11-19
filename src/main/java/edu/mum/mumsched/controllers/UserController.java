package edu.mum.mumsched.controllers;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mum.mumsched.domain.Student;
import edu.mum.mumsched.service.StudentService;

@Controller
public class UserController {

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	StudentService studentService;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPageAsDefault(Principal principal, Model model) {
		if (principal == null) {
			return "user/login";
		}
		Student s = studentService.getStudentByName(principal.getName());
		model.addAttribute("studentId", s == null? 1 : s.getId());
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Locale locale) {

		if (error != null) {
			model.addAttribute("error", messageSource.getMessage("error.invaliduserpass", null, locale));
		}

		if (logout != null) {
			model.addAttribute("msg", messageSource.getMessage("logout.success", null, locale));
		}

		return "user/login";
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied(Model model, Principal principal) {

		// check if user is login
		// Authentication auth =
		// SecurityContextHolder.getContext().getAuthentication();
		// if (!(auth instanceof AnonymousAuthenticationToken)) {
		// UserDetails userDetail = (UserDetails) auth.getPrincipal();
		// System.out.println(userDetail);
		//
		// model.addObject("username", userDetail.getUsername());
		//
		// }
		model.addAttribute("username", principal.getName());

		return "user/403";
	}
	
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public String viewCourses() {
		return "course/courses";
	}
}
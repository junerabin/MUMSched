package edu.mum.mumsched.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.dto.RegisterSections;
import edu.mum.mumsched.service.RegisterSectionService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterSectionService registerService;
	
	@RequestMapping(value = "/registersection/{id}", method = RequestMethod.GET)
	public String displaySections(@PathVariable int id, Model model) {
		List<Block> listBlocks = registerService.getBlocksByUserId(id);
		model.addAttribute("studentblocks",listBlocks);	
		model.addAttribute("studentId",id);
		return "student/sectionRegistration";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerSections(@ModelAttribute("regSections") RegisterSections registeredSections,  BindingResult result, ModelMap model) {
		if (registerService.registerSections(registeredSections) != 0)
			model.addAttribute("errorMsg", registerService.errorMsg());
		else
			model.addAttribute("successMsg", "Registered sucessfully for student with id " + registeredSections.getStudentId());
		return "student/registerResult";
	}	
}

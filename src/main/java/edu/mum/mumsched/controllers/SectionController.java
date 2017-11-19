package edu.mum.mumsched.controllers;

import java.util.List;

import javax.validation.Valid;

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

import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.domain.Course;
import edu.mum.mumsched.domain.Faculty;
import edu.mum.mumsched.domain.Section;
import edu.mum.mumsched.service.BlockService;
import edu.mum.mumsched.service.CourseService;
import edu.mum.mumsched.service.FacultyService;
import edu.mum.mumsched.service.SectionService;

@Controller
public class SectionController {

	@Autowired
	SectionService sectionService;
	@Autowired
	CourseService courseService;
	@Autowired
	BlockService blockService;
	@Autowired
	FacultyService facultyService;

	@RequestMapping(value = "/createsection", method = RequestMethod.GET)
	public String navigateToCreateSection(Model model) {
		model.addAttribute("section", new Section());

		List<Block> listBlock = blockService.findAll();
		model.addAttribute("listBlock", listBlock);
		List<Course> listCourse = courseService.findAll();
		model.addAttribute("listCourse", listCourse);
		List<Faculty> listFaculty = facultyService.findAllFaculties();
		model.addAttribute("listFaculty", listFaculty);
		List<Section> listSection = sectionService.findAll();
		model.addAttribute("listSection", listSection);

		return "section/createSection";
	}

	@RequestMapping(value = "/createsection", method = RequestMethod.POST)
	@ResponseBody
	public String createSection(@Valid @ModelAttribute("section") Section section, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			return "section/createsection";
		}
		sectionService.save(section);
		return "";
	}

	@RequestMapping(value = "/sections", method = RequestMethod.GET)
	public String findAllSection(Model model) {
		// model.addAttribute("section", new Section());
		model.addAttribute("listSection", sectionService.findAll());

		return "section/sections";
	}

	@RequestMapping(value = "/editsection/{id}", method = RequestMethod.GET)
	public String navigateToEditSection(@PathVariable("id") Integer id, Model model) {
		Section section = sectionService.getSectionById(id);
		model.addAttribute("section", section);

		List<Block> listBlock = blockService.findAll();
		model.addAttribute("listBlock", listBlock);
		List<Course> listCourse = courseService.findAll();
		model.addAttribute("listCourse", listCourse);
		List<Faculty> listFaculty = facultyService.findAllFaculties();
		model.addAttribute("listFaculty", listFaculty);
		List<Section> listSection = sectionService.findAll();
		model.addAttribute("listSection", listSection);

		return "section/editSection";
	}

	@RequestMapping(value = "/editsection", method = RequestMethod.POST)
	public String editSection(@Valid Section section, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "section/editSection";
		}
		sectionService.save(section);
		return "redirect:/";
	}

	@RequestMapping(value = "/deletesection/{id}")
	public String getDeleteSection(@PathVariable("id") Integer id, Model model) {
		Section section = sectionService.getSectionById(id);
		model.addAttribute("section", section);

		return "section/deleteSection";
	}

	@RequestMapping(value = "/deletesection")
	public String deleteSection(Section section, Model model) {
		sectionService.delete(sectionService.getSectionById(section.getId()));
		return "redirect:/";
	}

}

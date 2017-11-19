package edu.mum.mumsched.controllers;

import java.util.ArrayList;
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
import edu.mum.mumsched.domain.Entry;
import edu.mum.mumsched.service.BlockService;
import edu.mum.mumsched.service.EntryService;

@Controller
public class BlockController {
	
	@Autowired
	BlockService blockService;
	@Autowired
	EntryService entryService;
	
	@RequestMapping(value = "/block", method = RequestMethod.GET)
	public String findAllCourse(Model model){
		model.addAttribute("block", new Block());
		model.addAttribute("listBlock",blockService.findAll());
		
		return "block/blocks";
	}
	@RequestMapping(value = "/createblock", method = RequestMethod.GET)
	public String navigateCreateBlock(Model model){
		List<Entry> listEntry = entryService.findAll();
		model.addAttribute("listEntry", listEntry);
		model.addAttribute("block", new Block());	
		return "block/createBlock";
	}
	
//	@RequestMapping(value = "/createblock", method = RequestMethod.POST)
//	public String createBlock(Block block, BindingResult result, ModelMap model){
//		blockService.save(block);
//		return "";
//	}
	
	@RequestMapping(value = "/createblock", method = RequestMethod.POST)
	@ResponseBody
	public String createCourse(@Valid @ModelAttribute("block") Block block, BindingResult result, ModelMap model){
		
		if (result.hasErrors()) {
			return "course/createBlock";
        }
		blockService.save(block);
		return "";
	}
	
	@RequestMapping(value = "/editblock/{id}", method = RequestMethod.GET)
	public String navigateToEditBlock(@PathVariable("id") Integer id,Model model){
		
		Block block = blockService.getBlockById(id);
		model.addAttribute("block", block);
		List<Entry> listEntry = entryService.findAll();
		model.addAttribute("listEntry", listEntry);
		return "block/editBlock";
	}
	
	@RequestMapping(value = "/editblock", method = RequestMethod.POST)
	public String editBlock(Block block, Model model){

		blockService.save(block);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/deleteblock/{id}")
	public String getEntryCourse(@PathVariable("id") Integer id,Model model){
		Block block = blockService.getBlockById(id);
		model.addAttribute("block", block);
		
		return "block/confirmDeleteBlock";
	}
	
	@RequestMapping(value = "/deleteblock")
	public String deleteEntry(Block block,Model model){

		blockService.delete(blockService.getBlockById(block.getId()));
		return "redirect:/";
	}
}

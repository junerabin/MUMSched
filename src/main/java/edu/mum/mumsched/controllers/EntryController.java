package edu.mum.mumsched.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mum.mumsched.domain.Block;
import edu.mum.mumsched.domain.Entry;
import edu.mum.mumsched.service.BlockService;
import edu.mum.mumsched.service.EntryService;

@Controller
public class EntryController {
	@Autowired
	EntryService entryService;
	@Autowired
	BlockService blockService;
	
	@RequestMapping(value = "/entry", method = RequestMethod.GET)
	public String findAllEntry(Model model){
		model.addAttribute("entry", new Entry());
		model.addAttribute("listEntry",entryService.findAll());
		return "entry/entries";
	}
	
	@RequestMapping(value = "/createentry", method = RequestMethod.GET)
	public String navigateCreateEntry(Model model){
//		List<Block> listBlock = blockService.findAll();
//		model.addAttribute("listBlock", listBlock);
		model.addAttribute("entry", new Entry());	
		return "entry/createEntry";
	}
	
	@RequestMapping(value = "/createentry", method = RequestMethod.POST)
	@ResponseBody
	public String createEntry(Entry entry, BindingResult result, ModelMap model){

		List<Block> listBlock = blockService.findAll();
		List<Block> newListBlock = new ArrayList<>();
		for (Block block : listBlock) {
			if(block.getStartDate().compareTo(entry.getStartDate()) >= 0 
					&& block.getEndDate().compareTo(entry.getEndDate()) <= 0){
				newListBlock.add(block);
			}
		}
		entry.setBlocks(newListBlock);
		entryService.save(entry);
		return "";
	}
	
	@RequestMapping(value = "/editentry/{id}", method = RequestMethod.GET)
	public String navigateToEditEntry(@PathVariable("id") Integer id,Model model){
		
		Entry entry = entryService.getEntryById(id);
		model.addAttribute("entry", entry);
//		List<Block> listBlock = blockService.findAll();
//		model.addAttribute("listBlock", listBlock);
		return "entry/editEntry";
	}
	
	@RequestMapping(value = "/editentry", method = RequestMethod.POST)
	public String editEntry(Entry entry, Model model){

		entryService.save(entry);
		return "redirect:/";
	}
	@RequestMapping(value = "/deleteentry/{id}")
	public String getEntryCourse(@PathVariable("id") Integer id,Model model){
		Entry entry = entryService.getEntryById(id);
		model.addAttribute("entry", entry);
		
		return "entry/confirmDeleteEntry";
	}
	
	@RequestMapping(value = "/deleteentry")
	public String deleteEntry(Entry entry,Model model){

		entryService.delete(entryService.getEntryById(entry.getId()));
		return "redirect:/";
	}
}
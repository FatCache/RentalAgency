package com.rentalagency.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.rentalagency.me.dao.SimpleMessageDAO;
import com.rentalagency.me.model.SimpleMessage;
import com.rentalagency.me.model.SimpleMessage.Status;

@Controller
@RequestMapping(value = "/api/sm/")
public class SimpleMessageController {

	@Autowired
	SimpleMessageDAO smdao;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String message(Model model) {
		List<SimpleMessage> smlist = this.smdao.getMessageList();
		
		model.addAttribute("smlist",smlist);
		
		return "simplemessage";
	}
	
	@RequestMapping(value="/add/", method = RequestMethod.POST)
	public String addMessage(Model model, @RequestParam(value="descr",required=false) String descr) {
		SimpleMessage sm = new SimpleMessage();
		sm.setContent(descr);
		sm.setStatus(Status.UNDREAD);
		this.smdao.create(sm);
		
		List<SimpleMessage> smlist = smdao.getMessageList();
		model.addAttribute("smlist",smlist);
		return "simplemessage";
	}
	
	@RequestMapping(value="/remove/{id}", method = RequestMethod.GET)
	public String deleteMessage(Model model, @PathVariable("id") int id) {
		this.smdao.delete(id);
		
		List<SimpleMessage> smlist = this.smdao.getMessageList();
		model.addAttribute("smlist",smlist);
		return "redirect:/api/sm/";
	}
	
	@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
	public String editeMessage(Model model, @PathVariable("id") int id) {
		SimpleMessage simplemessage = this.smdao.getMessageById(id);
		System.out.println(simplemessage.getContent());
		List<SimpleMessage> smlist = this.smdao.getMessageList();
		
		model.addAttribute("simplemessage",simplemessage);
		model.addAttribute("smlist",smlist);
		return "simplemessage";
	}

}

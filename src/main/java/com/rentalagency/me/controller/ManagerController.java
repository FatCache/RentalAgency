package com.rentalagency.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.Request;

@Controller
@RequestMapping("/manager/")
public class ManagerController {
	
	@Autowired
	QueryDAO querydao;
	
	@RequestMapping(value="/manage/parkingrequest",method=RequestMethod.GET)
	public String viewRequest(Model model) {
		List<Request> prl = querydao.getRequestList();
		model.addAttribute("rList", prl);
		return "managerview";
	}
	
	@RequestMapping(value="/manage/parkingrequest/remove/{id}",method=RequestMethod.GET)
	public String deleteRequest(Model model,
			@PathVariable("id") int id) {
		querydao.deleteRequestById(id);
		return "redirect:/manager/manage/parkingrequest";
		
	}

}

package com.rentalagency.me.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rentalagency.me.Utilities;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.User;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	QueryDAO querydao;
	
	// User Creates Request
	@RequestMapping(value="/request/create",method=RequestMethod.GET)
	public String requestForm(Model model, HttpSession session) {
		User user = new User();
		user.setUser_id(4);
		session.setAttribute("user", user);
		
		model.addAttribute("parkingrequest", new ParkingRequest());
		model.addAttribute("rowspot", rowSpot.values());
		model.addAttribute("colspot", colSpot.values());
		
		return "requestview";
	}
	
	@RequestMapping(value="/request/create/*",method=RequestMethod.POST)
	public String requestSubmit(Model model, @ModelAttribute("parkingrequest") ParkingRequest prq,
			BindingResult result,
			@RequestParam(value="rsp",required=false) String rsp,
			@RequestParam(value="csp",required=false) String csp,
			@RequestParam(value="startTime", required=false) String startTime) {
		
		prq.setCsp(colSpot.valueOf(csp));
		prq.setRsp(rowSpot.valueOf(rsp));
		prq.setStartTime(Utilities.getDate(startTime));
		
		querydao.submitRequestById(prq, prq.getUser_id());

		
		
		
		return "index";
	}
	

}

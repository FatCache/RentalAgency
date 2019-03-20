package com.rentalagency.me.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.rentalagency.me.Utilities;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.exception.ResourceNotFoundException;
import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

@Controller
@RequestMapping("/user/")
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	QueryDAO querydao;
	
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(SessionStatus status) {
		
		return "dashboard/regular";
	}
	
	// User Creates Request
	@RequestMapping(value="/request/create",method=RequestMethod.GET)
	public String requestForm(Model model, HttpSession session) {
		
		model.addAttribute("parkingrequest", new ParkingRequest());
		model.addAttribute("rowspot", rowSpot.values());
		model.addAttribute("colspot", colSpot.values());
		
		return "regular/createrequest";
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
		
		return "redirect:/user/dashboard";
	}
	
	
	@RequestMapping(value="/request/view/{id}",method=RequestMethod.GET)
	public String requestSubmit(Model model,
			@PathVariable("id") int user_id) {
		
		List<ParkingRequest> rl = querydao.getParkingRequestListByUserId(user_id);
		model.addAttribute("rList",rl);
		
		return "regular/requestview";
	}
	

}

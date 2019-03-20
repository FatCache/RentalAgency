package com.rentalagency.me.controller;


import java.util.List;

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

import com.rentalagency.me.Utilities;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;

/**
 * Controller to handle the logic pertaining to the users associated 
 * with Regular Role. Delegated from the LoginController
 * @author abdusamed
 *
 */
@Controller
@RequestMapping("/user/")
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	QueryDAO querydao;
	
	/**
	 * Defaults to the view of dashboard for user with role Regular
	 * @return
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard() {
		return "dashboard/regular";
	}
	
	/**
	 * Upon the request of 'to create parking request', the form is populated
	 * with Parking Request object. This method makes use of SpringFramework
	 * Form tag
	 * @URL https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/view.html#view-jsp-formtaglib-formtag
	 * Set of enums is retrieved from the object class to populate the 
	 * selection field, Row Spot & Column SPot
	 * @param model
	 * @return returns populated create request page
	 */
	@RequestMapping(value="/request/create",method=RequestMethod.GET)
	public String requestForm(Model model) {
		
		model.addAttribute("parkingrequest", new ParkingRequest());
		model.addAttribute("rowspot", rowSpot.values());
		model.addAttribute("colspot", colSpot.values());
		
		return "regular/createrequest";
	}
	
	/**
	 * Saves the new parking request
	 * Upon POST request to the method, the methods begins to fill in attributes
	 * of Parking Request before sending it to the DAO Class
	 * @param prq ParkingRequst object created by the form tag
	 * @param result binds form object to ParkingRequst object
	 * @param rsp parses into Enum RowSpot
	 * @param csp parses into Enum ColumnSpot
	 * @param startTime HTML time received which is parsed using Utility Library
	 * @return returns back to the dashboard
	 */
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
	
	
	/**
	 * Retrieves list of parking request created by the user mapped to user_id
	 * @param user_id user_id used to get specific parking request
	 * @return request view
	 */
	@RequestMapping(value="/request/view/{id}",method=RequestMethod.GET)
	public String requestSubmit(Model model,
			@PathVariable("id") int user_id) {
		
		List<ParkingRequest> rl = querydao.getParkingRequestListByUserId(user_id);
		model.addAttribute("rList",rl);
		
		return "regular/requestview";
	}
	

}

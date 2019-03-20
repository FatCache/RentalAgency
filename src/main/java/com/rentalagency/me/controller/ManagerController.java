package com.rentalagency.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.rentalagency.me.dao.LoginDAO;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

@Controller
@RequestMapping("/manager/")
@SessionAttributes("user")
public class ManagerController {
	
	@Autowired
	QueryDAO querydao;
	
	@Autowired
	LoginDAO logindao;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(SessionStatus status) {
		return "dashboard/manager";
	}
	
	/*
	 * Parking Request retrieval & removal
	 */
	
	@RequestMapping(value="/request/parkingrequest/view",method=RequestMethod.GET)
	public String viewRequest(Model model) {
		List<Request> prl = querydao.getRequestList();
		model.addAttribute("rList", prl);
		return "manager/requestview";
	}
	
	@RequestMapping(value="/request/parkingrequest/remove/{id}",method=RequestMethod.GET)
	public String deleteRequest(Model model,
			@PathVariable("id") int id) {
		querydao.deleteRequestById(id);
		return "redirect:/useraccount/view";	
	}
	
	/*
	 * User account Request retrieval & Removal logic.
	 * Deleting a user account deletes the user 
	 */
	
	@RequestMapping(value="/useraccount/view",method=RequestMethod.GET)
	public String getUserAccount(Model model) {
		List<UserAccount> uas = querydao.getListOfUserAccount();
		model.addAttribute("useraccounts", uas);
		model.addAttribute("pageType", "useraccountview");
		return "manager/userview";
	}
	
	@RequestMapping(value="/useraccount/view/remove/{id}",method=RequestMethod.GET)
	public String deleteUser(Model model,
			@PathVariable("id") int id) {
		model.addAttribute("pageType", "useraccountview");
		logindao.deleteUserAccountById(id);
		
		return "redirect:/manager/useraccount/view";	
	}
	
	/*
	 * User list retrieval.
	 * Deleting a user account deletes the user 
	 */
	
	@RequestMapping(value="/user/view",method=RequestMethod.GET)
	public String getUser(Model model) {
		List<User> uas = querydao.getListOfUsers();
		model.addAttribute("useraccounts", uas);
		model.addAttribute("pageType", "userview");
		return "manager/userview";
	}

}

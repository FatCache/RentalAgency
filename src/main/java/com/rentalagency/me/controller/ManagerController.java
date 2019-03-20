package com.rentalagency.me.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import com.rentalagency.me.dao.LoginDAO;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.exception.ResourceNotFoundException;
import com.rentalagency.me.model.Message;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

/**
 * Controller to handle the logic pertaining to a user associated 
 * with a manager role
 * 
 * @author abdusamed
 *
 */
@Controller
@RequestMapping("/manager/")
@SessionAttributes("user")
public class ManagerController {
	
	/**
	 * USERVIEW for cases involving User object
	 * USERACCOUNTVIEW for cases involving UserAccount object
	 */
	private final static String USERVIEW = "userview";
	private final static String USERACCOUNTVIEW = "useraccountview";
	
	@Autowired
	QueryDAO querydao;
	
	@Autowired
	LoginDAO logindao;
	
	/**
	 * Base method to return dashboard manager 
	 * works as a 'homepage' for manager users.
	 * @param status
	 * @return view name to dashboard manager
	 */
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard() {
		return "dashboard/manager";
	}
	
	/**
	 * If Manager request a view of all request made by all users,
	 * this method is called and filling in the appropriate views 
	 * @param model
	 * @return view name request page
	 */
	@RequestMapping(value="/request/parkingrequest/view",method=RequestMethod.GET)
	public String viewRequest(Model model) {
		List<Request> prl = querydao.getRequestList();
		model.addAttribute("rList", prl);
		return "manager/requestview";
	}
	
	/**
	 * Response created when a manager creates a request for Parking request to be 
	 * deleted, indicating it has been resolved
	 * @param id request id sent part of URL parameter
	 * @return redirects to manager/requestview
	 */
	@RequestMapping(value="/request/parkingrequest/remove/{id}",method=RequestMethod.GET)
	public String deleteRequest(Model model,
			@PathVariable("id") int id) {
		querydao.deleteRequestById(id);
		return "redirect:/manager/requestview";	
	}
	
	/*
	 * Retrieve User *account* list without manager role and resolve view 
	 */
	
	@RequestMapping(value="/useraccount/view",method=RequestMethod.GET)
	public String getUserAccount(Model model) {
		List<UserAccount> uas = querydao.getListOfUserAccountRegular();
		model.addAttribute("useraccounts", uas);
		model.addAttribute("pageType", "useraccountview");
		return "manager/userview";
	}
	
	
	/*
	 * Retrieve User list without manager role and resolve view
	 */
	
	@RequestMapping(value="/user/view",method=RequestMethod.GET)
	public String getUser(Model model) {
		List<User> uas = querydao.getListOfUsers();
		model.addAttribute("users", uas);
		model.addAttribute("pageType", "userview");
		return "manager/userview";
	}
	
	/*
	 * Delete user account using user_id. Takes request from either view
	 * Useraccount view or User view.
	 */
	@RequestMapping(value="/useraccount/view/remove/{id}",method=RequestMethod.GET)
	public String deleteUser(Model model,
			@PathVariable("id") int id,
			@RequestParam("viewtype") String viewtype,
			HttpServletResponse response) {
		logindao.deleteUserAccountById(id);
		
		if(viewtype.equals(USERACCOUNTVIEW)) {
			return "redirect:/manager/useraccount/view";
		} 
		else if(viewtype.equals(USERVIEW)) {
			model.addAttribute("pageType", "userview");
			return "redirect:/manager/user/view";
		}
		
		throw new ResourceNotFoundException(); 
	}
	
	/**
	 * Custom Exception Handling for this class
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	public String handleResourceNotFoundException() {
	        return "error";
	    }
	
	
	@RequestMapping(value = "api/useraccount/view", method = RequestMethod.GET)
	public String messageGetJSON(Model model, @PathVariable String user) {
		List<UserAccount> uas = querydao.getListOfUserAccountRegular();
		model.addAttribute("uas", uas);
		return "jsonTemplate";
	}
	
	

}

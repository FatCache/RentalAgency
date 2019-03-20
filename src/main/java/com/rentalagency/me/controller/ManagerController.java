package com.rentalagency.me.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rentalagency.me.dao.LoginDAO;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.exception.ResourceNotFoundException;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.User.Role;
import com.rentalagency.me.model.UserAccount;

/**
 * Controller to handle the logic pertaining to the users associated 
 * with Manager role
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
	
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
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
		return "redirect:/manager/request/parkingrequest/view";	
	}
	
	/**
	 * Populates the userview page with list of user regular user accounts
	 * and sets up pagetype to reflect it
	 * @return user view populated with user account objects & page type to \
	 * reflect it
	 */
	@RequestMapping(value="/useraccount/view",method=RequestMethod.GET)
	public String getUserAccount(Model model) {
		List<UserAccount> uas = querydao.getListOfUserAccountRegular();
		model.addAttribute("useraccounts", uas);
		model.addAttribute("pageType", "useraccountview");
		return "manager/userview";
	}
	
	
	/**
	 * Populates the userview page with list of all  users
	 * and sets up pagetype to reflect it
	 * @return user view populated with user objects & page type to userview
	 */
	@RequestMapping(value="/user/view",method=RequestMethod.GET)
	public String getUser(Model model) {
		List<User> uas = querydao.getListOfUsers();
		model.addAttribute("users", uas);
		model.addAttribute("pageType", "userview");
		return "manager/userview";
	}
	
	/**
	 * Handles the logic of deleting used account by ID.
	 * Returns the user back to appropriate view from where originally
	 * came from 
	 * @param id user_id mapped to the user account 
	 * @param viewtype indicates origin of the the request
	 * @return returns view which depends on source view
	 */
	@RequestMapping(value="/useraccount/view/remove/{id}",method=RequestMethod.GET)
	public String deleteUser(Model model,
			@PathVariable("id") int id,
			@RequestParam("viewtype") String viewtype) {
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
	 * Custom Exception Handler mapped to ResourceNotFoundException class
	 * Logs a generic error when thrown
	 * Returns a view titled 'error' which currently is a JSP page which acts
	 * as a place ho
	 * 
	 *  Example method to Invoke: Request /api/useraccount/view/
	 *  without logged in or logged as user without Manager role
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFoundException() {
		logger.warn("Error Occured in the request");
	    return "error";	    
	}
	
	
	/**
	 * Basic API call to the /manager/useraccount/view
	 * Creates a JSON response which can be consumed for example by 
	 * Javascript frameworks - React, Angular
	 * Has authentication check which makes the caller is 
	 * 	1) Authenticated as being logged in
	 * 	2) Caller belongs to the manager class
	 * 
	 * @param model
	 * @param session
	 * @return JSON representation of Non Manager UserAccount list  
	 */
	@RequestMapping(value = "api/useraccount/view", method = RequestMethod.GET)
	public String messageGetJSON(Model model,HttpSession session) {
		if(session.getAttribute("user") == null) {
			logger.warn("Error: User not logged in");
			throw new ResourceNotFoundException();
		}
		UserAccount user = (UserAccount) session.getAttribute("user");
		if(!user.getUser().getRole().equals(Role.MANAGER)) {
			logger.warn("Error: Access Forbidden");
			throw new ResourceNotFoundException();
		}
		List<UserAccount> uas = querydao.getListOfUserAccountRegularJSON();
		
		model.addAttribute("uas", uas);
		return "jsonTemplate";
	}
	
	

}

package com.rentalagency.me.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.rentalagency.me.bean.LoginBean;
import com.rentalagency.me.dao.LoginDAO;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.Message;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.User.Role;
import com.rentalagency.me.model.UserAccount;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("user")
public class HomeController {

	@Autowired
	LoginDAO logindao;
	
	@Autowired
	QueryDAO querydao;
	

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/login";
	}

	/*
	 * @RequestParam instead of HttpServletRequest to avoid multiple
	 * request.parameter(...)
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(Model model, HttpSession session,
			@RequestParam(value = "username", required = false) String email,
			@RequestParam(value = "password", required = false) String password) {
		LoginBean lb = new LoginBean();
		lb.setEmail(email);
		lb.setPass(password);

		if (LoginDAO.validate(lb)) {
			UserAccount ua = logindao.getUserAccountByUserName(email);
			// Retrieve Role
			User user = querydao.getUserById(ua.getUser_id());
			session.setAttribute("user", ua);
			model.addAttribute("status", "success");
			System.out.println(user.getRole());
			if(user.getRole().equals(Role.REGULAR)) {
				return "redirect:/user/dashboard";
			}
			else if(user.getRole().equals(Role.MANAGER)) {
				return "redirect:/manager/dashboard";
			}					
			
			return "dashboard";
		}
		model.addAttribute("pagetype","login");
		model.addAttribute("status", "invalid");
		return "login";
	}

	// Return Login Page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("pagetype","login");
		model.addAttribute("status", null);
		return "login";
	}

	/*
	 * Message list for a user populates the Table
	 */

	@RequestMapping(value = "{user}/message", method = RequestMethod.GET)
	public String messageGet(Model model, @PathVariable String user) {

		List<Message> msgList = new ArrayList<Message>();

		model.addAttribute("msgList", msgList);
		model.addAttribute("pageType", "message");

		return "dashboard";
	}

	/*
	 * Message List as JSON Response
	 */
	@RequestMapping(value = "api/{user}/message", method = RequestMethod.GET)
	public String messageGetJSON(Model model, @PathVariable String user) {
		System.out.println("User Name:" + user);
		List<Message> msgList = new ArrayList<Message>();

		for (int i = 0; i < 10; i++) {
			msgList.add(new Message("Message No. " + i));
		}

		model.addAttribute("msgList", msgList);
		return "jsonTemplate";
	}
	
	// Submits 'register' pagetype attribute to login page
	@RequestMapping(value = "useraccount/register", method = RequestMethod.GET)
	public String createAccount(Model model) {
		// Get role set
		model.addAttribute("roles",Role.values());
		model.addAttribute("pagetype","register");
		return "login";

	}

	@RequestMapping(value = "useraccount/register", method = RequestMethod.POST)
	public String createAccount(Model model, 
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "roletype") String role) {
		
		UserAccount ua = new UserAccount();
		ua.setUsername(username);
		ua.setPassword(password);
		System.out.println(role);
		logindao.create(ua,role);
		
		model.addAttribute("pagetype","login");
		model.addAttribute("status", "success_register");
		return "login";

	}

	// Change Password
	@RequestMapping(value = "useraccount/changepassword", method = RequestMethod.GET)
	public String changePasswordGet(Model model, HttpSession session) {
		UserAccount ua = (UserAccount) session.getAttribute("user");
		if(ua == null ) {
			model.addAttribute("message","Please login first");
			return "redirect:/login";
		}
		model.addAttribute("pagetype", "changepassword");
		return "login";

	}

	@RequestMapping(value = "useraccount/changepassword", method = RequestMethod.POST)
	public String changePasswordPost(Model model,
			@RequestParam("password") String password,
			@RequestParam("username") String username,
			@RequestParam("user_id") int user_id) {
		logindao.modifyUserAccountById(user_id, username, password);
		model.addAttribute("pagetype","changepassword");
		return "login";

	}
	
	// Logout
	// Return Login Page
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/login";
	}

}

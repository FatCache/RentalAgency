package com.rentalagency.me.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.rentalagency.me.bean.LoginBean;
import com.rentalagency.me.dao.LoginDAO;
import com.rentalagency.me.model.Message;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	LoginDAO logindao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/*
	 * @RequestParam instead of HttpServletRequest to avoid 
	 * multiple request.parameter(...)
	 */
	@RequestMapping(value="/loginValidate", method = RequestMethod.POST)
	public String loginUser(Model model,
			@RequestParam(value="email", required=false) String email, 
	        @RequestParam(value="password", required=false) String password) {
		LoginBean lb = new LoginBean();
		lb.setEmail(email);
		lb.setPass(password);
		
		if(LoginDAO.validate(lb)) {
			model.addAttribute("status", "true");
			return "login";
		}

		model.addAttribute("status", "false");
		return "login";
	}
	
	// Return Login Page
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("status", null);
		return "login";
	}
	
//	@RequestMapping(value="api/userList/", method = RequestMethod.GET)
//	public String jsonFun(Model model) {
//		model.addAttribute("users",getUsers());
//		return "jsonTemplate";
//	}
	
	/*
	 * Message list for a user populates the Table
	 */
	
	@RequestMapping(value="{user}/message", method = RequestMethod.GET)
	public String messageGet(Model model, @PathVariable String user) {
		
		List<Message> msgList = new ArrayList<Message>();
				
		model.addAttribute("msgList", msgList);
		model.addAttribute("pageType", "message");
		
		return "dashboard";	
	}
	
	
	/*
	 * Message List as JSON Response
	 */
	@RequestMapping(value="api/{user}/message", method = RequestMethod.GET)
	public String messageGetJSON(Model model, @PathVariable String user) {
		System.out.println("User Name:" + user);
		List<Message> msgList = new ArrayList<Message>();
		
		for(int i = 0; i < 10; i++ ) {			
			msgList.add(new Message("Message No. " + i));
		}
		
		model.addAttribute("msgList", msgList);
		return "jsonTemplate";	
	}
	
	@RequestMapping(value="useraccount/register", method = RequestMethod.POST)
	public String createAccount(Model model,
			@RequestParam(value="username") String username, 
			@RequestParam(value="password") String password) {
		UserAccount ua = new UserAccount();
		
		ua.setUsername(username);
		ua.setPassword(password);

		logindao.create(ua);
		
		return "login";
		
	}
	@RequestMapping(value="useraccount/register", method = RequestMethod.GET)
	public String createAccount() {
		return "login";
		
	}
	
	
}

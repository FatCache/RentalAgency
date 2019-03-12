package com.rentalagency.me.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rentalagency.me.model.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
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
	
	public List<User> getUsers(){
		User u1 = new User("Abdusamed");
		User u2 = new User("Abdusamed");
		User u3 = new User("Abdusamed");
		
		List<User> users = new ArrayList<User>();
		
		users.add(u1);
		users.add(u1);
		users.add(u1);
		
		return users;
	}
	

	
	@RequestMapping(value="/json", method = RequestMethod.GET)
	public @ResponseBody List<User> jsonFun() {
		
		return getUsers();
	}
	
	@RequestMapping(value="/jsonXML", method = RequestMethod.GET)
	public String jsonFun(Model model) {
		model.addAttribute("users",getUsers());
		return "jsonTemplate";
	}
	
}

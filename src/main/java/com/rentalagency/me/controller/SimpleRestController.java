package com.rentalagency.me.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentalagency.me.model.User;

@RestController
@RequestMapping("/api/")
public class SimpleRestController {
	
	@RequestMapping(value="/users")
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
	

}

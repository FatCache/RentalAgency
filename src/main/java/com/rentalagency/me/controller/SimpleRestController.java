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
		List<User> users = new ArrayList<User>();
		
		return users;
	}
	

}

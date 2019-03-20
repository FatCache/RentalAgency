package com.rentalagency.me.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.rentalagency.me.bean.LoginBean;
import com.rentalagency.me.dao.LoginDAO;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.User.Role;
import com.rentalagency.me.model.UserAccount;

/**
 * Login Controller to handle views pertaining to the login page
 * SessionAttributes annotation used for session management of
 * currently logged in user.
 * 
 * @author abdusamed
 *
 */
@Controller
@SessionAttributes("user")
public class LoginController {

	
	@Autowired
	LoginDAO logindao;
	
	@Autowired
	QueryDAO querydao;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * Returns the default login page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/login";
	}

	/**
	 * Handles the main logic of use login authentication
	 * Covers the follow situation
	 * 	1) If successful login, redirect user to the 
	 * 	   appropriate dash board
	 *  2) If tried to login in but invalid, change the
	 *     model attributes to reflect it 
	 *     
	 * @session Initializes the session at login
	 * @param email Email submitted in the login form
	 * @param password Email submitted in the login form
	 * @return Returns a view name
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(Model model, HttpSession session,
			@RequestParam(value = "username", required = false) String email,
			@RequestParam(value = "password", required = false) String password) {
		LoginBean lb = new LoginBean();
		lb.setEmail(email);
		lb.setPass(password);

		if (LoginDAO.validate(lb)) {
			// Query the database to retrieve the objects
			UserAccount ua = logindao.getUserAccountByUserName(email);
			User user = querydao.getUserById(ua.getUser_id());
			
			session.setAttribute("user", ua);
			model.addAttribute("status", "success");
			
			// Return the corresponding view
			if(user.getRole().equals(Role.REGULAR)) {
				return "redirect:/user/dashboard";
			}
			else if(user.getRole().equals(Role.MANAGER)) {
				return "redirect:/manager/dashboard";
			}					
			
		}
		// If Invalid, return to try again
		model.addAttribute("pagetype","login");
		model.addAttribute("status", "invalid");
		return "login";
	}

	/**
	 * Resolves view for /login Get call
	 * @return login view
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("pagetype","login");
		model.addAttribute("status", null);
		return "login";
	}

	/**
	 * Populates form with Roles mentioned in the User class and changes
	 * the pagetype to register
	 * @param model
	 * @return login view
	 */
	@RequestMapping(value = "useraccount/register", method = RequestMethod.GET)
	public String createAccount(Model model) {
		// Get role set
		model.addAttribute("roles",Role.values());
		model.addAttribute("pagetype","register");
		return "login";

	}

	/**
	 * Saves the user account credentials to the database
	 * 
	 * @param model
	 * @param username User submitted username in the form
	 * @param password User submitted password the form
	 * @param role 	   User submitted role in the form	
	 * @return login view with additional attributes required for
	 * 		   redirection logic implemented
	 */
	@RequestMapping(value = "useraccount/register", method = RequestMethod.POST)
	public String createAccount(Model model, 
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "roletype") String role) {
		
		UserAccount ua = new UserAccount();
		ua.setUsername(username);
		ua.setPassword(password);
		
		logindao.create(ua,role);
		
		model.addAttribute("pagetype","login");
		model.addAttribute("status", "success_register");
		return "login";

	}

	/**
	 * Handles the request when the user request password to be changed
	 * @param session contains session 'User' to verify user authentication
	 * @return login type as 'changepassword'
	 */
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

	/**
	 * User submits the form to change the account password
	 * @param password form submitted password
	 * @param username form submitted username
	 * @param user_id  user_id retrieved from the session
	 * @return
	 */
	@RequestMapping(value = "useraccount/changepassword", method = RequestMethod.POST)
	public String changePasswordPost(Model model,
			@RequestParam("password") String password,
			@RequestParam("username") String username,
			@RequestParam("user_id") int user_id) {
		
		logindao.modifyUserAccountById(user_id, username, password);
		model.addAttribute("pagetype","changepassword");
		return "login";
	}
	
	/**
	 * Restores the state of the session back to normal
	 * and removes any attributes associated with it 
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/login";
	}

}

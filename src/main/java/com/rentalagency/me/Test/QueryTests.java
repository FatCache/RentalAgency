package com.rentalagency.me.Test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.UserAccount;

public class QueryTests {

	private ConfigurableApplicationContext context;

	private QueryDAO querydao;


	@Before
	public void initApplicationContext() {
		context = new ClassPathXmlApplicationContext("/servlet-context.xml");

		System.out.println(context.getBeanDefinitionCount());
		querydao = context.getBean(QueryDAO.class);
	}

	@After
	public void closeApplicationContext() {
		if (context != null) {
			context.close();
			context = null;
		}
	}

	// @Test
	public void notNull() {
		assert (context != null);
		assert (querydao != null);
	}
	
//	@Test
	public void submitRequestByIdTest() {
		ParkingRequest prq = new ParkingRequest();
		prq.setDescription("Parking Request from Test for account 2");
		prq.setCsp(colSpot.E);
		prq.setRsp(rowSpot.TWO);
		prq.setStatus(false);
		querydao.submitRequestById(prq,2);
	}


	
//	@Test
	public void getListOfUsersTest() {
		List<User> l = querydao.getListOfUsers();
		assert(l.size() != 0);
		System.out.println(l.size());
		for(User u : l) {
			System.out.println(u.getUser_id());
		}
	}

//	@Test
	public void getListOfUserAccount() {
		List<User> l = querydao.getListOfUserAccount();
		assert(l.size() != 0);
	}
	
	/*
	 * Parking Request Test
	 */
//	@Test
	public void getParkingRequestList() {
		List<Request> prl = querydao.getRequestList();
		for(Request p : prl) {
			System.out.println(p.getDescription());
		}
		assert (prl.size() != 0);
	}
	
//	@Test
	public void getParkingRequestByUserIdTest() {
		List<ParkingRequest> prl = querydao.getParkingRequestListByUserId(2);		
		assert (prl.size() != 0);
	}
	
	/*
	 * Creates and deletes a temporary Parking Request
	 */
//	@Test
	public void deleteRequestById() {
		ParkingRequest prq = new ParkingRequest();
		prq.setDescription("Temporary Request to be deleted 2");
		prq.setCsp(colSpot.E);
		prq.setRsp(rowSpot.TWO);
		prq.setStatus(true);
		querydao.submitRequestById(prq,2);
		
		// Obtain Request newly assigned request_id identifier
		ParkingRequest prqUpdated = querydao.getParkingRequestByUserIdByDescription(2,prq.getDescription());
		System.out.println(prqUpdated);
 
		assert(prqUpdated != null);
		
		// Delete Request by Id
		querydao.deleteRequestById(prqUpdated.getRequest_id());
		
		ParkingRequest nullRequest = querydao.getParkingRequestById(prqUpdated.getRequest_id());
		
		assert(nullRequest == null);
		
	}
	
	/*
	 * Testing the name so it not null for account number 2
	 */
//	@Test
	public void getUserByIdTest() {
		User us = querydao.getUserById(2);
		
		assert(us.getName() != null);
		
	}
	
	/*
	 * Test Useraccount retrieved are NON MANAGER role
	 */
//	@Test
//	public void getUserAccountNonManagerTest() {
//		List<UserAccount> ul = querydao.getListOfUserAccountRegular();
//		assert(ul.size() != 0);
//	}
}

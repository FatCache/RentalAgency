package com.rentalagency.me;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rentalagency.me.dao.LoginDAO;
import com.rentalagency.me.dao.QueryDAO;
import com.rentalagency.me.model.ParkingRequest;
import com.rentalagency.me.model.ParkingRequest.colSpot;
import com.rentalagency.me.model.ParkingRequest.rowSpot;
import com.rentalagency.me.model.Request;
import com.rentalagency.me.model.User;
import com.rentalagency.me.model.User.Role;
import com.rentalagency.me.model.UserAccount;

/**
 * Test cases intended to check the functionality 
 * of the class {@link LoginDAO}
 * 
 * @author abdusamed 
 * 
 */
public class QueryTests {

	private static ConfigurableApplicationContext context;

	private static QueryDAO querydao;
	private static LoginDAO logindao;
	private static int user_id;
	private static UserAccount testAccount;
	private User user;

	private final static Logger log = LoggerFactory.getLogger(QueryTests.class);

	@BeforeClass
	public static void initApplicationContext() {
		log.info("Starting jUnit Tests ...");
		context = new ClassPathXmlApplicationContext("classpath*:**/servlet-context.xml");
		
		querydao = context.getBean(QueryDAO.class);
		logindao = context.getBean(LoginDAO.class);
		
	}

	@AfterClass
	public static void closeApplicationContext() {
		log.info("Ending jUnits Tests... ");
		if (context != null) {
			context.close();
			context = null;
		}
	}

	@Test
	public void initializeVariables() {
		assertNotNull(context);
		assertNotNull(querydao);
	}
	
	/**
	 * Create Test user account and save it to the database using LoginDAO
	 * Retrieve its unique user_id assigned by hibernate via QueryDAO
	 * after saved. Check if user_id not null afterwards
	 */
	@Test
	public void createTestUserAccount() {
		assert(user_id == 0);
		
		testAccount = new UserAccount();
		testAccount.setUsername("test");
		testAccount.setPassword("test");
		
		logindao.create(testAccount, Role.MANAGER.getValue());
		
		user_id = querydao.getIdByUserAccount(testAccount);
		
		log.info("USER_ID ASSIGNED................." + user_id);
		assertNotNull(user_id);
		
	}
	
	
	@Test
	public void submitRequestByIdTest() {
		ParkingRequest prq = new ParkingRequest();
		prq.setDescription("Test Submit of Request");
		prq.setStartTime(new Date());
		prq.setCsp(colSpot.E);
		prq.setRsp(rowSpot.TWO);
		prq.setStatus(false);
		querydao.submitRequestById(prq,user_id);
		ParkingRequest test_prq = querydao.getParkingRequestByUserIdByDescription(user_id, prq.getDescription());
		
		assert(test_prq.getCsp().equals(prq.getCsp()));
		assert(test_prq.getRsp().equals(prq.getRsp()));
		
		// Cleanup: Delete the Request
		int request_id = test_prq.getRequest_id();
		querydao.deleteRequestById(request_id);
		
	}


	
	@Test
	public void getListOfUsersTest() {
		List<User> l = querydao.getListOfUsers();
		assert(l.size() != 0);
	}

	@Test
	public void getListOfUserAccount() {
		List<User> l = querydao.getListOfUserAccount();
		assert(l.size() != 0);
	}
	
	/*
	 * Parking Request Test
	 */
	@Test
	public void getParkingRequestList() {
		List<Request> prl = querydao.getRequestList();
		assert (prl.size() != 0);
	}
	
	
	
	
	@Test
	public void deleteAccountTest() {
		logindao.batchDeleteAccounts(testAccount.getUsername());
		List<ParkingRequest> prq = querydao.getParkingRequestListByUserId(user_id);
		assert(true);
	}
	
}
	
	

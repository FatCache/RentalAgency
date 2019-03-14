package com.rentalagency.me.dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rentalagency.me.model.SimpleMessage;

/**
 * Data persistence without using Spring Boot @Transaction && Repository
 * 
 * @author abdusamed
 *
 */
public class SimpleMessageDAO extends DAO {

	private final static Logger log = LoggerFactory.getLogger(SimpleMessageDAO.class);

	public void create(SimpleMessage sm) {
		try {
			begin();
			getSession().persist(sm);
			commit();
			log.info("Simple Message with this content added: " + sm.getContent());
		} catch (HibernateException e) {
			log.warn("Unable To Add Message, Failure");
			rollback();
		} finally {
			close();
		}
	}

	public void update(SimpleMessage sm) {
		try {
			begin();
			getSession().update(sm);
			commit();
			log.info("Simple Message with this content updated: " + sm.getContent());
		} catch (HibernateException e) {
			log.warn("Unable To Update Message, Failure");
			rollback();
		} finally {
			close();
		}
	}

	public SimpleMessage getMessageById(int id) {
		SimpleMessage sm = null;
		try {
			begin();
			/*
			 * Another way is to use Query to retrieves parameters of the data object 
			 * and populate fields of a Java object using setter methods
			 */
			sm = (SimpleMessage) getSession().load(SimpleMessage.class, new Integer(id));
			
			commit();
			log.info("Simple Message with loaded correctly against query: " + id);
		} catch (HibernateException e) {
			log.warn("Unable To Add Message, Failure");
			rollback();
		} finally {
			close();
		}
		
		return sm;
	}
	
	public void delete(int id) {
		try {
			begin();
			SimpleMessage sm = (SimpleMessage) getSession().load(SimpleMessage.class, new Integer(id));
			if(sm != null) {
				getSession().delete(sm);
			}
			commit();
			log.info("Simple Message with DELETED against query: " + id);
		} catch (HibernateException e) {
			log.warn("Unable To DELETE Message, Failure");
			rollback();
		} finally {
			close();
		}
	}
	
	

	public List<SimpleMessage> getMessageList() {
		List<SimpleMessage> l = null;
		try {
			begin();
			l = getSession().createQuery("from SimpleMessage").list();
			log.info("Simple Message List Retreived");
		} catch (HibernateException e) {
			log.warn("Unable To Retrieve Message List, Failure");
			rollback();
		} finally {
			close();
		}

		return l;

	}

}

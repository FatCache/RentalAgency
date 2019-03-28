package com.rentalagency.me.dao;

import org.hibernate.HibernateException;

import com.rentalagency.me.model.Avatar;

public class AvatarDAO extends DAO {
	
	/**
    * Inserts a row in the Avatar table.
    * Do not need to pass the id, it will be generated.
    * @param avatar
    * @return an instance of the object Avatar
    */
   public Avatar saveAvatar(Avatar avatar)
   {
       
       try {
           begin();
           getSession().save(avatar);
           commit();
       } catch (HibernateException e) {
           rollback();
           e.printStackTrace();
       } finally {
           close();
       }
       return avatar;
   }

   /**
    * Get an Avatar from database
    * @param avatarId id of the book to be retrieved
    */
   public Avatar getAvatar(Integer avatarId)
   {
      
       try {
           Avatar avatar = (Avatar) getSession().get(Avatar.class, avatarId);
           return avatar;
       } catch (HibernateException e) {
           e.printStackTrace();
       } finally {
           getSession().close();
       }
       return null;
   }
	

}

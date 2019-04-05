package com.rentalagency.me;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rentalagency.me.dao.AvatarDAO;
import com.rentalagency.me.model.Avatar;

public class AvatarTest {

	private static AvatarDAO avatarDAO;
	 
    @BeforeClass
    public static  void runBeforeClass() {
    	avatarDAO = new AvatarDAO();
    }
 
    @AfterClass
    public static void runAfterClass() {
    	avatarDAO = null;
    }
 
    /**
     * Test method for {@link com.loiane.dao.BookDAOImpl#saveBook()}.
     */
    @Test
    public void testSaveAvatar() {
    	
        //File file = new File("classpath*:**/avatarTest.jpg");
    	File file = new File("src/main/resources/avatarTest.jpg");

        byte[] bFile = new byte[(int) file.length()];
 
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getName());
 
        Avatar avatar = new Avatar();
        avatar.setName(file.getName());
        avatar.setImage(bFile);
 
        avatarDAO.saveAvatar(avatar);
 
        assertNotNull(avatar.getAvatarId());
    }
 
    /**
     * 
     */
    @Test
    public void testGetAvatar() {
 
        Avatar avatar = avatarDAO.getAvatar(5);
 
        assertNotNull(avatar);
        
        try{
            //FileOutputStream fos = new FileOutputStream("images\\output.jpg");  //windows
            FileOutputStream fos = new FileOutputStream("src/main/resources/Test"+avatar.getName());            
            fos.write(avatar.getImage());
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }        
        
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Singleton
@Startup
public class RegisterAdmin {

    @PersistenceContext
    EntityManager em;

    @EJB
    UserService usrSrv;

    @PostConstruct
    public void init() {
        checkAdmin1();
    }

    public void checkAdmin1() {
        String username = "admin1";

        try {
            SystemUser su = em.createQuery("SELECT c FROM SystemUser c WHERE c.username = :username", SystemUser.class)
                    .setParameter("username", username).getSingleResult();
            if (su.equals("admin1")) {
                System.out.println("Admin1 already exists");
            }
        } catch (NoResultException e) {
            registerAdmin1("admin1", "admin1", "admin", "one", "N/A", 0.0);
        }

    }

    public void registerAdmin1(String username, String userpassword, String name, String surname, String currency, double balance) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, balance);
            sys_user_group = new SystemUserGroup(username, "admins");

            em.persist(sys_user);
            em.persist(sys_user_group);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

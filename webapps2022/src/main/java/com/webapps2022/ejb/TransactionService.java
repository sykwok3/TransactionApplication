package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
@TransactionAttribute(REQUIRED)
public class TransactionService {

    @PersistenceContext(unitName = "WebappsDBPU")
    EntityManager em;

    public TransactionService() {
    }

    public void write(String username, double newBalance) {
        SystemUser su = em.createQuery("SELECT c FROM SystemUser c WHERE c.username = :username", SystemUser.class)
                .setParameter("username", username).getSingleResult();
        su.setBalance(newBalance);
        em.persist(su);
    }

    public double read(String username) {
        SystemUser su = em.createQuery("SELECT c FROM SystemUser c WHERE c.username = :username", SystemUser.class)
                .setParameter("username", username).getSingleResult();
        return su.getBalance();
    }

    public String readCurrency(String username) {
        SystemUser su = em.createQuery("SELECT c FROM SystemUser c WHERE c.username = :username", SystemUser.class)
                .setParameter("username", username).getSingleResult();
        return su.getCurrency();
    }

//    public boolean checkUser(String username) {
//        boolean result = false;
//        try {
//            SystemUser su = em.createQuery("SELECT c FROM SystemUser c WHERE c.username = :username", SystemUser.class)
//                    .setParameter("username", username).getSingleResult();
//            if (su.equals(username)) {
//                System.out.println("user exists");
//                result = true;
//            }
//        } catch (NoResultException e) {
//            result = false;
//        }
//        return result;
//    }

    public void mySubmit(String recipientUsername, double paymentAmount, String currentUsername, double originalAmount) {
        if (read(currentUsername) >= paymentAmount) {

            double currentUserNewBalance = read(currentUsername) - originalAmount;
            write(currentUsername, currentUserNewBalance);

            double recipientUserNewBalance = read(recipientUsername) + paymentAmount;
            write(recipientUsername, recipientUserNewBalance);
        } else {
            System.out.println("User does not exists");
        }
    }
}

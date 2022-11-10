package com.webapps2022.ejb;
import com.webapps2022.entity.Notifications;
import java.util.Date;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class NotificationServiceBean implements NotificationService {

    @PersistenceContext
    EntityManager em;

    public NotificationServiceBean() {
    }

    @Override
    public synchronized List<Notifications> getNotificationList(String currentUsername) {
        List<Notifications> records = em.createQuery("SELECT c FROM Notifications c WHERE c.fromUser = :fromUser OR c.toUser = :toUser", Notifications.class)
                .setParameter("fromUser", currentUsername).setParameter("toUser", currentUsername).getResultList();
        return records;
    }

    @Override
    public void insertNotification(String type, String currentUsername, String requestFromUsername, double paymentAmount, String senderCurrency, String recieverCurrency, Date timeStamp) {
        Notifications notifications = new Notifications(type, currentUsername, requestFromUsername, paymentAmount, senderCurrency, recieverCurrency, timeStamp);
        em.persist(notifications);
        System.out.println("Inserted the following notification:");
        System.out.println("Type: " + type);
        System.out.println("From User: " + currentUsername);
        System.out.println("Sender Currency: " + senderCurrency);
        System.out.println("To User: " + requestFromUsername);
        System.out.println("Amount: " + paymentAmount);
        System.out.println("Recipient Currency: " + recieverCurrency);
        System.out.println("Date: " + timeStamp);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Notifications: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Notifications: PreDestroy");
    }
}

package com.webapps2022.ejb;

import com.webapps2022.entity.TransactionRecord;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TransactionStorageServiceBean implements TransactionStorageService {

    @PersistenceContext
    EntityManager em;

    public TransactionStorageServiceBean() {
    }

    @Override
    public synchronized List<TransactionRecord> getRecordList(String currentUsername) {
        List<TransactionRecord> records = em.createQuery("SELECT c FROM TransactionRecord c WHERE c.fromUser = :fromUser OR c.toUser = :toUser", TransactionRecord.class)
                .setParameter("fromUser", currentUsername).setParameter("toUser", currentUsername).getResultList();
        return records;
    }

    @Override
    public synchronized List<TransactionRecord> getRecordListAdmin() {
        List<TransactionRecord> records = em.createQuery("SELECT c FROM TransactionRecord c", TransactionRecord.class)
                .getResultList();
        return records;
    }

    @Override
    public void insertRecord(String recipientUsername, double paymentAmount, String currentUsername, String fromUserCurrency, String toUserCurrency, Date timeStamp) {
        TransactionRecord record = new TransactionRecord(currentUsername, recipientUsername, paymentAmount, fromUserCurrency, toUserCurrency, timeStamp);
        em.persist(record);
        System.out.println("Inserted the following transaction record:");
        System.out.println("From User: " + currentUsername);
        System.out.println("To User: " + recipientUsername);
        System.out.println("Amount: " + paymentAmount);
        System.out.println("Sender Currency: " + fromUserCurrency);
        System.out.println("Recipient Currency: " + toUserCurrency);
        System.out.println("Date: " + timeStamp);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Transaction Record: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Transaction Record: PreDestroy");
    }
}

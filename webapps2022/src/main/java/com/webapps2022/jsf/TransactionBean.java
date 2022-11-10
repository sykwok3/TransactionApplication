package com.webapps2022.jsf;

import com.webapps2022.ejb.NotificationService;
import javax.ejb.EJB;
import javax.inject.Named;
import com.webapps2022.ejb.TransactionService;
import com.webapps2022.ejb.TransactionStorageService;
import com.webapps2022.ejb.UserService;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.TransactionRecord;
import com.webapps2022.thrift.TimeStampClient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author user
 */
@Named(value = "transactionBean")
@ConversationScoped

@DeclareRoles({"users", "admin"})
public class TransactionBean implements Serializable {

    @EJB
    TransactionService transactionService;

    @EJB
    TransactionStorageService store;

    @EJB
    NotificationService notificationService;

    @EJB
    UserService userservice;

    private String type;
    private String recipientUsername;
    private double paymentAmount;

    public TransactionBean() {
        this.type = "Payment Made";
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getCurrentUserName() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String currentUsername = request.getRemoteUser();
        return currentUsername;
    }

    @PermitAll
    public double getBalance() {
        return transactionService.read(getCurrentUserName());
    }

    @PermitAll
    public String getCurrency() {
        return transactionService.readCurrency(getCurrentUserName());
    }

    @PermitAll
    public String getrecipientCurrency() {
        return transactionService.readCurrency(recipientUsername);
    }

    public double getCurrentFx(String currency1, String currency2, double amount) {
        Client client = ClientBuilder.newClient();
        WebTarget conversionResource = client.target(
                "http://localhost:10000/webapps2022/conversion")
                .path("{currency1}")
                .resolveTemplate("currency1", currency1)
                .path("{currency2}")
                .resolveTemplate("currency2", currency2)
                .path("{amount}")
                .resolveTemplate("amount", amount);//String.valueOf(amount)
        Invocation.Builder builder = conversionResource.request(MediaType.APPLICATION_JSON);
        double response = builder.get(double.class);
        client.close();
        return response;
    }

    @PermitAll
    public void mySubmit() {
        transactionService.mySubmit(recipientUsername, getCurrentFx(getCurrency(), getrecipientCurrency(), paymentAmount), getCurrentUserName(), paymentAmount);
        store.insertRecord(recipientUsername, getCurrentFx(getCurrency(), getrecipientCurrency(), paymentAmount), getCurrentUserName(), getCurrency(), getrecipientCurrency(), getTimeStamp());
        notificationService.insertNotification(type, getCurrentUserName(), recipientUsername, getCurrentFx(getCurrency(), getrecipientCurrency(), paymentAmount), getCurrency(), getrecipientCurrency(), getTimeStamp());
    }

    @PermitAll
    public List<TransactionRecord> getRecordList() {
        return store.getRecordList(getCurrentUserName());
    }

    @RolesAllowed("admins")
    public List<TransactionRecord> getRecordListAdmin() {
        return store.getRecordListAdmin();
    }

    @RolesAllowed("admins")
    public List<SystemUser> getAllUsersAdmin() {
        return userservice.getRecordListAdmin();
    }

    public Date getTimeStamp() {
        TimeStampClient TSclient = new TimeStampClient();
        Date date = new Date(TSclient.startClient());
        return date;
    }

}

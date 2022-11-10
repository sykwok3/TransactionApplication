/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.NotificationService;
import com.webapps2022.ejb.TransactionService;
import com.webapps2022.entity.Notifications;
import com.webapps2022.thrift.TimeStampClient;
import javax.ejb.EJB;
import javax.inject.Named;
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
@Named(value = "requestBean")
@ConversationScoped
@DeclareRoles({"users", "admin"})
public class requestPaymentBean implements Serializable {

    private String type;
    private String requestFromUsername;
    private double requestAmount;

    @EJB
    NotificationService notificationService;

    @EJB
    TransactionService transactionService;

    public requestPaymentBean() {
        this.type = "Payment Request";
    }

    public String getRequestFromUsername() {
        return requestFromUsername;
    }

    public void setRequestFromUsername(String requestFromUsername) {
        this.requestFromUsername = requestFromUsername;
    }

    public double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(double requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getCurrentUserName() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String currentUsername = request.getRemoteUser();
        return currentUsername;
    }

    @PermitAll
    public String getCurrency() {
        return transactionService.readCurrency(getCurrentUserName());
    }

    @PermitAll
    public String getrecipientCurrency() {
        return transactionService.readCurrency(requestFromUsername);
    }

    public Date getTimeStamp() {
        TimeStampClient TSclient = new TimeStampClient();
        Date date = new Date(TSclient.startClient());
        return date;
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
    public void insertNotification() {
        notificationService.insertNotification(type, getCurrentUserName(), requestFromUsername, getCurrentFx(getCurrency(),getrecipientCurrency(),requestAmount), getCurrency(), getrecipientCurrency(), getTimeStamp());
    }

    @PermitAll
    public List<Notifications> getNotificationList() {
        return notificationService.getNotificationList(getCurrentUserName());
    }
}

package com.webapps2022.jsf;

import com.webapps2022.ejb.UserService;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 *
 * @author parisis
 */
@Named
@RequestScoped
@DeclareRoles({"users", "admin"})
public class RegistrationBean {

    @EJB
    UserService usrSrv;

    private String username;
    private String userpassword;
    private String name;
    private String surname;
    private String currency;
    private double balance;

    public RegistrationBean() {
        this.balance = 1000.0;

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
    public String register() {
        usrSrv.registerUser(username, userpassword, name, surname, currency, getCurrentFx("GBP", currency, balance));
        return "index";
    }

    @RolesAllowed("admins")
    public void registerAdmin() {
        usrSrv.registerAdmin(username, userpassword, name, surname, "N/A", 0.0);
    }

    public UserService getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserService usrSrv) {
        this.usrSrv = usrSrv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}

package com.webapps2022.restservice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// The Singleton annotaion denotes that there will be a single object of the RSEmployee class - don't change that
@Singleton
@Path("/conversion")
public class Conversion {

    public Conversion() {
    }

    @GET
    @Path("/{currency1}/{currency2}/{amount}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Double getCurrency(@PathParam("currency1") String currency1, @PathParam("currency2") String currency2, @PathParam("amount") double amount) {
        //DO CONVERSION HERE
       Double result = 0.0;
        if (currency1.equals("GBP") && currency2.equals("GBP")) {
            result =  amount * 1;
        } else if (currency1.equals("GBP") && currency2.equals("USD")) {
            result = amount * 1.301785;
        } else if (currency1.equals("GBP") && currency2.equals("EUR")) {
            result = amount * 1.20731278;
        } else if (currency1.equals("USD") && currency2.equals("USD")) {
            result = amount * 1;
        } else if (currency1.equals("USD") && currency2.equals("GBP")) {
            result = amount * 0.768176004;
        } else if (currency1.equals("USD") && currency2.equals("EUR")) {
            result = amount * 0.927428704;
        } else if (currency1.equals("EUR") && currency2.equals("EUR")) {
            result = amount * 1;
        } else if (currency1.equals("EUR") && currency2.equals("GBP")) {
            result = amount * 0.828285777;
        } else if (currency1.equals("EUR") && currency2.equals("USD")) {
            result = amount * 1.07825;
        }
        return result;
    }

    @PostConstruct
    public void init() {
        System.out.println("Singleton Object for this RESTfull Web Service has been created!!");
    }

    @PreDestroy
    public void clean() {
        System.out.println("Singleton Object for this RESTfull Web Service has been cleaned!!");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author user
 */
@Entity
public class TransactionRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // here on could use Bean Validation annotations to enforce specific rules - this could be alternatively implemented when validating the form in the web tier
    // for now we check only for Null values
    @NotNull
    String fromUser;

    @NotNull
    String toUser;

    @NotNull
    double amount;

    @NotNull
    String fromUserCurrency;

    @NotNull
    String toUserCurrency;

    @NotNull
    Date dateTime;

    public TransactionRecord() {
    }

    public TransactionRecord(String fromUser, String toUser, double amount, String toUserCurrency, String fromUserCurrency, Date dateTime) {

        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.toUserCurrency = toUserCurrency;
        this.fromUserCurrency = fromUserCurrency;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToUserCurrency() {
        return toUserCurrency;
    }

    public void setToUserCurrency(String toUserCurrency) {
        this.toUserCurrency = toUserCurrency;
    }

    public String getFromUserCurrency() {
        return fromUserCurrency;
    }

    public void setFromUserCurrency(String fromUserCurrency) {
        this.fromUserCurrency = fromUserCurrency;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.fromUser);
        hash = 71 * hash + Objects.hashCode(this.toUser);
        hash = 71 * hash + Objects.hashCode(this.amount);
        hash = 71 * hash + Objects.hashCode(this.fromUserCurrency);
        hash = 71 * hash + Objects.hashCode(this.toUserCurrency);
        hash = 71 * hash + Objects.hashCode(this.dateTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionRecord other = (TransactionRecord) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fromUser, other.fromUser)) {
            return false;
        }
        if (!Objects.equals(this.toUser, other.toUser)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.fromUserCurrency, other.fromUserCurrency)) {
            return false;
        }
        if (!Objects.equals(this.toUserCurrency, other.toUserCurrency)) {
            return false;
        }
        return Objects.equals(this.dateTime, other.dateTime);
    }
}

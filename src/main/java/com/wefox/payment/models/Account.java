package com.wefox.payment.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_id")
    private Integer accountId;
    @Column(name = "email")
    private String email;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column(name = "last_payment_date")
    private String lastPaymentDate;
    @Column(name = "created_on")
    private Timestamp createdAt;
    @OneToMany(mappedBy="account")
    private List<Payment> payments;

    Account() {}

    public Integer getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}

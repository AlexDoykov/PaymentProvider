package com.wefox.payment.models;

import com.wefox.payment.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private String paymentId;
    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;
    @Column(name = "paymentType")
    private PaymentType paymentType;
    @Column(name = "creditCard")
    private String creditCard;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "created_on")
    private Timestamp createdAt;

    public Payment() {}

    public String getPaymentId() {
        return paymentId;
    }

    public Account getAccount() {
        return account;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public Integer getAmount() {
        return amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}



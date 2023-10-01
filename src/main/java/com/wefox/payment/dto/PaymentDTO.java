package com.wefox.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PaymentDTO {

    PaymentDTO() {}

    @JsonProperty("payment_id")
    public UUID paymentId;
    @JsonProperty("account_id")
    public Integer accountId;
    @JsonProperty("payment_type")

    public String paymentType;
    @JsonProperty("credit_card")

    public String creditCard;
    @JsonProperty("amount")

    public Integer amount;
    @JsonProperty("delay")

    public Integer delay;
}

package com.wefox.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentDTO {

    PaymentDTO() {}

    @JsonProperty("payment_id")
    public String paymentId;
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

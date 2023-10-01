package com.wefox.payment.kafka;

import com.wefox.payment.dto.PaymentDTO;
import com.wefox.payment.enums.PaymentType;
import com.wefox.payment.models.Payment;
import com.wefox.payment.repositories.AccountRepository;
import com.wefox.payment.repositories.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;


@Service
public class KafkaPaymentListener {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    Logger LOG = LoggerFactory.getLogger(KafkaPaymentListener.class);

    @KafkaListener(topics = "offline", groupId = "payment")
    void listener(PaymentDTO paymentDto) {
        try {
            paymentRepository.save(convertToEntity(paymentDto));
            LOG.info(paymentDto.accountId.toString());
            LOG.info(paymentDto.amount.toString());
        } catch (IllegalArgumentException e) {
            LOG.info("Entity that was used was null or not found " + e.getMessage());
        }


    }

    private Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        if (Objects.equals(paymentDTO.paymentType, "offline")) {
            payment.setPaymentType(PaymentType.OFFLINE);
        } else if (Objects.equals(paymentDTO.paymentType, "online")) {
            payment.setPaymentType(PaymentType.ONLINE);
        } else {
            throw new IllegalArgumentException("Unknown payment type: " + paymentDTO.paymentType);
        }
        payment.setPaymentId(paymentDTO.paymentId);
        payment.setCreatedAt(Timestamp.from(Instant.now()));
        payment.setAmount(paymentDTO.amount);
        payment.setCreditCard(paymentDTO.creditCard);
        payment.setAccount(accountRepository.findById(paymentDTO.accountId).get());
        return payment;
    }
}

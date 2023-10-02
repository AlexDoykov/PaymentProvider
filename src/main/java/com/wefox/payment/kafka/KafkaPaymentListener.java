package com.wefox.payment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.payment.dto.PaymentDTO;
import com.wefox.payment.enums.PaymentType;
import com.wefox.payment.models.Account;
import com.wefox.payment.models.Payment;
import com.wefox.payment.repositories.AccountRepository;
import com.wefox.payment.repositories.PaymentRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class KafkaPaymentListener {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    Logger LOG = LoggerFactory.getLogger(KafkaPaymentListener.class);

    @KafkaListener(
            topics = "offline",
            groupId = "payment"
    )
    void offlinePaymentListener(PaymentDTO paymentDto) {
        try {
            savePayment(paymentDto);
        } catch (IllegalArgumentException e) {
            LOG.error("Entity that was used was not found " + e.getMessage());
        }
    }

    @KafkaListener(
            topics = "online",
            groupId = "payment"
    )
    void onlinePaymentListener(PaymentDTO paymentDto) {
        try {
            if (verifyPayment(paymentDto)) {
                savePayment(paymentDto);
            }
        } catch (IllegalArgumentException e) {
            LOG.info("Entity that was used was null or not found " + e.getMessage());
        } catch (JsonProcessingException e) {
            LOG.info("PaymentDTO was not serialized successfully " + e.getMessage());
        }
    }

    private void savePayment(PaymentDTO paymentDto) {
        Payment payment = paymentRepository.save(convertToEntity(paymentDto));
        Account account = accountRepository.findById(paymentDto.accountId).get();
        account.setLastPaymentDate(payment.getCreatedAt().toString());
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

    private boolean verifyPayment(PaymentDTO paymentDto) throws JsonProcessingException {
        String uri = "http://localhost:9000/payment";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(paymentDto), headers);
        ResponseEntity<?> result =
                restTemplate.exchange(uri, HttpMethod.POST, entity, Void.class);

        return result.getStatusCode().is2xxSuccessful();
    }
}

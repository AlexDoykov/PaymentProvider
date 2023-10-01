package com.wefox.payment.kafka;

import com.wefox.payment.dto.PaymentDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaPaymentListener {

    Logger LOG = LoggerFactory.getLogger(KafkaPaymentListener.class);

    @KafkaListener(topics = "offline", groupId = "payment")
    void listener(PaymentDTO data) {
        LOG.info(data.accountId.toString());
        LOG.info(data.amount.toString());

    }
}

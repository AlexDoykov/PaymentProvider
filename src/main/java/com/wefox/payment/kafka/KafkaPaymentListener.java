package com.wefox.payment.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaPaymentListener {

    Logger LOG = LoggerFactory.getLogger(KafkaPaymentListener.class);

    @KafkaListener(topics = "offline")
    void listener(String data) {
        LOG.info(data);
    }
}

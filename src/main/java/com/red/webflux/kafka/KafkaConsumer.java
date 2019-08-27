package com.red.webflux.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 16:46 2019/8/22
 */
@Component
public class KafkaConsumer<T> {


        private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

        /**
         * 监听kafka.tut 的topic，不做其他业务
         *
         * @param record
         * @param topic  topic
         */
//        @KafkaListener(id = "tut", topics = "kafka.tut")
        public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());

            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                logger.info("Receive： +++++++++++++++ Topic:" + topic);
                logger.info("Receive： +++++++++++++++ Record:" + record);
                logger.info("Receive： +++++++++++++++ Message:" + message);
            }
        }

    }


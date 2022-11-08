//package com.dut.team92.organizationservice.message.listener;
//
//import com.dut.team92.kafka.consumer.KafkaConsumer;
//import com.dut.team92.kafka.model.OrganizationRequestModel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@Slf4j
//public class OrganizationRequestKafkaListener implements KafkaConsumer<OrganizationRequestModel> {
//    private OrganizationRequestMessageListener organizationRequestMessageListener;
//
//    @Autowired
//    public OrganizationRequestKafkaListener(OrganizationRequestMessageListener organizationRequestMessageListener) {
//        this.organizationRequestMessageListener = organizationRequestMessageListener;
//    }
//
//    @Override
//    @KafkaListener(id = "${kafka-consumer-config.organization-consumer-group-id}",
//            topics = "${organization-service.organization-request-topic-name}")
//    public void receive(@Payload List<OrganizationRequestModel> messages,
//                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
//                        @Header(KafkaHeaders.OFFSET) List<Long> offSets) {
//        log.info("{} number of organization requests received with keys:{}, partitions:{} and offsets: {}",
//                messages.size(),
//                keys.toString(),
//                partitions.toString(),
//                offSets.toString());
//
//        messages.forEach(organizationRequestModel -> {
//            // publish event response user service
//        });
//    }
//}

package com.dut.team92.memberservice.messages.listener;

import com.dut.team92.kafka.consumer.KafkaConsumer;
import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.memberservice.messages.listener.services.MemberRequestMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUserRequestKafkaListener implements KafkaConsumer<MemberRequestModel> {
    private final MemberRequestMessageListener memberRequestMessageListener;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.update-user-consumer-group-id}",
            topics = "${member-service.update-user-request-topic-name}")
    public void receive(@Payload List<MemberRequestModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offSets) {
        log.info("{} number of member requests received with keys:{}, partitions:{} and offsets: {}",
                messages.size(), keys.toString(), partitions.toString(), offSets.toString());
        messages.forEach(memberRequestMessageListener::updateUser);
    }
}

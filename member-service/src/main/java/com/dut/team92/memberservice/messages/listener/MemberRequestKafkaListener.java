package com.dut.team92.memberservice.messages.listener;

import com.dut.team92.kafka.consumer.KafkaConsumer;
import com.dut.team92.kafka.model.ListMemberRequestModel;
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
public class MemberRequestKafkaListener implements KafkaConsumer<ListMemberRequestModel> {
    private final MemberRequestMessageListener memberRequestMessageListener;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.member-consumer-group-id}",
        topics = "${member-service.member-request-topic-name}")
    public void receive(@Payload List<ListMemberRequestModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offSets) {
        log.info("{} number of member requests received with keys:{}, partitions:{} and offsets: {}",
            messages.size(), keys.toString(), partitions.toString(), offSets.toString());
        messages.forEach(message -> {
            memberRequestMessageListener.completeAddUserToOrganization(message.getMemberRequestModels());
        });
    }
}

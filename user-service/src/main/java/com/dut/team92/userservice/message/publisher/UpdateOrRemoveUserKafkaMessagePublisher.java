package com.dut.team92.userservice.message.publisher;

import com.dut.team92.common.exception.CommonServerErrorException;
import com.dut.team92.kafka.helper.KafkaMessageHelper;
import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.kafka.producer.KafkaProducer;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.message.config.UserServiceConfigData;
import com.dut.team92.userservice.message.mapper.UserMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateOrRemoveUserKafkaMessagePublisher implements UpdateOrRemoveMessagePublisher{
    private final KafkaMessageHelper kafkaMessageHelper;
    private final KafkaProducer<String, MemberRequestModel> kafkaProducer;
    private final UserServiceConfigData userServiceConfigData;
    private final UserMessagingDataMapper userMessagingDataMapper;

    @Override
    public void publishRemoveUser(User user, String key) {
        log.info("Publishing remove user event by user id: {}", key);
        try {
            publish(user, key, userServiceConfigData.getRemoveUserRequestTopicName());
            log.info("Published  remove user event by user id: {}", key);
        }
        catch(Exception e){
            log.error("Error publishing  remove user event by user id: {} and message is {}",
                    key, e.getMessage());
            throw new CommonServerErrorException(500, e.getMessage());
        }
    }

    @Override
    public void publishUpdateUser(User user, String key) {
        log.info("Publishing update user event by user id: {}", key);
        try {
            publish(user, key, userServiceConfigData.getUpdateUserRequestTopicName());
            log.info("Published  update user event by user id: {}", key);
        }
        catch(Exception e){
            log.error("Error publishing  update user event by user id: {} and message is {}",
                    key, e.getMessage());
            throw new CommonServerErrorException(500, e.getMessage());
        }
    }

    private void publish(User user, String key, String topic) {
        var memberRequestModel = userMessagingDataMapper.convertToMember(user);
        var callback =
                kafkaMessageHelper.getKafkaCallBack(topic,
                        memberRequestModel,
                        key,
                        "MemberRequestModel");

        kafkaProducer.send(
                topic,
                key,
                memberRequestModel,
                callback
        );
    }
}

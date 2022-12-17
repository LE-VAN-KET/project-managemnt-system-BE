package com.dut.team92.userservice.message.publisher;

import com.dut.team92.common.exception.CommonServerErrorException;
import com.dut.team92.kafka.helper.KafkaMessageHelper;
import com.dut.team92.kafka.model.ListMemberRequestModel;
import com.dut.team92.kafka.producer.KafkaProducer;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.message.config.UserServiceConfigData;
import com.dut.team92.userservice.message.mapper.UserMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserKafkaMessagePublisher implements UserMessagePublisher {
    private final KafkaMessageHelper kafkaMessageHelper;
    private final KafkaProducer<String, ListMemberRequestModel> kafkaProducer;
    private final UserServiceConfigData userServiceConfigData;
    private final UserMessagingDataMapper userMessagingDataMapper;


    @Override
    public void publish(List<User> users, String key) {
        log.info("Publishing create list member event for organization id: {}", key);
        try(ListMemberRequestModel listMemberRequestModel = new ListMemberRequestModel()) {
            var memberRequestModels = userMessagingDataMapper.userToMember(users, UUID.fromString(key));
            listMemberRequestModel.setMemberRequestModels(memberRequestModels);
            var callback =
                    kafkaMessageHelper.getKafkaCallBack(userServiceConfigData.getMemberRequestTopicName(),
                        listMemberRequestModel,
                        key,
                        "ListMemberRequestModel");

            kafkaProducer.send(
                    userServiceConfigData.getMemberRequestTopicName(),
                    key,
                    listMemberRequestModel,
                    callback
                   );
            log.info("Published create list member event for organization id: {}",
                    key);
        }
        catch(Exception e){
            log.error("Error publishing create list member event for organization id: {} and message is {}",
                    key, e.getMessage(),  e);
            throw new CommonServerErrorException(500, e.getMessage());
        }
    }
}

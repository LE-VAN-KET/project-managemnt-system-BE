package com.dut.team92.userservice.message.publisher;

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

@Component
@Slf4j
@RequiredArgsConstructor
public class UserKafkaMessagePublisher implements UserMessagePublisher {
    private final KafkaMessageHelper kafkaMessageHelper;
    private final KafkaProducer<String, ListMemberRequestModel> kafkaProducer;
    private final UserServiceConfigData userServiceConfigData;
    private final UserMessagingDataMapper userMessagingDataMapper;


    @Override
    public void publish(List<User> users) {
        log.info("Publishing create list member event for organization id: {}",
                users.get(0).getOrganizationId());
        try{
            var memberRequestModels = userMessagingDataMapper.userToMember(users);
            ListMemberRequestModel listMemberRequestModel = new ListMemberRequestModel();
            listMemberRequestModel.setMemberRequestModels(memberRequestModels);
            var callback =
                    kafkaMessageHelper.getKafkaCallBack(userServiceConfigData.getOrganizationRequestTopicName(),
                        listMemberRequestModel,
                        users.get(0).getOrganizationId().toString(),
                        "ListMemberRequestModel");

            kafkaProducer.send(
                    userServiceConfigData.getMemberRequestTopicName(),
                    users.get(0).getOrganizationId().toString(),
                    listMemberRequestModel,
                    callback
                   );
            log.info("Published create list member event for organization id: {}",
                    users.get(0).getOrganizationId());
        }
        catch(Exception e){
            log.error("Error publishing create list member event for organization id: {} and message is {}",
                    users.get(0).getOrganizationId(), e.getMessage(),  e);
        }
    }
}

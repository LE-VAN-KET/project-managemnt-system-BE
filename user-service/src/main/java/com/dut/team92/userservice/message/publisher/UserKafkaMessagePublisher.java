//package com.dut.team92.userservice.message.publisher;
//
//import com.dut.team92.kafka.helper.KafkaMessageHelper;
//import com.dut.team92.kafka.model.OrganizationRequestModel;
//import com.dut.team92.kafka.producer.KafkaProducer;
//import com.dut.team92.userservice.domain.dto.event.UserCreatedEvent;
//import com.dut.team92.userservice.message.config.UserServiceConfigData;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import lombok.var;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class UserKafkaMessagePublisher implements UserMessagePublisher {
//    private final KafkaMessageHelper kafkaMessageHelper;
//    private final KafkaProducer<String, OrganizationRequestModel> kafkaProducer;
//    private final UserServiceConfigData userServiceConfigData;
////    private final UserMessagingDataMapper userMessagingDataMapper;
//
//
//    @Override
//    public void publish(UserCreatedEvent userCreatedEvent) {
//        UUID userId = userCreatedEvent.getUser().getId();
//        log.info("Publishing user created event for user id: {}", userId);
//        try{
////            var organizationRequestModel = userCreatedEvent.getOrganization();
////            var callback =
////                    kafkaMessageHelper.getKafkaCallBack(userServiceConfigData.getOrganizationRequestTopicName(),
////                        organizationRequestModel,
////                        userId.toString(),
////                        "OrganizationRequestModel");
////
////            kafkaProducer.send(
////                    userServiceConfigData.getOrganizationRequestTopicName(),
////                    userId.toString(),
////                    organizationRequestModel,
////                    callback
////                   );
//            log.info("Published user created event for user id: {}", userId);
//
//        }
//        catch(Exception e){
//            log.error("Error publishing user created event for user id: {} and message is {}",
//                    userId, e.getMessage(),  e);
//        }
//    }
//}

//package com.dut.team92.memberservice.messages.publisher;
//
//import com.dut.team92.kafka.helper.KafkaMessageHelper;
//import com.dut.team92.kafka.model.ListUserRequestModel;
//import com.dut.team92.kafka.producer.KafkaProducer;
//import com.dut.team92.memberservice.domain.dto.request.CreateMemberDto;
//import com.dut.team92.memberservice.messages.config.MemberServiceConfigData;
//import com.dut.team92.memberservice.messages.mapper.MemberMessageMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import lombok.var;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.UUID;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class MemberKafkaMessagePublisher implements MemberMessagePublisher {
//    private final KafkaMessageHelper kafkaMessageHelper;
//    private final KafkaProducer<String, ListUserRequestModel> kafkaProducer;
//    private final MemberServiceConfigData memberServiceConfigData;
//    private final MemberMessageMapper memberMessageMapper;
//
//
//    @Override
//    public void publish(List<CreateMemberDto> createMemberDtos, UUID organizationId) {
//        log.info("Publishing create list user to organization event for organization id: {}", organizationId);
//        try{
//            ListUserRequestModel userRequestModelList = new ListUserRequestModel();
//            var userRequestModels = memberMessageMapper
//                    .createMemberDtoToUserRequestModel(createMemberDtos);
//            userRequestModelList.setUserRequestModels(userRequestModels);
//            var callback =
//                    kafkaMessageHelper.getKafkaCallBack(memberServiceConfigData.getUserRequestTopicName(),
//                            userRequestModelList,
//                            organizationId.toString(),
//                        "UserRequestModel");
//
//            kafkaProducer.send(
//                    memberServiceConfigData.getUserRequestTopicName(),
//                    organizationId.toString(),
//                    userRequestModelList,
//                    callback
//                   );
//            log.info("Published create user event for organization id: {}", organizationId);
//
//        }
//        catch(Exception e){
//            log.error("Error publishing create user event for organization id: {} and message is {}",
//                    organizationId, e.getMessage(),  e);
//        }
//    }
//}

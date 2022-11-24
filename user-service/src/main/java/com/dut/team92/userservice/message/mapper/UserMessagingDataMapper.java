package com.dut.team92.userservice.message.mapper;

import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.userservice.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMessagingDataMapper {
    public List<MemberRequestModel> userToMember(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(u -> {
            MemberRequestModel memberRequestModel = new MemberRequestModel();
            memberRequestModel.setId(UUID.randomUUID());
            memberRequestModel.setSagaId(UUID.randomUUID());
            memberRequestModel.setUserId(u.getId());
            memberRequestModel.setMailNotification(u.getMailNotification());
            memberRequestModel.setOrganizationId(u.getOrganizationId());
            return memberRequestModel;
            }).collect(Collectors.toList());
    }
}

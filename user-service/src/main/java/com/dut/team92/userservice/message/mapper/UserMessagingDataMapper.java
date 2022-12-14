package com.dut.team92.userservice.message.mapper;

import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.userservice.domain.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMessagingDataMapper {
    public List<MemberRequestModel> userToMember(List<User> users, UUID organizationId) {
        return users.stream().filter(Objects::nonNull).map(u -> {
            MemberRequestModel memberRequestModel = new MemberRequestModel();
            memberRequestModel.setId(UUID.randomUUID());
            memberRequestModel.setSagaId(UUID.randomUUID());
            BeanUtils.copyProperties(u, memberRequestModel, "id");
            memberRequestModel.setUserId(u.getId());
            memberRequestModel.setStatus(u.getStatus().name());
            if (u.getUserInformation() != null) {
                memberRequestModel.setFirstName(u.getUserInformation().getFirstName());
                memberRequestModel.setLastName(u.getUserInformation().getLastName());
                memberRequestModel.setDisplayName(u.getUserInformation().getDisplayName());
            }
            memberRequestModel.setOrganizationId(organizationId);
            return memberRequestModel;
            }).collect(Collectors.toList());
    }

    public MemberRequestModel convertToMember(User user) {
        MemberRequestModel memberRequestModel = new MemberRequestModel();
        memberRequestModel.setId(UUID.randomUUID());
        memberRequestModel.setSagaId(UUID.randomUUID());
        BeanUtils.copyProperties(user, memberRequestModel, "id");
        memberRequestModel.setUserId(user.getId());
        memberRequestModel.setStatus(user.getStatus().name());
        if (user.getUserInformation() != null) {
            memberRequestModel.setFirstName(user.getUserInformation().getFirstName());
            memberRequestModel.setLastName(user.getUserInformation().getLastName());
            memberRequestModel.setDisplayName(user.getUserInformation().getDisplayName());
        }
        return memberRequestModel;
    }
}

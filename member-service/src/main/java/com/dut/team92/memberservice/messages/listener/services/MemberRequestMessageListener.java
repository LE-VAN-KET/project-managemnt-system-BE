package com.dut.team92.memberservice.messages.listener.services;

import com.dut.team92.kafka.model.MemberRequestModel;

import java.util.List;
import java.util.UUID;

public interface MemberRequestMessageListener {
    void completeAddUserToOrganization(List<MemberRequestModel> memberRequestModelList);
    void removeUser(UUID userId);
    void updateUser(MemberRequestModel memberRequestModel);
}

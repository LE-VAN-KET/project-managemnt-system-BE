package com.dut.team92.memberservice.messages.mapper;

import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.memberservice.domain.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMessageMapper {
    public List<User> memberRequestModelToUser(List<MemberRequestModel> memberRequestModelList) {
        return memberRequestModelList.stream().filter(Objects::nonNull).map(m -> {
            User user = new User();
            BeanUtils.copyProperties(m, user);
            user.setStatus(UserStatus.valueOf(m.getStatus()));
            user.setId(m.getUserId());
            return user;
        }).collect(Collectors.toList());
    }

    public User convertToUser(MemberRequestModel memberRequestModel) {
        User user = new User();
        BeanUtils.copyProperties(memberRequestModel, user);
        user.setStatus(UserStatus.valueOf(memberRequestModel.getStatus()));
        user.setId(memberRequestModel.getUserId());
        return user;
    }
}

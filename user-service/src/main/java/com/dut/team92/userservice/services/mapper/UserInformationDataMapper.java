package com.dut.team92.userservice.services.mapper;

import com.dut.team92.userservice.domain.dto.request.CreateMemberDto;
import com.dut.team92.userservice.domain.dto.request.CreateUserAdminOrganizationCommand;
import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.domain.entity.UserInformation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserInformationDataMapper {

    public UserInformation createUserCommandToUserInformation(CreateUserCommand createUserCommand) {
        UserInformation userInformation = new UserInformation();
        if (createUserCommand != null) {
            BeanUtils.copyProperties(createUserCommand, userInformation);
        }
        return userInformation;
    }
    public UserInformation createUserCommandToUserInformation(CreateUserAdminOrganizationCommand createUserCommand) {
        UserInformation userInformation = new UserInformation();
        if (createUserCommand != null) {
            BeanUtils.copyProperties(createUserCommand, userInformation);
        }
        return userInformation;
    }

    public List<UserInformation> createMemberDtoToUserInformation(List<CreateMemberDto> createMemberDtoList){
        return createMemberDtoList.stream().filter(Objects::nonNull).map(m -> {
            UserInformation userInformation = new UserInformation();
            BeanUtils.copyProperties(m, userInformation);
            User user = new User();
            user.setUsername(m.getUsername().trim());
            userInformation.setUser(user);
            return userInformation;
        }).collect(Collectors.toList());
    }
}

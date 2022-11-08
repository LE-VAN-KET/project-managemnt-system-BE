package com.dut.team92.userservice.services.mapper;

import com.dut.team92.userservice.domain.dto.request.CreateUserAdminOrganizationCommand;
import com.dut.team92.userservice.domain.dto.request.CreateUserCommand;
import com.dut.team92.userservice.domain.entity.UserInformation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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
}

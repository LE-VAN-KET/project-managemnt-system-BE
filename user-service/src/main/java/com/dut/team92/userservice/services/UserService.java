package com.dut.team92.userservice.services;

import com.dut.team92.common.service.RepositoryService;
import com.dut.team92.userservice.domain.dto.UserDto;
import com.dut.team92.userservice.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService extends RepositoryService<User> {
    boolean isUsernameExist(String username);
    boolean isMailNotification(String mailNotification);
    List<UserDto> addListUserMemberToOrganization(MultipartFile file, UUID organizationId);
}

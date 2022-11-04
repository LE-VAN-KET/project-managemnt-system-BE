package com.dut.team92.userservice.services;

import com.dut.team92.common.service.RepositoryService;
import com.dut.team92.userservice.domain.entity.User;

public interface UserService extends RepositoryService<User> {
    boolean isUsernameExist(String username);
    boolean isMailNotification(String mailNotification);
}

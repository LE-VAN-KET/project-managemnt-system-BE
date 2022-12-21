package com.dut.team92.userservice.message.publisher;

import com.dut.team92.userservice.domain.entity.User;

import java.util.UUID;

public interface UpdateOrRemoveMessagePublisher {
    void publishRemoveUser(User user, String key);
    void publishUpdateUser(User user, String key);
}

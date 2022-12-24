package com.dut.team92.userservice.message.publisher;

import com.dut.team92.userservice.domain.entity.User;

import java.util.List;

public interface UserMessagePublisher {
    void publish(List<User> userList, String key);
}

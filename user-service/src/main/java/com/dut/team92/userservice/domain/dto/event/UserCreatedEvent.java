package com.dut.team92.userservice.domain.dto.event;

import com.dut.team92.userservice.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class UserCreatedEvent {
    private User user;
    private ZonedDateTime createAt;
}

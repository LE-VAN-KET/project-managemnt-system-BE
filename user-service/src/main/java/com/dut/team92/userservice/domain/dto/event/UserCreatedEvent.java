package com.dut.team92.userservice.domain.dto.event;

import com.dut.team92.userservice.domain.entity.User;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class UserCreatedEvent {
    private User user;
    private ZonedDateTime createAt;

    public UserCreatedEvent(User user, ZonedDateTime createAt) {
        this.user = user;
        this.createAt = createAt;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.dut.team92.userservice.services;

import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMailNotification(String mailNotification) {
        return userRepository.existsByMailNotification(mailNotification);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User create(User entity) {
        entity.setIsDelete(false);
        entity.setIsSystemAdmin(false);
        entity.setStatus(UserStatus.ACTIVE);
        return userRepository.save(entity);
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

package com.dut.team92.userservice.services;

import com.dut.team92.userservice.domain.entity.UserInformation;
import com.dut.team92.userservice.repository.UserInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInformationServiceImpl implements UserInformationService{
    @Autowired
    private UserInformationRepository userInformationRepository;

    @Override
    public List<UserInformation> getAll() {
        return null;
    }

    @Override
    public UserInformation get(Long id) {
        return null;
    }

    @Override
    public UserInformation create(UserInformation entity) {
        return userInformationRepository.save(entity);
    }

    @Override
    public UserInformation update(UserInformation entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

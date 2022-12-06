package com.dut.team92.userservice.services;

import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.userservice.domain.dto.UserDto;
import com.dut.team92.userservice.domain.dto.request.CreateMemberDto;
import com.dut.team92.userservice.domain.dto.response.CheckOrganizationExistResponse;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.domain.entity.UserInformation;
import com.dut.team92.userservice.exception.EmailAlreadyExistsException;
import com.dut.team92.userservice.exception.FailedReadDataFileCSV;
import com.dut.team92.userservice.exception.OrganizationNotFoundException;
import com.dut.team92.userservice.exception.UsernameAlreadyExistsException;
import com.dut.team92.userservice.message.publisher.UserKafkaMessagePublisher;
import com.dut.team92.userservice.proxy.OrganizationServiceProxy;
import com.dut.team92.userservice.repository.UserInformationRepository;
import com.dut.team92.userservice.repository.UserRepository;
import com.dut.team92.userservice.services.mapper.UserDataMapper;
import com.dut.team92.userservice.services.mapper.UserInformationDataMapper;
import com.dut.team92.userservice.util.CSVHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private UserDataMapper userDataMapper;
    private UserRepository userRepository;
    private UserInformationDataMapper userInformationDataMapper;
    private UserInformationRepository userInformationRepository;
    private OrganizationServiceProxy organizationServiceProxy;
    private UserKafkaMessagePublisher userKafkaMessagePublisher;

    @Autowired
    public UserServiceImpl(UserDataMapper userDataMapper,
                           UserRepository userRepository,
                           UserInformationDataMapper userInformationDataMapper,
                           UserInformationRepository userInformationRepository,
                           OrganizationServiceProxy organizationServiceProxy,
                           UserKafkaMessagePublisher userKafkaMessagePublisher) {
        this.userDataMapper = userDataMapper;
        this.userRepository = userRepository;
        this.userInformationDataMapper = userInformationDataMapper;
        this.userInformationRepository = userInformationRepository;
        this.organizationServiceProxy = organizationServiceProxy;
        this.userKafkaMessagePublisher = userKafkaMessagePublisher;
    }

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

    @Override
    public List<UserDto> addListUserMemberToOrganization(MultipartFile file, UUID organizationId) {
        try {
            CheckOrganizationExistResponse organizationExist = organizationServiceProxy.checkOrganizationExist(
                    organizationId.toString());
            if (!organizationExist.getIsExistOrganization()) {
                log.warn("Call Organization Service ERROR Status: {}, Message: {}", organizationExist.getCode(),
                        organizationExist.getMessage());
                throw new OrganizationNotFoundException("Organization not found with id " + organizationId);
            }
            // check file isFormat CSV
            CSVHelper.hasCSVFormat(file);
            List<CreateMemberDto> createMemberDtos = CSVHelper.csvToMembers(file.getInputStream(), organizationId);
            List<User> users = userDataMapper.createMemberDtoToUser(createMemberDtos);
            users.forEach(this::validateUser);
            List<User> savedUsers = userRepository.saveAll(users);
            Map<String, User> userMap = new HashMap<>();
            savedUsers.forEach(u -> userMap.put(u.getUsername(), u));
            List<UserInformation> userInformations = userInformationDataMapper
                    .createMemberDtoToUserInformation(createMemberDtos, userMap);
            userInformationRepository.saveAll(userInformations);
            userKafkaMessagePublisher.publish(new ArrayList<>(userMap.values()));
            return userDataMapper.userListToUserDtoList(savedUsers);
        } catch (IOException e) {
            throw new FailedReadDataFileCSV("Fail save csv data: " + e.getMessage());
        }
    }

    private void validateUser(@Valid User user) {
        if (this.isUsernameExist(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        if (this.isMailNotification(user.getMailNotification())) {
            throw new EmailAlreadyExistsException(user.getMailNotification());
        }
    }
}

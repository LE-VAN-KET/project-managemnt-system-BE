package com.dut.team92.userservice.services;

import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.common.security.model.CustomUserPrincipal;
import com.dut.team92.userservice.domain.dto.UserDto;
import com.dut.team92.userservice.domain.dto.request.CreateMemberDto;
import com.dut.team92.userservice.domain.dto.request.UpdateUserDto;
import com.dut.team92.userservice.domain.dto.response.CheckOrganizationExistResponse;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.domain.entity.UserInformation;
import com.dut.team92.userservice.exception.*;
import com.dut.team92.userservice.message.publisher.UpdateOrRemoveUserKafkaMessagePublisher;
import com.dut.team92.userservice.message.publisher.UserKafkaMessagePublisher;
import com.dut.team92.userservice.proxy.OrganizationServiceProxy;
import com.dut.team92.userservice.repository.UserInformationRepository;
import com.dut.team92.userservice.repository.UserRepository;
import com.dut.team92.userservice.services.mapper.UserDataMapper;
import com.dut.team92.userservice.services.mapper.UserInformationDataMapper;
import com.dut.team92.userservice.util.CSVHelper;
import com.dut.team92.userservice.util.PropertyPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private UserDataMapper userDataMapper;
    private UserRepository userRepository;
    private UserInformationDataMapper userInformationDataMapper;
    private UserInformationRepository userInformationRepository;
    private OrganizationServiceProxy organizationServiceProxy;
    private UserKafkaMessagePublisher userKafkaMessagePublisher;
    private final UpdateOrRemoveUserKafkaMessagePublisher updateOrRemoveUserKafkaMessagePublisher;

    @Autowired
    public UserServiceImpl(UserDataMapper userDataMapper,
                           UserRepository userRepository,
                           UserInformationDataMapper userInformationDataMapper,
                           UserInformationRepository userInformationRepository,
                           OrganizationServiceProxy organizationServiceProxy,
                           UserKafkaMessagePublisher userKafkaMessagePublisher,
                           UpdateOrRemoveUserKafkaMessagePublisher updateOrRemoveUserKafkaMessagePublisher) {
        this.userDataMapper = userDataMapper;
        this.userRepository = userRepository;
        this.userInformationDataMapper = userInformationDataMapper;
        this.userInformationRepository = userInformationRepository;
        this.organizationServiceProxy = organizationServiceProxy;
        this.userKafkaMessagePublisher = userKafkaMessagePublisher;
        this.updateOrRemoveUserKafkaMessagePublisher = updateOrRemoveUserKafkaMessagePublisher;
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
            userKafkaMessagePublisher.publish(new ArrayList<>(userMap.values()), organizationId.toString());
            return userDataMapper.userListToUserDtoList(savedUsers, organizationId);
        } catch (IOException e) {
            throw new FailedReadDataFileCSV("Fail save csv data: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserDto update(UpdateUserDto userDto, UUID userId) {
        User existUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        BeanUtils.copyProperties(userDto, existUser, PropertyPojo.getNullPropertyNames(userDto));
        BeanUtils.copyProperties(userDto, existUser.getUserInformation(), PropertyPojo.getNullPropertyNames(userDto));
        if (Objects.nonNull(userDto.getUsername())
                && userRepository.isExistUsernameByNotUserId(existUser.getUsername(), existUser.getId())) {
            throw new UsernameAlreadyExistsException(existUser.getUsername());
        }
        if (Objects.nonNull(userDto.getMailNotification())
                && userRepository.isExistEmailByNotUserId(existUser.getMailNotification(), existUser.getId())) {
            throw new EmailAlreadyExistsException(existUser.getMailNotification());
        }
        User updatedUser = userRepository.save(existUser);
        updateOrRemoveUserKafkaMessagePublisher.publishUpdateUser(updatedUser, updatedUser.getId().toString());
        return userDataMapper.convertToDto(updatedUser);
    }

    @Override
    public void removeUser(UUID userId) {
        User existUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        userRepository.delete(existUser);
        updateOrRemoveUserKafkaMessagePublisher.publishRemoveUser(existUser, userId.toString());
    }

    @Override
    public UserDto getCurrentUser() {
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findById(UUID.fromString(userPrincipal.getSubId())).orElseThrow((() ->
                new UserNotFoundException("User not found!")));
        return userDataMapper.convertToDto(user);
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

package com.dut.team92.userservice.security.service;

import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.common.security.model.CustomUserPrincipal;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.exception.RoleNotFoundException;
import com.dut.team92.userservice.exception.UserNotEnabledException;
import com.dut.team92.userservice.exception.UserNotFoundException;
import com.dut.team92.userservice.proxy.MemberServiceProxy;
import com.dut.team92.userservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MemberServiceProxy memberServiceProxy;
    private final HttpServletRequest request;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository,
                                    MemberServiceProxy memberServiceProxy,
                                    HttpServletRequest request) {
        this.userRepository = userRepository;
        this.memberServiceProxy = memberServiceProxy;
        this.request = request;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomUserPrincipal loadUserByUsername(String username) {
        User user = userRepository.findByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("Username is not found!"));
        if (!user.getStatus().equals(UserStatus.ACTIVE))
            throw new UserNotEnabledException();

        Object response = memberServiceProxy.getRolesByUserId(user.getId().toString(),
                request.getHeader(HttpHeaders.AUTHORIZATION));

        Collection<? extends GrantedAuthority> authorities = getAuthorities(convertObjectToRoleList(response));

        return new CustomUserPrincipal(user.getOrganizationId().toString(),
                username,
                user.getPassword(),
                user.getId().toString(),
                "",
                true,
                authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roleList) {
        return roleList.stream().map(roleName ->
                new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toSet());
    }

    private List<String> convertObjectToRoleList(Object o){
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (o instanceof List) {
                return mapper.readValue(mapper.writeValueAsString(o),
                        new TypeReference<List<String>>(){});
            } else {
                CommonErrorResponse error = mapper.readValue(mapper.writeValueAsString(o),
                        CommonErrorResponse.class);
                throw new RoleNotFoundException(error.getMessage());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

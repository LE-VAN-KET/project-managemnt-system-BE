package com.dut.team92.userservice.security.service;

import antlr.collections.List;
import com.dut.team92.common.enums.RoleType;
import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.common.security.model.CustomUserPrincipal;
import com.dut.team92.userservice.domain.entity.User;
import com.dut.team92.userservice.exception.UserNotEnabledException;
import com.dut.team92.userservice.exception.UserNotFoundException;
import com.dut.team92.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CustomUserPrincipal loadUserByUsername(String username) {
        User user = userRepository.findByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("Username is not found!"));
        if (!user.getStatus().equals(UserStatus.ACTIVE))
            throw new UserNotEnabledException();

        Collection<? extends GrantedAuthority> authorities = getAuthorities();
//        if (user.getIsSystemAdmin()) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + RoleType.SUPER_ADMIN.toString()));
//        } else if (user.getIsOrganizerAdmin()) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + RoleType.ADMIN_ORGANIZATION.toString()));
//        }

        return new CustomUserPrincipal(username,
                user.getPassword(),
                user.getId().toString(),
                "",
                true,
                authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + RoleType.USER.toString()));
        return authorities;
    }
}

package com.dut.team92.memberservice.services;

import com.dut.team92.memberservice.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RolesRepository rolesRepository;
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllRolesByUserId(UUID userId) {
        return rolesRepository.getRolesByUserId(userId);
    }
}

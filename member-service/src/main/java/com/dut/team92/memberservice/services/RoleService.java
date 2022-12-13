package com.dut.team92.memberservice.services;

import java.util.UUID;
import java.util.List;

public interface RoleService {
    List<String> getAllRolesByUserId(UUID userId);
}

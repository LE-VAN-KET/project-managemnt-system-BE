package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RoleService roleService;

    @GetMapping
    public List<String> getRolesByUserId(@RequestParam("user_id") String userId) {
        return roleService.getAllRolesByUserId(UUID.fromString(userId));
    }
}

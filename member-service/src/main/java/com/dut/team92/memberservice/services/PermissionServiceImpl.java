package com.dut.team92.memberservice.services;

import com.dut.team92.memberservice.domain.dto.request.permission.RolesPermissionDto;
import com.dut.team92.memberservice.domain.dto.response.Permission.GroupsOfScreenResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.PermissionResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.ScreenResponse;
import com.dut.team92.memberservice.domain.entity.Function;
import com.dut.team92.memberservice.domain.entity.Permissions;
import com.dut.team92.memberservice.enums.groupOfScreen;
import com.dut.team92.memberservice.exception.FunctionNotFoundException;
import com.dut.team92.memberservice.exception.PermissionNotFoundException;
import com.dut.team92.memberservice.exception.SavePermissionsFailedException;
import com.dut.team92.memberservice.repository.FunctionRepository;
import com.dut.team92.memberservice.repository.PermissionsRepository;
import com.dut.team92.memberservice.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PermissionsRepository permissionsRepository;
    @Autowired
    private FunctionRepository functionRepository;

    @Override
    public List<RolesResponse> getRoles(UUID organizationId) {
        return rolesRepository.getListRoles(organizationId);
    }

    @Override
    public List<GroupsOfScreenResponse> getListPermission(Long roleId) {
        List<PermissionResponse> permissions = rolesRepository.getListPermission(roleId);
        int i =1;
        List<ScreenResponse> screens = rolesRepository.getListScreen();
        List<GroupsOfScreenResponse> res = new ArrayList<GroupsOfScreenResponse>();

        for (groupOfScreen value : groupOfScreen.values()) {
            res.add(new GroupsOfScreenResponse(value.ordinal(), value.name()));
        }
        screens.forEach((screen) -> {
            screen.setPermissions(permissions.stream()
                    .filter(x -> x.getScreenId().equals(screen.getId())).collect(Collectors.toList()));
        });
        res.forEach((group) -> {
            group.setScreens(screens.stream()
                    .filter(x -> x.getGroupId() == group.getId()).collect(Collectors.toList()));
        });
        return res;
    }

    @Override
    public boolean savePermission(RolesPermissionDto data) {
        try{
            List<Permissions> dbPermissions = new ArrayList<Permissions>();
            data.getPermissions().forEach((permission) -> {
                Permissions dbPermission = permissionsRepository.findById(permission.getId()).orElseThrow(() ->
                        new PermissionNotFoundException("Permission not found with id equal " + permission.getId()));
                dbPermission.setEnable(permission.isEnable());
                Function function = functionRepository.findById(dbPermission.getFunctionId()).orElseThrow(() ->
                        new FunctionNotFoundException("Function not found with id equal " + dbPermission.getFunctionId()));
                // enable, default function enable
                if(function.getIsRequired() && !permission.isEnable())
                {
                    dbPermission.setEnable(true);
                }
                dbPermissions.add(dbPermission);
            });
            permissionsRepository.saveAll(dbPermissions);
            return true;
        }
        catch (Exception e)
        {
            throw new SavePermissionsFailedException("ExecutionException: " + e.getMessage());
        }
    }
}

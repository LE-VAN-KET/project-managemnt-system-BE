package com.dut.team92.memberservice.services;

import com.dut.team92.common.enums.RoleType;
import com.dut.team92.memberservice.domain.dto.request.permission.AddUpdateRoleRequest;
import com.dut.team92.memberservice.domain.dto.request.permission.RolesPermissionDto;
import com.dut.team92.memberservice.domain.dto.response.Permission.*;
import com.dut.team92.memberservice.domain.entity.*;
import com.dut.team92.memberservice.enums.groupOfScreen;
import com.dut.team92.memberservice.exception.*;
import com.dut.team92.memberservice.repository.FunctionRepository;
import com.dut.team92.memberservice.repository.PermissionsRepository;
import com.dut.team92.memberservice.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public boolean savePermission(long role_id, RolesPermissionDto data) {
        try{
            Optional<Roles> role = rolesRepository.findById(role_id);
            if (!role.isPresent())
            {
                throw new RoleExistException("Role already exists in organization!");
            }
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

    @Override
    public boolean addNewRoles(AddUpdateRoleRequest data) {
        try{
            Optional<Long> exist = rolesRepository.checkExist(data.getName(), data.getOrganizationId());
            if (exist.isPresent())
            {
                throw new RoleExistException("Role already exists in organization!");
            }
            Roles role = new Roles();
            role.setName(data.getName());
            role.setCode(data.getCode());
            role.setDescription(data.getDescription());
            role.setOrganizationId(data.getOrganizationId());
            role.setType(2);
            rolesRepository.save(role);
            List<FunctionModel> functions = functionRepository.getFunctionOfScreen();
            List<Permissions> dbPermissions = new ArrayList<Permissions>();
            functions.forEach((function) -> {
                Permissions permission = new Permissions();
                permission.setRole(role);
                permission.setScreenId(function.getScreenId());
                permission.setFunctionId(function.getFunctionId());
                permission.setEnable(function.isRequired());
                dbPermissions.add(permission);
            });
            permissionsRepository.saveAll(dbPermissions);
            return true;
        }
        catch (Exception e)
        {
            throw new SavePermissionsFailedException("ExecutionException: " + e.getMessage());
        }
    }

    @Override
    public boolean updateRoles(long roleId,AddUpdateRoleRequest data) {
        try{
            Optional<Roles> check = rolesRepository.findById(roleId);
            if (!check.isPresent())
            {
                throw new RoleExistException("Role don't exists in organization!");
            }
            Roles role  = check.get();
            role.setName(data.getName());
            role.setCode(data.getCode());
            role.setDescription(data.getDescription());
            role.setOrganizationId(data.getOrganizationId());
            rolesRepository.save(role);
            return true;
        }
        catch (Exception e)
        {
            throw new SavePermissionsFailedException("ExecutionException: " + e.getMessage());
        }
    }
}

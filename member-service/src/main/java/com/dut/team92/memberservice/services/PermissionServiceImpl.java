package com.dut.team92.memberservice.services;

import com.dut.team92.common.security.TokenKey;
import com.dut.team92.common.security.TokenProvider;
import com.dut.team92.memberservice.domain.dto.request.CheckPermissionModel;
import com.dut.team92.memberservice.domain.dto.request.permission.AddUpdateRoleRequest;
import com.dut.team92.memberservice.domain.dto.request.permission.RolesPermissionDto;
import com.dut.team92.memberservice.domain.dto.response.Permission.*;
import com.dut.team92.memberservice.domain.dto.response.Response;
import com.dut.team92.memberservice.domain.entity.*;
import com.dut.team92.memberservice.enums.groupOfScreen;
import com.dut.team92.memberservice.exception.*;
import com.dut.team92.memberservice.repository.FunctionRepository;
import com.dut.team92.memberservice.repository.PermissionsRepository;
import com.dut.team92.memberservice.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    private final TokenProvider tokenProvider;

    @Autowired
    private HttpServletRequest request;

    public PermissionServiceImpl(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Response getRoles(UUID organizationId) {
        Response response = new Response();
        try
        {
            List<RolesResponse> res = rolesRepository.getListRoles(organizationId);
            if(res != null)
            {
                response.setMessage("success");
                response.setRspCode("200");
                response.setData(res);
            }
            else {
                response.setMessage("notfound");
                response.setRspCode("403");
            }
        }
        catch (Exception e)
        {
            response.setMessage("Error: "+ e.toString());
            response.setRspCode("500");
        }
        return response;
    }

    @Override
    public Response getListPermission(Long roleId) {
        Response response = new Response();
        try
        {
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
            if(res != null)
            {
                response.setMessage("success");
                response.setRspCode("200");
                response.setData(res);
            }
            else {
                response.setMessage("notfound");
                response.setRspCode("403");
            }
        }
        catch (Exception e)
        {
            response.setMessage("Error: "+ e.toString());
            response.setRspCode("500");
        }
        return response;
    }

    @Override
    public Response savePermission(long role_id, RolesPermissionDto data) {
        Response res = new Response();
        try
        {
            Optional<Roles> role = rolesRepository.findById(role_id);
            if (!role.isPresent())
            {
                res.setMessage("notfound");
                res.setRspCode("403");
                return res;
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
            res.setMessage("success");
            res.setRspCode("200");
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }

    @Override
    public Response addNewRoles(AddUpdateRoleRequest data) {
        Response res = new Response();
        try
        {
            Optional<Long> exist = rolesRepository.checkExist(data.getName(), data.getOrganizationId());
            if (exist.isPresent())
            {
                res.setMessage("notfound");
                res.setRspCode("403");
                return res;
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
                permission.setFunctionId(function.getFunctionId());
                permission.setEnable(function.isRequired());
                dbPermissions.add(permission);
            });
            permissionsRepository.saveAll(dbPermissions);
            res.setMessage("success");
            res.setRspCode("200");
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }

    @Override
    public Response updateRoles(long roleId,AddUpdateRoleRequest data) {
        Response res = new Response();
        try
        {
            Optional<Roles> check = rolesRepository.findById(roleId);
            if (!check.isPresent())
            {
                res.setMessage("notfound");
                res.setRspCode("403");
                return res;
            }
            Roles role  = check.get();
            role.setName(data.getName());
            role.setCode(data.getCode());
            role.setDescription(data.getDescription());
            role.setOrganizationId(data.getOrganizationId());
            rolesRepository.save(role);
            res.setMessage("success");
            res.setRspCode("200");
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }
    @Override
    public Response checkPermission(CheckPermissionModel data) {
        Response res = new Response();
        try
        {
            String userId = tokenProvider.extractClaim(tokenProvider.parseJwt(request))
                    .get(TokenKey.SUB_ID, String.class);
            int check = rolesRepository.checkPermission(data.getFunctionId(), UUID.fromString(userId), data.getProjectId());
            if(check > 0)
            {
                res.setMessage("success");
                res.setRspCode("200");
            }
            else {
                res.setMessage("Forbidden");
                res.setRspCode("403");
            }
        }
        catch (Exception e)
        {
            res.setMessage("Error: "+ e.toString());
            res.setRspCode("500");
        }
        return res;
    }
}

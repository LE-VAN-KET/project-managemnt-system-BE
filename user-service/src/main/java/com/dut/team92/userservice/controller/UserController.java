package com.dut.team92.userservice.controller;

import com.dut.team92.userservice.domain.dto.UserDto;
import com.dut.team92.userservice.domain.dto.request.UpdateUserDto;
import com.dut.team92.userservice.domain.dto.response.Response;
import com.dut.team92.userservice.proxy.MemberServiceProxy;
import com.dut.team92.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @PostMapping("/organizations/{organization_id}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addBatchAccountMemberByUploadfile(@PathVariable("organization_id") String organizationId,
                                                           @RequestParam("file") MultipartFile file,
                                                           @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                                           @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("ORGANIZER_ADD_MULTY_USER" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return userService.addListUserMemberToOrganization(file, UUID.fromString(organizationId));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object updateInformation(@PathVariable("id") String userId, @RequestBody UpdateUserDto userDto,
                                    @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                    @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("USER_UPDATE_INFOMATION" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return userService.update(userDto, UUID.fromString(userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ORGANIZATION')")
    public Object removeUser(@PathVariable("id") String userId,
                             @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                             @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("ORGANIZER_REMOVE_USER" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        userService.removeUser(UUID.fromString(userId));
        return null;
    }

    @GetMapping("/information")
    @PreAuthorize("isAuthenticated()")
    public UserDto getCurrentUserInformation() {
        return userService.getCurrentUser();
    }
}

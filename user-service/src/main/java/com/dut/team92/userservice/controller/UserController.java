package com.dut.team92.userservice.controller;

import com.dut.team92.userservice.domain.dto.UserDto;
import com.dut.team92.userservice.domain.dto.request.UpdateUserDto;
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

    @PostMapping("/organizations/{organization_id}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDto> addBatchAccountMemberByUploadfile(@PathVariable("organization_id") String organizationId,
                                                           @RequestParam("file") MultipartFile file) {
        return userService.addListUserMemberToOrganization(file, UUID.fromString(organizationId));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateInformation(@PathVariable("id") String userId, @RequestBody UpdateUserDto userDto) {
        return userService.update(userDto, UUID.fromString(userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_ORGANIZATION')")
    public void removeUser(@PathVariable("id") String userId) {
        userService.removeUser(UUID.fromString(userId));
    }

    @GetMapping("/information")
    @PreAuthorize("isAuthenticated()")
    public UserDto getCurrentUserInformation() {
        return userService.getCurrentUser();
    }
}

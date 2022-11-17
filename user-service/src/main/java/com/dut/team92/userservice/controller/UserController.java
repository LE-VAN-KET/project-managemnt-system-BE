package com.dut.team92.userservice.controller;

import com.dut.team92.userservice.domain.dto.UserDto;
import com.dut.team92.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
}

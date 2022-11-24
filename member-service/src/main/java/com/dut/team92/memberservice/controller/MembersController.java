package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.domain.dto.response.CheckExistMemberResponse;
import com.dut.team92.memberservice.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/members")
public class MembersController {
    @Autowired
    private MemberService memberService;

    @GetMapping("check/{member_id}/projects/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public CheckExistMemberResponse checkExistMemberInProject(@PathVariable("member_id") String memberId,
                                                              @PathVariable("project_id") String projectId) {
        boolean existMember = memberService.existMemberInProject(UUID.fromString(memberId),
                UUID.fromString(projectId));
        return CheckExistMemberResponse.builder().isExistMember(existMember).build();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void addMemberToProject(@Valid @RequestBody AddMemberToProjectRequest command) {
        memberService.addMemberToProject(command);
    }

}

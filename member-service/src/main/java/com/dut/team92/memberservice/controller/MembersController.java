package com.dut.team92.memberservice.controller;

import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.dto.UserDto;
import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.domain.dto.response.CheckExistMemberResponse;
import com.dut.team92.memberservice.domain.dto.response.ProjectResponse;
import com.dut.team92.memberservice.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public MemberDto addMemberToProject(@Valid @RequestBody AddMemberToProjectRequest command) {
        return memberService.addMemberToProject(command);
    }

    @GetMapping("/organization/{organization_id}/search")
    public List<UserDto> searchMemberInOrganization(@PathVariable("organization_id") String organizationId,
                                                       @RequestParam(value = "keyword", required = false)
                                                       String keyword) {
        return memberService.searchMemberInOrganization(UUID.fromString(organizationId), keyword);
    }

    @GetMapping("/projects/{project_id}/search")
    public List<MemberDto> searchMemberInProject(@PathVariable("project_id") String projectId,
                                                       @RequestParam(value = "keyword", required = false)
                                                       String keyword) {
        return memberService.searchMemberInProject(UUID.fromString(projectId), keyword);
    }

    @GetMapping("/project-ids")
    public List<ProjectResponse> getListProjectIdMemberAttending(@RequestParam("organization_id") String organizationId,
                                                                 @RequestHeader("Authorization") String authorization) {
        return memberService.getAllProjectIdByUserIdAndOrganizationId(UUID.fromString(organizationId));
    }

    @GetMapping("/{id}")
    public MemberDto getMemberByMemberId(@PathVariable("id") String memberId) {
        return memberService.getMemberById(UUID.fromString(memberId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeMemberInTheProject(@PathVariable("id") String memberId) {
        memberService.removeMemberInTheProject(UUID.fromString(memberId));
    }

}

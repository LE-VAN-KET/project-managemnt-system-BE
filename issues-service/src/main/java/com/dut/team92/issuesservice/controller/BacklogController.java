package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.BacklogDto;
import com.dut.team92.issuesservice.domain.dto.response.Response;
import com.dut.team92.issuesservice.proxy.MemberServiceProxy;
import com.dut.team92.issuesservice.services.BacklogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/backlogs")
public class BacklogController {
    @Autowired
    private BacklogService backlogService;

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object getBacklog(@RequestParam(name = "project_id") String projectId,
                                 @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                 @RequestHeader(value = "Authorization", required = true) String authorizationHeader) throws JsonProcessingException {
            Response res = memberServiceProxy.checkPermission("GET_BACKLOG" , authorizationHeader , prjId);
            if(!res.getRspCode().equals("200"))
            {
                return res;
            }
        return backlogService.getAllIssuesInBacklogAndInSprintStartingOrUnStart(UUID.fromString(projectId));
    }
}

package com.dut.team92.issuesservice.controller;

import com.dut.team92.issuesservice.domain.dto.BoardDto;
import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.SprintDto;
import com.dut.team92.issuesservice.domain.dto.request.CreateIssuesBacklogCommand;
import com.dut.team92.issuesservice.domain.dto.request.IssuesTransferRequest;
import com.dut.team92.issuesservice.domain.dto.request.MoveIssuesCommand;
import com.dut.team92.issuesservice.domain.dto.response.MoveIssuesResponse;
import com.dut.team92.issuesservice.domain.dto.response.Response;
import com.dut.team92.issuesservice.proxy.MemberServiceProxy;
import com.dut.team92.issuesservice.services.IssuesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/issues")
@SecurityRequirement(name = "BearerAuth")
public class IssuesController {

    @Autowired
    private IssuesService issuesService;

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Object createIssues(@Valid @RequestBody CreateIssuesBacklogCommand command,
                                  @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                  @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("ADD_ISSUES" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return issuesService.createIssues(command);
    }

    @GetMapping("/{issues_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getIssues(@PathVariable("issues_id") @NotNull String issuesId,
                            @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                            @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_DETAIL_ISSUES" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return issuesService.get(UUID.fromString(issuesId));
    }

    @GetMapping("/backlog")
    @ResponseStatus(HttpStatus.OK)
    public Object getAllIssuesBacklogByProjectId(@RequestParam(name = "project_id") String projectId,
                                                 @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                                 @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_BACKLOG_ISSUES" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return issuesService.getAllIssuesBacklogByProjectId(UUID.fromString(projectId));
    }

    @PutMapping("/{issues_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object updateDetailsIssues(@PathVariable("issues_id") String issuesId,
                                         @Valid @RequestBody CreateIssuesBacklogCommand command,
                                      @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                      @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("UPDATE_ISSUES" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return issuesService.updateIssues(command, UUID.fromString(issuesId));
    }

    @DeleteMapping("/{issues_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object deleteIssues(@PathVariable("issues_id") String issuesId,
                               @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                               @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("DELTE_ISSUES" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        issuesService.deleteIssues(UUID.fromString(issuesId));
        return  null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object getAllIssuesInProject(@RequestParam("project_id") String projectId,
                                        @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                        @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_ALL_ISSUES" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return issuesService.getAllIssuesInProject(UUID.fromString(projectId));
    }

    @PostMapping("/move")
    @ResponseStatus(HttpStatus.OK)
    public Object moveIssuesBacklogToBacklog(@Valid @RequestBody MoveIssuesCommand command,
                                             @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                             @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("CHANGE_BACKLOG_OF_ISSUES" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return issuesService.moveIssues(command);
    }

    @GetMapping("/boards")
    public Object getAllIssuesInBoardAndSprintStatusRunning(@RequestParam("project_id") String projectId,
                                                            @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                                            @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_DETAIL_SPRINT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return issuesService.getAllIssuesInBoardOfSprintStatusRunningByProjectId(UUID.fromString(projectId));
    }

    @PutMapping("/transfer/to/sprint")
    public Object transferIssuesToNewSprint(@RequestBody IssuesTransferRequest issuesTransferRequest,
                                            @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                            @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("TRANFER_ISSUES_TO_SPRINT" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        issuesService.updateIssuesToSPrint(issuesTransferRequest.getOldBoardList(),
                issuesTransferRequest.getNewBoardList());
        return  null;
    }

    @PutMapping("/transfer/to/backlog")
    public Object transferIssuesToBacklog(@RequestBody List<UUID> boardIdList,
                                          @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                          @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("TRANFER_ISSUES_TO_BACKLOG" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        issuesService.updateIssuesToBacklog(boardIdList);
        return  null;
    }

}

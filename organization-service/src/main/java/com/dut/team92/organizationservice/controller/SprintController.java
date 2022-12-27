package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.MoveIssuesType;
import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.dto.response.Response;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.dut.team92.organizationservice.proxy.MemberServiceProxy;
import com.dut.team92.organizationservice.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {
    @Autowired
    private SprintService sprintService;

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object createSprint(@Valid @RequestBody CreateSprintCommand command,
                                  @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                  @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("ADD_SPINT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return sprintService.createSprint(command);
    }

    @PutMapping("/{sprint_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object updateSprint(@Valid @RequestBody SprintDto sprintDto,
                                  @PathVariable("sprint_id") String sprintId,
                               @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                               @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("UPDATE_SPRINT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        sprintDto.setId(UUID.fromString(sprintId));
        return sprintService.updateSprint(sprintDto);
    }

    @PutMapping("/{sprint_id}/complete")
    public Object completeSprint(@PathVariable("sprint_id") String sprintId,
                               @RequestParam MoveIssuesType moveIssuesType,
                                 @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                 @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("COMPLETE_SPRINT" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        sprintService.completedSprint(UUID.fromString(sprintId), moveIssuesType);
        return null;
    }

    @GetMapping("/backlog")
    public Object getAllSprintStaringOrUnStart(@RequestParam(name = "project_id")
                                                            String projectId,
                                               @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                               @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_SPRINTS_BACKLOG" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return sprintService.getAllSprintByListStatus(UUID.fromString(projectId),
                SprintStatus.UNSTART, SprintStatus.STARTING);
    }

    @GetMapping("/boards")
    public Object getAllSprintRunning(@RequestParam(name = "project_id")
                                                        String projectId,
                                      @RequestHeader(value="PROJECT-ID", required = false) String prjId,
                                      @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("GET_SPRINTS_BOARD" , authorizationHeader , prjId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        return sprintService.getAllSprintByListStatus(UUID.fromString(projectId),
                SprintStatus.STARTING);
    }

}

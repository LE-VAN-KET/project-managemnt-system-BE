package com.dut.team92.issuesservice.proxy;

import com.dut.team92.issuesservice.configuration.CustomFeignClientConfiguration;
import com.dut.team92.issuesservice.domain.dto.response.CheckBoardExistResponse;
import com.dut.team92.issuesservice.domain.dto.response.CheckProjectExistResponse;
import com.dut.team92.issuesservice.domain.dto.response.ProjectKeyResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.constraints.NotNull;

@FeignClient(name = "${app.feign.config.organization-client.name}", url = "${app.feign.config.organization-client.url}",
        configuration = CustomFeignClientConfiguration.class)
@Retry(name = "organization")
public interface OrganizationServiceProxy {
    @GetMapping("/api/organizations/{organization_id}/projects/check/{project_id}")
    CheckProjectExistResponse existProjectByIdAndOrganizationId(
            @PathVariable("organization_id") @NotNull String organizationId,
            @PathVariable("project_id") @NotNull String projectId);

    @GetMapping("/api/boards/check/{board_id}")
    CheckBoardExistResponse existBoardByBoardId(@PathVariable("board_id") @NotNull String boardId,
                                                @RequestHeader(value = "Authorization", required = true)
                                                String authorizationHeader);
    @GetMapping("/api/organizations/{organization_id}/projects/{project_id}/project-key")
    ProjectKeyResponse getProjectKey(@PathVariable("project_id") String projectId,
                                     @PathVariable String organization_id,
                                     @RequestHeader(value = "Authorization", required = true)
                         String authorizationHeader);
}


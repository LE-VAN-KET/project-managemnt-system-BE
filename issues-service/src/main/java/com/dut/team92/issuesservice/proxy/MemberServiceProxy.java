package com.dut.team92.issuesservice.proxy;

import com.dut.team92.issuesservice.configuration.CustomFeignClientConfiguration;
import com.dut.team92.issuesservice.domain.dto.response.CheckExistMemberResponse;
import com.dut.team92.issuesservice.domain.dto.response.Response;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${app.feign.config.member-client.name}", url = "${app.feign.config.member-client.url}",
        configuration = CustomFeignClientConfiguration.class)
@Retry(name = "member")
public interface MemberServiceProxy {

    @GetMapping("/api/members/check/{member_id}/projects/{project_id}")
    CheckExistMemberResponse checkExistMemberInProject(@PathVariable("member_id") String memberId,
                                                       @PathVariable("project_id") String projectId);
    @GetMapping("/api/permissions/check/{functionCode}")
    Response checkPermission(@PathVariable("functionCode") String functionCode,
                             @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                             @RequestHeader(value = "PROJECT-ID", required = false) String projectId);
}

package com.dut.team92.organizationservice.proxy;

import com.dut.team92.organizationservice.configuration.CustomFeignClientConfiguration;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${app.feign.config.member-client.name}", url = "${app.feign.config.member-client.url}",
        configuration = CustomFeignClientConfiguration.class)
@Retry(name = "member")
public interface MemberServiceProxy {

    @GetMapping("/api/members/project-ids")
    Object getListProjectIdMemberAttending(@RequestParam("organization_id") String organizationId,
                                           @RequestHeader(value = "Authorization", required = true)
                                           String authorizationHeader);
}

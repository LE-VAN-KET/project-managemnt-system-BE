package com.dut.team92.userservice.proxy;

import com.dut.team92.userservice.configuration.CustomFeignClientConfiguration;
import com.dut.team92.userservice.domain.dto.response.Response;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${app.feign.config.member-client.name}", url = "${app.feign.config.member-client.url}",
        configuration = CustomFeignClientConfiguration.class)
@Retry(name = "organization")
public interface MemberServiceProxy {
    @GetMapping("/api/roles")
    Object getRolesByUserId(@RequestParam("user_id") String userId,
                            @RequestHeader(value = "Authorization", required = true)
                            String authorizationHeader);

    @GetMapping("/api/permissions/check/{functionCode}")
    Response checkPermission(@PathVariable("functionCode") String functionCode,
                             @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                             @RequestHeader(value = "PROJECT-ID", required = false) String projectId);
}

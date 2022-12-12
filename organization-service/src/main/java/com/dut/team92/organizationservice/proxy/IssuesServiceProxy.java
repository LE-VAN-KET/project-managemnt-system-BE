package com.dut.team92.organizationservice.proxy;

import com.dut.team92.organizationservice.configuration.CustomFeignClientConfiguration;
import com.dut.team92.organizationservice.domain.dto.BoardDto;
import com.dut.team92.organizationservice.domain.dto.request.IssuesTransferRequest;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "${app.feign.config.issues-client.name}", url = "${app.feign.config.issues-client.url}",
        configuration = CustomFeignClientConfiguration.class)
@Retry(name = "issues")
public interface IssuesServiceProxy {

    @PutMapping("/api/issues/transfer/to/sprint")
    Object transferIssuesToNewSprint(@RequestBody IssuesTransferRequest issuesTransferRequest,
                                     @RequestHeader(value = "Authorization", required = true)
                               String authorizationHeader);

    @PutMapping("/api/issues/transfer/to/backlog")
    Object transferIssuesToBacklog(@RequestBody List<UUID> boardIdList,
                               @RequestHeader(value = "Authorization", required = true)
                               String authorizationHeader);
}


package com.dut.team92.userservice.proxy;

import com.dut.team92.userservice.configuration.CustomFeignClientConfiguration;
import com.dut.team92.userservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.userservice.domain.dto.response.CreateOrganizationResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${app.feign.config.organization-client.name}", url = "${app.feign.config.organization-client.url}",
        configuration = CustomFeignClientConfiguration.class)
@Retry(name = "organization")
public interface OrganizationServiceProxy {
    @PostMapping("/api/organizations")
    CreateOrganizationResponse createOrganization(@RequestBody CreateOrganizationCommand
                                                          createOrganizationCommand);
    @GetMapping("/api/organizations/check/{organization_id}")
    boolean checkOrganizationExist(@PathVariable("organization_id") String organizationId);

}


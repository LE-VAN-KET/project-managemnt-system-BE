package com.dut.team92.userservice.proxy;

import com.dut.team92.userservice.configuration.CustomFeignClientConfiguration;
import com.dut.team92.userservice.domain.dto.request.CreateOrganizationCommand;
import com.dut.team92.userservice.domain.dto.response.CreateOrganizationResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${app.feign.config.organization-client.name}", url = "${app.feign.config.organization-client.url}",
        configuration = CustomFeignClientConfiguration.class)
@Retry(name = "organization")
public interface OrganizationServiceProxy {
    @PostMapping("/api/organizations")
    CreateOrganizationResponse createOrganization(@RequestBody CreateOrganizationCommand
                                                          createOrganizationCommand);
//    @Component
//    class Fallback implements OrganizationServiceProxy {
//        private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//        @Override
//        public CreateOrganizationResponse createOrganization(CreateOrganizationCommand createOrganizationCommand) {
//            log.info("Fallback");
//            return null;
//        }
//    }
}


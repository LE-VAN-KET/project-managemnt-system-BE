package com.dut.team92.organizationservice.configuration;

import com.dut.team92.organizationservice.proxy.FeignCustomErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFeignClientConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignCustomErrorDecoder();
    }
}

package com.dut.team92.issuesservice;

import com.dut.team92.common.configuration.SharedConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@Import(SharedConfigurationReference.class)
@EnableJpaAuditing
public class IssuesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssuesServiceApplication.class, args);
    }

}

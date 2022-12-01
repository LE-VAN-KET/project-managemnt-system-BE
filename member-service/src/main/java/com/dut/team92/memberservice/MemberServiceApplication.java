package com.dut.team92.memberservice;

import com.dut.team92.common.configuration.SharedConfigurationReference;
import com.dut.team92.kafka.KafkaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import({SharedConfigurationReference.class, KafkaApplication.class})
public class MemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }

}

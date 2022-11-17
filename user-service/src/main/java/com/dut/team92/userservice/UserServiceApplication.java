package com.dut.team92.userservice;

import com.dut.team92.common.configuration.SharedConfigurationReference;
import com.dut.team92.kafka.KafkaApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@Import({SharedConfigurationReference.class, KafkaApplication.class})
@EnableJpaAuditing
@EnableFeignClients
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}

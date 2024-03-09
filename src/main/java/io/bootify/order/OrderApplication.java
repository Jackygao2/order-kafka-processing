package io.bootify.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class OrderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}

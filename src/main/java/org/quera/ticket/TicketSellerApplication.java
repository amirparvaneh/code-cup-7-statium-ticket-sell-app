package org.quera.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class TicketSellerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketSellerApplication.class, args);
    }
}

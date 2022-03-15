package com.brihaspathee.zeus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileMgmtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileMgmtServiceApplication.class, args);
    }

}

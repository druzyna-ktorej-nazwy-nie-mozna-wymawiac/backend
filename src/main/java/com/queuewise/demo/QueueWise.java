package com.queuewise.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackageClasses = {})
public class QueueWise {

    public static void main(String[] args) {
        SpringApplication.run(QueueWise.class, args);
    }

}

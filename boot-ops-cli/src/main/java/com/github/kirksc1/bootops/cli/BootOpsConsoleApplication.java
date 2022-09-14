package com.github.kirksc1.bootops.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The BootOpsConsoleApplication class provides the entry point for the command line execution process via the
 * main(String[] args) method.
 */
@SpringBootApplication
@Slf4j
public class BootOpsConsoleApplication {

    public static void main(String[] args) {
        log.info("Boot Ops Processing Initiated");
        SpringApplication.run(BootOpsConsoleApplication.class, args);
        log.info("Boot Ops Processing Completed");
    }
}

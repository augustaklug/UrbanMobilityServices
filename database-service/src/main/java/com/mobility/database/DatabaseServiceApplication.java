package com.mobility.database;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DatabaseServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DatabaseServiceApplication.class, args);

        // Execute Flyway migrations manually
        Flyway flyway = context.getBean(Flyway.class);
        flyway.migrate();

        // Keep the application running
        synchronized (DatabaseServiceApplication.class) {
            try {
                DatabaseServiceApplication.class.wait();
            } catch (InterruptedException e) {
                // Log the interruption
            }
        }
    }
}

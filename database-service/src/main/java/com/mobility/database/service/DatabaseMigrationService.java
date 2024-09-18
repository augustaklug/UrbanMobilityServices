package com.mobility.database.service;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseMigrationService {

    private final Flyway flyway;

    public void migrateDatabase() {
        flyway.migrate();
    }
}

package com.github.mjeanroy.mongohero.core.services;

import com.github.mjeanroy.mongohero.core.exceptions.DatabaseNotFoundException;
import com.github.mjeanroy.mongohero.core.model.Database;
import com.github.mjeanroy.mongohero.core.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class DatabaseService {

    private final DatabaseRepository databaseRepository;

    @Autowired
    DatabaseService(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    public Stream<Database> findAll() {
        return databaseRepository.listDatabases();
    }

    public Database findOneOrFail(String name) {
        return databaseRepository.getDatabase(name).orElseThrow(() ->
                new DatabaseNotFoundException(name)
        );
    }
}

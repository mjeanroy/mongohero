package com.github.mjeanroy.mongohere.core.services;

import com.github.mjeanroy.mongohere.core.exceptions.DatabaseNotFoundException;
import com.github.mjeanroy.mongohere.core.model.Database;
import com.github.mjeanroy.mongohere.core.repository.DatabaseRepository;
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
        return databaseRepository.findAll();
    }

    public Database findOneOrFail(String name) {
        return databaseRepository.findOne(name).orElseThrow(() ->
                new DatabaseNotFoundException(name)
        );
    }
}

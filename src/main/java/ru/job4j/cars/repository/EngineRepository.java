package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Класс репозитория - двигателя.
 * Реализация методов работы с обьектом Engine.
 */
@Repository
@AllArgsConstructor
public class EngineRepository {

    private final CrudRepository crudRepository;

    public Engine create(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    public void delete(int engineId) {
        findById(engineId)
                .ifPresent(engine -> crudRepository.run(session -> session.delete(engine)));
    }

    public List<Engine> findAll() {
        return crudRepository.query("from Engine ORDER BY id", Engine.class);
    }

    public List<Engine> findByName(String name) {
        return crudRepository.query("from Engine as i where i.name = :fName",
                Engine.class, Map.of("fName", name));
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional("from Engine as i where i.id = :fId",
                Engine.class, Map.of("fId", id));
    }
}
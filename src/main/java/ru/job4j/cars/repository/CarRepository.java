package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     * @param car машина.
     * @return пользователь с id.
     */
    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    /**
     * Обновить в базе машину.
     * @param car пользователь.
     */
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    /**
     * Удалить car по id.
     * @param carId ID
     */
    public void delete(int carId) {
        crudRepository.run(
                "delete from car where id = :fId",
                Map.of("fId", carId)
        );
    }

    /**
     * Найти car по ID
     * @return car.
     */
    public Optional<Car> findById(int carId) {
        return crudRepository.optional("FROM Car f JOIN FETCH f.engine "
                + "JOIN FETCH f.owners "
                + "WHERE f.id = :id", Car.class, Map.of("id", carId));
    }

    /**
     * Список авто.
     *
     * @return список задач.
     */
    public List<Car> findAll() {
        return crudRepository.query("FROM Car f JOIN FETCH f.engine "
                + "JOIN FETCH f.owners "
                + "order by f.id ASC", Car.class);
    }
}

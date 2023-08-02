package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.Collection;
import ru.job4j.cars.model.User;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE auto_user SET name = :fName and password = :fPassword WHERE id = :fId")
                    .setParameter("fName", "new name")
                    .setParameter("fPassword", "new password")
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        List<User> list = new ArrayList<>();
        Session session = sf.openSession();
        Query query = session.createQuery("from User");
        for (Object st : query.getResultList()) {
            list.add((User) st);
        }
        return list;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        User result = session.get(User.class, userId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        List<User> list = new ArrayList<>();
        Session session = sf.openSession();
        Query query = session.createQuery(
                "from User as u where u.login like :fKey", User.class);
        query.setParameter("fKey", "%" + key + "%");
        for (Object st : query.getResultList()) {
            list.add((User) st);
        }
        return list;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        org.hibernate.query.Query<?> query = session.createQuery(
                "from User as u where u.login = :fLogin", User.class);
        query.setParameter("fLogin", login);
        return (Optional<User>) query.uniqueResult();
    }
}
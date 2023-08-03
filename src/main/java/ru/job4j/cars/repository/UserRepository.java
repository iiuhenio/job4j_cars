package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;
import javax.persistence.*;

import java.util.ArrayList;
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
        } finally {
            session.close();
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
                            "UPDATE auto_user SET login = :fLogin and password = :fPassword WHERE id = :fId")
                    .setParameter("fName", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
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
        } finally {
            session.close();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        List list = new ArrayList<>();
        Session session = sf.openSession();
        try {
            Query query = session.createQuery("from User", User.class);
            /*
            for (Object st : query.getResultList()) {
                list.add((User) st);
            }
             */
            list = query.getResultList();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = sf.openSession();
        User result = session.get(User.class, userId);
        try {
            session.beginTransaction();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return Optional.ofNullable(result);
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        List list = new ArrayList<>();
        Session session = sf.openSession();
        try {
            Query query = session.createQuery(
                    "from User as u where u.login like :fKey", User.class);
            query.setParameter("fKey", "%" + key + "%");
            /*
            for (Object st : query.getResultList()) {
                list.add((User) st);
            }
             */
            list = query.getResultList();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Optional<User> optionalUser = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE login = :fLogin", User.class);
            query.setParameter("fLogin", login);
            optionalUser = (Optional<User>) ((org.hibernate.query.Query<?>) query).uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return optionalUser;
    }
}
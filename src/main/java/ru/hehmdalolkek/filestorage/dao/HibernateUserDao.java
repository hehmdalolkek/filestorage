package ru.hehmdalolkek.filestorage.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.hehmdalolkek.filestorage.exception.DatabaseException;
import ru.hehmdalolkek.filestorage.model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.hehmdalolkek.filestorage.util.HibernateUtil.openSession;

public class HibernateUserDao implements UserDao {

    @Override
    public Optional<User> findById(Integer id) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.find(User.class, id);
                transaction.commit();
                return Optional.ofNullable(user);
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                List<User> users = session.createQuery("from User", User.class)
                        .getResultList();
                transaction.commit();
                return users;
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public User save(User user) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User savedUser = session.merge(user);
                transaction.commit();
                return savedUser;
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createMutationQuery("delete from User where id = :id")
                        .setParameter("id", id)
                        .executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

}

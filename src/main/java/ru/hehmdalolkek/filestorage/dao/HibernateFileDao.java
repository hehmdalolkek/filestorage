package ru.hehmdalolkek.filestorage.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.hehmdalolkek.filestorage.exception.DatabaseException;
import ru.hehmdalolkek.filestorage.model.File;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.hehmdalolkek.filestorage.util.HibernateUtil.openSession;

public class HibernateFileDao implements FileDao {

    @Override
    public Optional<File> findById(Integer id) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                File file = session.find(File.class, id);
                transaction.commit();
                return Optional.ofNullable(file);
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<File> findAll() {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                List<File> files = session.createQuery("from File", File.class)
                        .getResultList();
                transaction.commit();
                return files;
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public File save(File file) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                File savedFile = session.merge(file);
                transaction.commit();
                return savedFile;
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
                session.createMutationQuery("delete from File where id = :id")
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

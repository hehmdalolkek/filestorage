package ru.hehmdalolkek.filestorage.dao;

import jakarta.persistence.EntityGraph;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.hehmdalolkek.filestorage.exception.DatabaseException;
import ru.hehmdalolkek.filestorage.model.Event;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static ru.hehmdalolkek.filestorage.util.HibernateUtil.openSession;

public class HibernateEventDao implements EventDao {

    @Override
    public Optional<Event> findById(Integer id) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                EntityGraph<?> entityGraph = session.getEntityGraph("eventEntityGraph");
                Map<String, Object> queryProperties = Map.of("jakarta.persistence.loadgraph", entityGraph);
                Event event = session.find(Event.class, id, queryProperties);
                transaction.commit();
                return Optional.ofNullable(event);
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<Event> findAll() {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                EntityGraph<?> entityGraph = session.getEntityGraph("eventEntityGraph");
                List<Event> events = session.createQuery("from Event", Event.class)
                        .setHint("javax.persistence.loadgraph", entityGraph)
                        .getResultList();
                transaction.commit();
                return events;
            } catch (Exception e) {
                if (Objects.nonNull(transaction)) {
                    transaction.rollback();
                }
                throw new DatabaseException(e.getMessage(), e);
            }
        }
    }

    @Override
    public Event save(Event event) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Event savedEvent = session.merge(event);
                transaction.commit();
                return savedEvent;
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
                session.createMutationQuery("delete from Event where id = :id")
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

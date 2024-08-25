package ru.hehmdalolkek.filestorage.service;

import jakarta.transaction.Transactional;
import ru.hehmdalolkek.filestorage.dao.EventDao;
import ru.hehmdalolkek.filestorage.exception.EventNotFoundException;
import ru.hehmdalolkek.filestorage.model.Event;

import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    @Transactional
    public Event findById(Integer id) {
        return this.eventDao.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException("Event with id=%d not found".formatted(id)));
    }

    @Override
    @Transactional
    public List<Event> findAll() {
        return this.eventDao.findAll();
    }

    @Override
    @Transactional
    public Event save(Event event) {
        return this.eventDao.save(event);
    }

    @Override
    @Transactional
    public Event update(Event event) {
        Event foundedEvent = this.eventDao.findById(event.getId())
                .orElseThrow(() ->
                        new EventNotFoundException("Event with id=%d not found".formatted(event.getId())));
        foundedEvent.setUser(event.getUser());
        foundedEvent.setFile(event.getFile());
        return this.eventDao.save(foundedEvent);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        this.eventDao.deleteById(id);
    }
}

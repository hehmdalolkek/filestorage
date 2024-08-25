package ru.hehmdalolkek.filestorage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.hehmdalolkek.filestorage.dao.EventDao;
import ru.hehmdalolkek.filestorage.exception.EventNotFoundException;
import ru.hehmdalolkek.filestorage.model.Event;
import ru.hehmdalolkek.filestorage.util.EventUtil;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    private final EventService eventService;

    private final EventDao eventDao;

    public EventServiceImplTest() {
        this.eventDao = mock(EventDao.class);
        this.eventService = new EventServiceImpl(this.eventDao);
    }

    @Test
    @DisplayName("Test findById() with exists event")
    public void givenExistsId_whenFindEvent_thenReturnEvent() {
        // given
        Integer id = 1;
        Event event = EventUtil.getPersistedEvent1();
        when(this.eventDao.findById(anyInt()))
                .thenReturn(Optional.of(event));

        // when
        Event result = this.eventService.findById(id);

        // then
        assertThat(result).isEqualTo(event);
        verify(this.eventDao).findById(anyInt());
        verifyNoMoreInteractions(this.eventDao);
    }

    @Test
    @DisplayName("Test findById() with non-exists event")
    public void givenNonExistsId_whenFindEvent_thenThrowException() {
        // given
        Integer id = 1;
        when(this.eventDao.findById(anyInt()))
                .thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> this.eventService.findById(id))
                .isInstanceOf(EventNotFoundException.class)
                .hasMessage("Event with id=%d not found".formatted(id));
        verify(this.eventDao).findById(anyInt());
        verifyNoMoreInteractions(this.eventDao);
    }

    @Test
    @DisplayName("Test findAll() functionality")
    public void givenEvents_whenFindAll_thenReturnListOfEvents() {
        // given
        Event event1 = EventUtil.getPersistedEvent1();
        Event event2 = EventUtil.getPersistedEvent2();
        List<Event> events = List.of(event1, event2);
        when(this.eventDao.findAll())
                .thenReturn(events);

        // when
        List<Event> result = this.eventService.findAll();

        // then
        assertThat(result).isEqualTo(events);
        verify(this.eventDao).findAll();
        verifyNoMoreInteractions(this.eventDao);
    }

    @Test
    @DisplayName("Test save() functionality")
    public void givenEvent_whenSave_thenReturnEvent() {
        // given
        Event transparentEvent = EventUtil.getTransparentEvent1();
        Event persistedEvent = EventUtil.getPersistedEvent1();
        when(this.eventDao.save(any(Event.class))).thenReturn(persistedEvent);

        // when
        Event result = this.eventService.save(transparentEvent);

        // then

        assertThat(result).isEqualTo(persistedEvent);
        verify(this.eventDao).save(any(Event.class));
        verifyNoMoreInteractions(this.eventDao);
    }

    @Test
    @DisplayName("Test update() with exists event id")
    public void givenEventWithExistsId_whenUpdate_thenReturnEvent() {
        // given
        Event event = EventUtil.getPersistedEvent1();
        when(this.eventDao.findById(anyInt()))
                .thenReturn(Optional.of(event));
        when(this.eventDao.save(any(Event.class)))
                .thenReturn(event);

        // when
        Event result = this.eventService.update(event);

        // then

        assertThat(result).isEqualTo(event);
        verify(this.eventDao).findById(anyInt());
        verify(this.eventDao).save(any(Event.class));
        verifyNoMoreInteractions(this.eventDao);
    }


    @Test
    @DisplayName("Test update() with non-exists event id")
    public void givenEventWithNonExistsId_whenUpdate_thenThrowException() {
        // given
        Event event = EventUtil.getPersistedEvent1();
        when(this.eventDao.findById(anyInt()))
                .thenReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> this.eventService.update(event))
                .isInstanceOf(EventNotFoundException.class)
                .hasMessage("Event with id=%d not found".formatted(event.getId()));
        verify(this.eventDao).findById(anyInt());
        verifyNoMoreInteractions(this.eventDao);
    }

    @Test
    @DisplayName("Test delete() functionality")
    public void givenId_whenDelete_thenDeleteEvent() {
        // given
        Integer id = 1;

        // when
        this.eventService.delete(id);

        // then
        verify(this.eventDao).deleteById(anyInt());
        verifyNoMoreInteractions(this.eventDao);
    }

}

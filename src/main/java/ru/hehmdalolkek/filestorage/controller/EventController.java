
package ru.hehmdalolkek.filestorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.hehmdalolkek.filestorage.dao.HibernateEventDao;
import ru.hehmdalolkek.filestorage.model.Event;
import ru.hehmdalolkek.filestorage.service.EventService;
import ru.hehmdalolkek.filestorage.service.EventServiceImpl;
import ru.hehmdalolkek.filestorage.util.ControllerUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/api/v1/events/*")
public class EventController extends HttpServlet {

    private final EventService eventService;

    private final ObjectMapper objectMapper;

    public EventController() {
        HibernateEventDao eventDao = new HibernateEventDao();
        this.eventService = new EventServiceImpl(eventDao);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        String pathInfo = req.getPathInfo();

        if (Objects.isNull(pathInfo) || pathInfo.isBlank()) {
            List<Event> events = this.eventService.findAll();
            this.objectMapper.writeValue(resp.getWriter(), events);
        } else {
            Integer id = ControllerUtil.parseIdFromPathInfo(pathInfo);
            Event event = this.eventService.findById(id);
            this.objectMapper.writeValue(resp.getWriter(), event);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Event event = this.objectMapper.readValue(req.getReader(), Event.class);
        Event savedEvent = this.eventService.save(event);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        this.objectMapper.writeValue(resp.getWriter(), savedEvent);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Event event = this.objectMapper.readValue(req.getReader(), Event.class);
        Event updatedEvent = this.eventService.update(event);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        this.objectMapper.writeValue(resp.getWriter(), updatedEvent);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Integer id = ControllerUtil.parseIdFromPathInfo(pathInfo);
        this.eventService.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

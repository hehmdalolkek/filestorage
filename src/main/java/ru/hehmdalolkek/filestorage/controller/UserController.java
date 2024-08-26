package ru.hehmdalolkek.filestorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.hehmdalolkek.filestorage.dao.HibernateUserDao;
import ru.hehmdalolkek.filestorage.model.User;
import ru.hehmdalolkek.filestorage.service.UserService;
import ru.hehmdalolkek.filestorage.service.UserServiceImpl;
import ru.hehmdalolkek.filestorage.util.ControllerUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/api/v1/users/*")
public class UserController extends HttpServlet {

    private final UserService userService;

    private final ObjectMapper objectMapper;

    public UserController() {
        HibernateUserDao userDao = new HibernateUserDao();
        this.userService = new UserServiceImpl(userDao);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        String pathInfo = req.getPathInfo();

        if (Objects.isNull(pathInfo) || pathInfo.isBlank()) {
            List<User> users = this.userService.findAll();
            this.objectMapper.writeValue(resp.getWriter(), users);
        } else {
            Integer id = ControllerUtil.parseIdFromPathInfo(pathInfo);
            User user = this.userService.findById(id);
            this.objectMapper.writeValue(resp.getWriter(), user);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = this.objectMapper.readValue(req.getReader(), User.class);
        User savedUser = this.userService.save(user);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        this.objectMapper.writeValue(resp.getWriter(), savedUser);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = this.objectMapper.readValue(req.getReader(), User.class);
        User updatedUser = this.userService.update(user);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        this.objectMapper.writeValue(resp.getWriter(), updatedUser);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Integer id = ControllerUtil.parseIdFromPathInfo(pathInfo);
        this.userService.delete(id);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

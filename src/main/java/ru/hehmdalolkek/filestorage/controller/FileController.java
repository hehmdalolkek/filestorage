package ru.hehmdalolkek.filestorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.hehmdalolkek.filestorage.dao.*;
import ru.hehmdalolkek.filestorage.exception.BadRequestException;
import ru.hehmdalolkek.filestorage.model.Event;
import ru.hehmdalolkek.filestorage.model.File;
import ru.hehmdalolkek.filestorage.model.User;
import ru.hehmdalolkek.filestorage.service.*;
import ru.hehmdalolkek.filestorage.util.ControllerUtil;
import ru.hehmdalolkek.filestorage.util.FileUploadUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/api/v1/files/*")
@MultipartConfig
public class FileController extends HttpServlet {

    private final FileService fileService;

    private final UserService userService;

    private final EventService eventService;

    private final ObjectMapper objectMapper;

    public FileController() {
        FileDao fileDao = new HibernateFileDao();
        this.fileService = new FileServiceImpl(fileDao);
        UserDao userDao = new HibernateUserDao();
        this.userService = new UserServiceImpl(userDao);
        EventDao eventDao = new HibernateEventDao();
        this.eventService = new EventServiceImpl(eventDao);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        String pathInfo = req.getPathInfo();

        if (Objects.isNull(pathInfo) || pathInfo.isBlank()) {
            List<File> files = this.fileService.findAll();
            this.objectMapper.writeValue(resp.getWriter(), files);
        } else {
            Integer id = ControllerUtil.parseIdFromPathInfo(pathInfo);
            File file = this.fileService.findById(id);
            this.objectMapper.writeValue(resp.getWriter(), file);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.isNull(req.getParameter("user_id"))) {
            throw new BadRequestException("User id is required");
        }
        Integer userId = Integer.parseInt(req.getParameter("user_id"));
        User user = this.userService.findById(userId);

        Part file = req.getPart("file");
        File uploadedFile = FileUploadUtil.uploadFile(file);
        File savedFile = this.fileService.save(uploadedFile);

        Event event = new Event(null, user, savedFile);
        this.eventService.save(event);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        this.objectMapper.writeValue(resp.getWriter(), savedFile);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = this.objectMapper.readValue(req.getReader(), File.class);
        File updatedFile = this.fileService.update(file);
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        this.objectMapper.writeValue(resp.getWriter(), updatedFile);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (Objects.isNull(pathInfo) || pathInfo.isBlank()) {
            throw new BadRequestException("Not found id of file");
        } else {
            Integer id = ControllerUtil.parseIdFromPathInfo(pathInfo);
            File file = this.fileService.findById(id);
            this.fileService.delete(id);
            FileUploadUtil.deleteFile(file.getFilePath());
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

}

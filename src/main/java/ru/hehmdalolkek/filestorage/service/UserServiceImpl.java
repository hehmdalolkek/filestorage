package ru.hehmdalolkek.filestorage.service;

import jakarta.transaction.Transactional;
import ru.hehmdalolkek.filestorage.dao.UserDao;
import ru.hehmdalolkek.filestorage.exception.UserNotFoundException;
import ru.hehmdalolkek.filestorage.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User findById(Integer id) {
        return this.userDao.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id=%d not found".formatted(id)));
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return this.userDao.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        return this.userDao.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User foundedUser = this.userDao.findById(user.getId())
                .orElseThrow(() ->
                        new UserNotFoundException("User with id=%d not found".formatted(user.getId())));
        foundedUser.setName(user.getName());
        foundedUser.setEvents(user.getEvents());
        return this.userDao.save(foundedUser);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        this.userDao.deleteById(id);
    }
}

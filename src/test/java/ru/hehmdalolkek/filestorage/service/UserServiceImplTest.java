package ru.hehmdalolkek.filestorage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.hehmdalolkek.filestorage.dao.UserDao;
import ru.hehmdalolkek.filestorage.exception.UserNotFoundException;
import ru.hehmdalolkek.filestorage.model.User;
import ru.hehmdalolkek.filestorage.util.UserUtil;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private final UserService userService;

    private final UserDao userDao;

    public UserServiceImplTest() {
        this.userDao = mock(UserDao.class);
        this.userService = new UserServiceImpl(userDao);
    }

    @Test
    @DisplayName("Test findById() with exists user")
    public void givenExistsId_whenFindUser_thenReturnUser() {
        // given
        Integer id = 1;
        User user = UserUtil.getPersistedUser1();
        when(this.userDao.findById(anyInt()))
                .thenReturn(Optional.of(user));

        // when
        User result = this.userService.findById(id);

        // then
        assertThat(result).isEqualTo(user);
        verify(this.userDao).findById(anyInt());
        verifyNoMoreInteractions(this.userDao);
    }

    @Test
    @DisplayName("Test findById() with non-exists user")
    public void givenNonExistsId_whenFindUser_thenThrowException() {
        // given
        Integer id = 1;
        when(this.userDao.findById(anyInt()))
                .thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> this.userService.findById(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id=%d not found".formatted(id));
        verify(this.userDao).findById(anyInt());
        verifyNoMoreInteractions(this.userDao);
    }

    @Test
    @DisplayName("Test findAll() functionality")
    public void givenUsers_whenFindAll_thenReturnListOfUsers() {
        // given
        User user1 = UserUtil.getPersistedUser1();
        User user2 = UserUtil.getPersistedUser2();
        List<User> users = List.of(user1, user2);
        when(this.userDao.findAll())
                .thenReturn(users);

        // when
        List<User> result = this.userService.findAll();

        // then
        assertThat(result).isEqualTo(users);
        verify(this.userDao).findAll();
        verifyNoMoreInteractions(this.userDao);
    }

    @Test
    @DisplayName("Test save() functionality")
    public void givenUser_whenSave_thenReturnUser() {
        // given
        User transparentUser = UserUtil.getTransparentUser1();
        User persistedUser = UserUtil.getPersistedUser1();
        when(this.userDao.save(any(User.class))).thenReturn(persistedUser);

        // when
        User result = this.userService.save(transparentUser);

        // then

        assertThat(result).isEqualTo(persistedUser);
        verify(this.userDao).save(any(User.class));
        verifyNoMoreInteractions(this.userDao);
    }

    @Test
    @DisplayName("Test update() with exists user id")
    public void givenUserWithExistsId_whenUpdate_thenReturnUser() {
        // given
        User user = UserUtil.getPersistedUser1();
        when(this.userDao.findById(anyInt()))
                .thenReturn(Optional.of(user));
        when(this.userDao.save(any(User.class)))
                .thenReturn(user);

        // when
        User result = this.userService.update(user);

        // then

        assertThat(result).isEqualTo(user);
        verify(this.userDao).findById(anyInt());
        verify(this.userDao).save(any(User.class));
        verifyNoMoreInteractions(this.userDao);
    }


    @Test
    @DisplayName("Test update() with non-exists user id")
    public void givenUserWithNonExistsId_whenUpdate_thenThrowException() {
        // given
        User user = UserUtil.getPersistedUser1();
        when(this.userDao.findById(anyInt()))
                .thenReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> this.userService.update(user))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id=%d not found".formatted(user.getId()));
        verify(this.userDao).findById(anyInt());
        verifyNoMoreInteractions(this.userDao);
    }

    @Test
    @DisplayName("Test delete() functionality")
    public void givenId_whenDelete_thenDeleteUser() {
        // given
        Integer id = 1;

        // when
        this.userService.delete(id);

        // then
        verify(this.userDao).deleteById(anyInt());
        verifyNoMoreInteractions(this.userDao);
    }

}

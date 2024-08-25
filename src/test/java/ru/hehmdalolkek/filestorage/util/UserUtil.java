package ru.hehmdalolkek.filestorage.util;

import ru.hehmdalolkek.filestorage.model.User;

public class UserUtil {

    public static User getTransparentUser1() {
        return new User(null, "1", null);
    }

    public static User getTransparentUser2() {
        return new User(null, "2", null);
    }

    public static User getPersistedUser1() {
        return new User(1, "1", null);
    }

    public static User getPersistedUser2() {
        return new User(2, "2", null);
    }

}

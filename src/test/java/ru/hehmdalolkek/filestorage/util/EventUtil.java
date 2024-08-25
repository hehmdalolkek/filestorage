package ru.hehmdalolkek.filestorage.util;

import ru.hehmdalolkek.filestorage.model.Event;
import ru.hehmdalolkek.filestorage.model.File;
import ru.hehmdalolkek.filestorage.model.User;

public class EventUtil {

    public static Event getTransparentEvent1() {
        User user = UserUtil.getPersistedUser1();
        File file = FileUtil.getPersistedFile1();
        return new Event(null, user, file);
    }

    public static Event getTransparentEvent2() {
        User user = UserUtil.getPersistedUser2();
        File file = FileUtil.getPersistedFile2();
        return new Event(null, user, file);
    }

    public static Event getPersistedEvent1() {
        User user = UserUtil.getPersistedUser1();
        File file = FileUtil.getPersistedFile1();
        return new Event(1, user, file);
    }

    public static Event getPersistedEvent2() {
        User user = UserUtil.getPersistedUser2();
        File file = FileUtil.getPersistedFile2();
        return new Event(2, user, file);
    }

}

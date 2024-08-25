package ru.hehmdalolkek.filestorage.util;

import ru.hehmdalolkek.filestorage.model.File;

public class FileUtil {

    public static File getTransparentFile1() {
        return new File(null, "1", "1");
    }

    public static File getTransparentFile2() {
        return new File(null, "2", "2");
    }

    public static File getPersistedFile1() {
        return new File(1, "1", "1");
    }

    public static File getPersistedFile2() {
        return new File(2, "2", "2");
    }

}

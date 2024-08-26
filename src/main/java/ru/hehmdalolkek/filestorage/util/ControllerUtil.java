package ru.hehmdalolkek.filestorage.util;

import ru.hehmdalolkek.filestorage.exception.BadRequestException;

public class ControllerUtil {

    public static Integer parseIdFromPathInfo(String pathInfo) {
        boolean pathHasId = pathInfo.matches("^/\\d+$");
        if (!pathHasId) {
            throw new BadRequestException("Invalid path.");
        }
        String id = pathInfo.substring(1);
        return Integer.parseInt(id);
    }

}

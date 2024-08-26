package ru.hehmdalolkek.filestorage.util;

import jakarta.servlet.http.Part;
import ru.hehmdalolkek.filestorage.model.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadUtil {

    private final static String FILE_PATH = "/files";

    public static File uploadFile(Part file) throws IOException {
        String suffix = file.getSubmittedFileName()
                .substring(file.getSubmittedFileName().lastIndexOf('.'));
        String fileName = UUID.randomUUID() + suffix;

        Path directoryPath = Paths.get(FILE_PATH);
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }

        Path filePath = directoryPath.resolve(fileName);
        Files.write(filePath, file.getInputStream().readAllBytes());

        return new File(null, fileName, filePath.toString());
    }

    public static void deleteFile(String filePath) throws IOException {
        Files.delete(Path.of(filePath));
    }

}

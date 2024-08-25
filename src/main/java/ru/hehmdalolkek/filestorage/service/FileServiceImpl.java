package ru.hehmdalolkek.filestorage.service;

import jakarta.transaction.Transactional;
import ru.hehmdalolkek.filestorage.dao.FileDao;
import ru.hehmdalolkek.filestorage.exception.FileNotFoundException;
import ru.hehmdalolkek.filestorage.model.File;

import java.util.List;

public class FileServiceImpl implements FileService {

    private final FileDao fileDao;

    public FileServiceImpl(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    @Transactional
    public File findById(Integer id) {
        return this.fileDao.findById(id)
                .orElseThrow(() ->
                        new FileNotFoundException("File with id=%d not found".formatted(id)));
    }

    @Override
    @Transactional
    public List<File> findAll() {
        return this.fileDao.findAll();
    }

    @Override
    @Transactional
    public File save(File file) {
        return this.fileDao.save(file);
    }

    @Override
    @Transactional
    public File update(File file) {
        File foundedFile = this.fileDao.findById(file.getId())
                .orElseThrow(() ->
                        new FileNotFoundException("File with id=%d not found".formatted(file.getId())));
        foundedFile.setName(file.getName());
        foundedFile.setFilePath(file.getFilePath());
        return this.fileDao.save(foundedFile);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        this.fileDao.deleteById(id);
    }
}

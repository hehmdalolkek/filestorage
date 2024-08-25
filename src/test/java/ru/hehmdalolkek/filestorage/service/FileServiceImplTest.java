package ru.hehmdalolkek.filestorage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.hehmdalolkek.filestorage.dao.FileDao;
import ru.hehmdalolkek.filestorage.exception.FileNotFoundException;
import ru.hehmdalolkek.filestorage.model.File;
import ru.hehmdalolkek.filestorage.util.FileUtil;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class FileServiceImplTest {

    private final FileService fileService;

    private final FileDao fileDao;

    public FileServiceImplTest() {
        this.fileDao = mock(FileDao.class);
        this.fileService = new FileServiceImpl(fileDao);
    }

    @Test
    @DisplayName("Test findById() with exists file")
    public void givenExistsId_whenFindFile_thenReturnFile() {
        // given
        Integer id = 1;
        File file = FileUtil.getPersistedFile1();
        when(this.fileDao.findById(anyInt()))
                .thenReturn(Optional.of(file));

        // when
        File result = this.fileService.findById(id);

        // then
        assertThat(result).isEqualTo(file);
        verify(this.fileDao).findById(anyInt());
        verifyNoMoreInteractions(this.fileDao);
    }

    @Test
    @DisplayName("Test findById() with non-exists file")
    public void givenNonExistsId_whenFindFile_thenThrowException() {
        // given
        Integer id = 1;
        when(this.fileDao.findById(anyInt()))
                .thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> this.fileService.findById(id))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessage("File with id=%d not found".formatted(id));
        verify(this.fileDao).findById(anyInt());
        verifyNoMoreInteractions(this.fileDao);
    }

    @Test
    @DisplayName("Test findAll() functionality")
    public void givenFiles_whenFindAll_thenReturnListOfFiles() {
        // given
        File file1 = FileUtil.getPersistedFile1();
        File file2 = FileUtil.getPersistedFile2();
        List<File> files = List.of(file1, file2);
        when(this.fileDao.findAll())
                .thenReturn(files);

        // when
        List<File> result = this.fileService.findAll();

        // then

        assertThat(result).isEqualTo(files);
        verify(this.fileDao).findAll();
        verifyNoMoreInteractions(this.fileDao);
    }

    @Test
    @DisplayName("Test save() functionality")
    public void givenFile_whenSave_thenReturnFile() {
        // given
        File transparentFile = FileUtil.getTransparentFile1();
        File persistedFile = FileUtil.getPersistedFile1();
        when(this.fileDao.save(any(File.class))).thenReturn(persistedFile);

        // when
        File result = this.fileService.save(transparentFile);

        // then

        assertThat(result).isEqualTo(persistedFile);
        verify(this.fileDao).save(any(File.class));
        verifyNoMoreInteractions(this.fileDao);
    }

    @Test
    @DisplayName("Test update() with exists file id")
    public void givenFileWithExistsId_whenUpdate_thenReturnFile() {
        // given
        File file = FileUtil.getPersistedFile1();
        when(this.fileDao.findById(anyInt()))
                .thenReturn(Optional.of(file));
        when(this.fileDao.save(any(File.class)))
                .thenReturn(file);

        // when
        File result = this.fileService.update(file);

        // then
        assertThat(result).isEqualTo(file);
        verify(this.fileDao).findById(anyInt());
        verify(this.fileDao).save(any(File.class));
        verifyNoMoreInteractions(this.fileDao);
    }


    @Test
    @DisplayName("Test update() with non-exists file id")
    public void givenFileWithNonExistsId_whenUpdate_thenThrowException() {
        // given
        File file = FileUtil.getPersistedFile1();
        when(this.fileDao.findById(anyInt()))
                .thenReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> this.fileService.update(file))
                .isInstanceOf(FileNotFoundException.class)
                .hasMessage("File with id=%d not found".formatted(file.getId()));
        verify(this.fileDao).findById(anyInt());
        verifyNoMoreInteractions(this.fileDao);
    }

    @Test
    @DisplayName("Test delete() functionality")
    public void givenId_whenDelete_thenDeleteFile() {
        // given
        Integer id = 1;

        // when
        this.fileService.delete(id);

        // then
        verify(this.fileDao).deleteById(anyInt());
        verifyNoMoreInteractions(this.fileDao);
    }

}
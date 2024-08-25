package ru.hehmdalolkek.filestorage.service;

import java.util.List;

public interface GenericService<O, I> {

    O findById(I id);

    List<O> findAll();

    O save(O o);

    O update(O o);

    void delete(I id);

}

package ru.hehmdalolkek.filestorage.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<O, I> {

    Optional<O> findById(I id);

    List<O> findAll();

    O save(O o);

    void deleteById(I id);

}

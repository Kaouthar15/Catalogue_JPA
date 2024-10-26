package services;

import entities.Product;

import java.util.List;
import java.util.Optional;

public interface Repository<T, V> {

    Optional<T> get(V id);

    List<T> getAll();

    Optional<T> save(T t);

    Optional<T> update(T t);

    void delete(T t);

    Optional<T> add(T t);

}
